package com.reservas.application.service.Impl;

import com.reservas.application.exception.ErrorNegocio;
import com.reservas.application.mapper.DetalleReservaMapper;
import com.reservas.application.mapper.ReservaMapper;
import com.reservas.application.service.ReservaService;
import com.reservas.domain.model.DetalleReserva;
import com.reservas.domain.model.Habitacion;
import com.reservas.domain.model.Reserva;
import com.reservas.domain.model.Usuario;
import com.reservas.domain.repository.HabitacionRepository;
import com.reservas.domain.repository.ReservaRepository;
import com.reservas.domain.repository.UsuarioRepository;
import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioRepository usuarioRepository;
    private final DetalleReservaMapper detalleReservaMapper;
    private final HabitacionRepository habitacionRepository;

    @Override
    @Transactional
    public ReservaDtoResponse agregarReserva(ReservaDtoRequest reservaDtoRequest) {
        Usuario usuarioBuscado = usuarioRepository.findById(reservaDtoRequest.idUsuario())
                .orElseThrow(()-> new ErrorNegocio("", HttpStatus.NOT_FOUND));
        Reserva reserva = reservaMapper.toEntity(reservaDtoRequest,usuarioBuscado,0);

        double montoTotalReservas = 0.0;
        List<DetalleReserva> listaDetalleReserva = new ArrayList<>();

        for(DetalleReservaDtoRequests detalleDto : reservaDtoRequest.reservaDetalleDtoRequests()){
            Habitacion habitacion = habitacionRepository.findById(detalleDto.idHabitacion())
                    .orElseThrow(()-> new ErrorNegocio("No se encontro la habitacion con id " + detalleDto.idHabitacion(), HttpStatus.NOT_FOUND));
            long dias = ChronoUnit.DAYS.between(detalleDto.fechaInicio(), detalleDto.fechaFin());
            double montoTotalHabitacion = habitacion.getTarifaDiaria() * dias ;
            montoTotalReservas += montoTotalHabitacion;


            DetalleReserva detalleReserva = detalleReservaMapper.toEntity(detalleDto,reserva,habitacion,dias);
            detalleReserva.setMontoTotalHabitacion(montoTotalHabitacion);
            reserva.addDetalleReserva(detalleReserva);
        }
        reserva.setMontoTotalReservas(montoTotalReservas);

        reserva =  reservaRepository.save(reserva);

        return reservaMapper.toDto(reserva);
    }
    //ToDO:Terminar la implementacion de los metodos de Reserva
}
