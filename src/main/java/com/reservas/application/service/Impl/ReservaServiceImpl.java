package com.reservas.application.service.Impl;

import com.reservas.application.exception.ErrorNegocio;
import com.reservas.application.mapper.DetalleReservaMapper;
import com.reservas.application.mapper.ReservaMapper;
import com.reservas.application.service.ReservaService;
import com.reservas.domain.model.DetalleReserva;
import com.reservas.domain.model.Enum.EstadoHabitacion;
import com.reservas.domain.model.Enum.EstadoReserva;
import com.reservas.domain.model.Habitacion;
import com.reservas.domain.model.Reserva;
import com.reservas.domain.model.Usuario;
import com.reservas.domain.repository.DetalleReservaRepository;
import com.reservas.domain.repository.HabitacionRepository;
import com.reservas.domain.repository.ReservaRepository;
import com.reservas.domain.repository.UsuarioRepository;
import com.reservas.web.dto.reserva.ReservaDtoRequest;
import com.reservas.web.dto.reserva.ReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoRequests;
import com.reservas.web.dto.reserva.detalle.DetalleReservaDtoResponse;
import com.reservas.web.dto.reserva.detalle.DetalleReservaModificarEstadoDtoRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioRepository usuarioRepository;
    private final DetalleReservaMapper detalleReservaMapper;
    private final HabitacionRepository habitacionRepository;
    private final DetalleReservaRepository detalleReservaRepository;

    @Override
    @Transactional
    public ReservaDtoResponse agregarReserva(ReservaDtoRequest reservaDtoRequest) {
        Usuario usuarioBuscado = usuarioRepository.findById(reservaDtoRequest.idUsuario())
                .orElseThrow(()-> new ErrorNegocio("", HttpStatus.NOT_FOUND));
        Reserva reserva = reservaMapper.toEntity(reservaDtoRequest,usuarioBuscado,0);

        double montoTotalReservas = 0.0;
        //List<DetalleReserva> listaDetalleReserva = new ArrayList<>();

        montoTotalReservas = agregarDetalleReserva(reservaDtoRequest,montoTotalReservas,reserva);
        reserva.setMontoTotalReservas(montoTotalReservas);

        reserva =  reservaRepository.save(reserva);

        return reservaMapper.toDto(reserva);
    }

    @Override
    @Transactional
    public ReservaDtoResponse modificarReserva(ReservaDtoRequest reservaDtoRequest, Long idReserva) {
        Reserva reservaBuscada = reservaRepository.findByIdReservaAndActivoTrue(idReserva)
                .orElseThrow(() -> new ErrorNegocio("No se encontro la reserva con id " + idReserva, HttpStatus.NOT_FOUND));
        reservaBuscada.getDetalleReservas().forEach(detalleReserva -> {
            detalleReserva.setEstadoReserva(EstadoReserva.LIBRE);
            detalleReserva.getHabitacion().setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
        });
        reservaBuscada.getDetalleReservas().clear();
        double nuevoMontoTotalReservas = 0.0;

        nuevoMontoTotalReservas = agregarDetalleReserva(reservaDtoRequest,nuevoMontoTotalReservas,reservaBuscada);

        reservaBuscada.setMontoTotalReservas(nuevoMontoTotalReservas);

        reservaBuscada = reservaRepository.save(reservaBuscada);

        return reservaMapper.toDto(reservaBuscada);
    }

    @Override
    public DetalleReservaDtoResponse modificarEstadoDetalleReserva(DetalleReservaModificarEstadoDtoRequest reservaDtoRequest, Long idDetalleReserva) {
        DetalleReserva detalleReserva = detalleReservaRepository.findById(idDetalleReserva)
                .orElseThrow(()-> new ErrorNegocio("No se encontro el detalle de la reserva con id " + idDetalleReserva, HttpStatus.NOT_FOUND));
        detalleReserva.setEstadoReserva(EstadoReserva.valueOf(reservaDtoRequest.estadoReserva().toUpperCase()));
        detalleReserva = detalleReservaRepository.save(detalleReserva);
        return detalleReservaMapper.toDto(detalleReserva);
    }

    @Override
    public ReservaDtoResponse buscarReservaPorId(Long idReserva) {
        Reserva reserva = reservaRepository.findByIdReservaAndActivoTrue(idReserva)
                .orElseThrow(()-> new ErrorNegocio("No se encontro la reserva con id"+idReserva, HttpStatus.NOT_FOUND));
        return reservaMapper.toDto(reserva);
    }

    @Override
    public Page<ReservaDtoResponse> listaReservas(Pageable pageable) {
        Page<Reserva> reservas = reservaRepository.findAllByActivoTrue(pageable);
        return reservas.map(reservaMapper::toDto);
    }

    public double agregarDetalleReserva(ReservaDtoRequest reservaDtoRequest, double nuevoMontoTotalReservas, Reserva reservaBuscada) {
        for (DetalleReservaDtoRequests detalleDto : reservaDtoRequest.reservaDetalleDtoRequests()) {
            Habitacion habitacion = habitacionRepository.findById(detalleDto.idHabitacion())
                    .orElseThrow(() -> new ErrorNegocio("No se encontro la habitacion con id " + detalleDto.idHabitacion(), HttpStatus.NOT_FOUND));
            if (detalleReservaRepository.verificarHabitacionReservadaRangoFecha(habitacion.getIdHabitacion(),detalleDto.fechaInicio(),detalleDto.fechaFin())){
                throw new ErrorNegocio("La habitacion ya esta ocupada en las fechas : " + detalleDto.fechaInicio() + " - "+ detalleDto.fechaFin(), HttpStatus.CONFLICT);
            }

            long dias = ChronoUnit.DAYS.between(detalleDto.fechaInicio(), detalleDto.fechaFin());
            double montoTotalHabitacion = habitacion.getTarifaDiaria() * dias;
            nuevoMontoTotalReservas += montoTotalHabitacion;

            DetalleReserva nuevoDetalle = detalleReservaMapper.toEntity(detalleDto, reservaBuscada, habitacion, dias);
            nuevoDetalle.getHabitacion().setEstadoHabitacion(EstadoHabitacion.OCUPADA);
            nuevoDetalle.setEstadoReserva(EstadoReserva.CONFIRMADA);
            nuevoDetalle.setMontoTotalHabitacion(montoTotalHabitacion);

            reservaBuscada.addDetalleReserva(nuevoDetalle);

        }
        return nuevoMontoTotalReservas;
    }
}
