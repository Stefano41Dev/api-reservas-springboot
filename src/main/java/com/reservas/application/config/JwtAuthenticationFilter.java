package com.reservas.application.config;

import com.reservas.application.config.security.JwtService;
import com.reservas.application.service.Impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl usuarioService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrae el token (omitiendo "Bearer ") y el email
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        // 3. Si se encuentra un email y el usuario no está autenticado en el contexto actual
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga los detalles del usuario
            UserDetails userDetails = this.usuarioService.loadUserByUsername(userEmail);

            // 4. Valida el token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Crea un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 5. Establece el usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
