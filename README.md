# API-Reservas

API REST para la gestión de un sistema de reservas de hotel, desarrollada con **Spring Boot 3.5.6** y **Java 21**. Permite administrar habitaciones, usuarios y reservas, con autenticación basada en **JWT**.

## Tabla de contenidos

- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Puesta en marcha](#puesta-en-marcha)
- [Autenticación y roles](#autenticación-y-roles)
- [Manejo de errores](#manejo-de-errores)
- [Endpoints](#endpoints)
  - [Auth](#auth---autenticación)
  - [Habitación](#habitación---habitacion)
  - [Reserva](#reserva---apireserva)
- [Modelos de datos (DTOs)](#modelos-de-datos-dtos)
- [Enumeraciones](#enumeraciones)

## Tecnologías

- Spring Boot 3.5.6 (Web, Data JPA, Security, Validation)
- PostgreSQL
- JWT (`jjwt` 0.12.6)
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Docker / Docker Compose

## Arquitectura

El proyecto sigue una organización por capas:

```
com.reservas
├── application
│   ├── config          # Seguridad, JWT, Swagger
│   ├── exception        # Manejo global de errores
│   ├── mapper           # Conversión entidad <-> DTO
│   └── service           # Lógica de negocio
├── domain
│   ├── model             # Entidades JPA
│   └── repository        # Repositorios Spring Data
└── web
    ├── controller         # Endpoints REST
    └── dto                # Contratos de entrada/salida
```

## Puesta en marcha

### Con Docker Compose

```bash
docker compose up --build
```

Esto levanta la API en el puerto `8080` y una base de datos PostgreSQL en el puerto `5433`.

### Variables de entorno relevantes

| Variable | Descripción |
|---|---|
| `SUPABASE_URL` | URL de conexión JDBC a la base de datos |


La documentación interactiva (Swagger UI) queda disponible en `/swagger-ui.html`.

### Usuario de prueba (seed)

El archivo `data.sql` inserta un usuario administrador de prueba:

- **email:** `stefano@gmail.com`
- **password:** `Stefano`
- **rol:** `ADMINISTRADOR`

## Autenticación y roles

La API usa JSON Web Tokens. Tras el login o registro se recibe un `token` (access token) y un `refreshToken`. El `token` debe enviarse en las peticiones protegidas en el header:

```
Authorization: Bearer <token>
```

Roles disponibles (`RolesUsuario`): `ADMINISTRADOR`, `CLIENTE`.

Reglas de acceso configuradas:

| Ruta | Acceso |
|---|---|
| `/auth/**` | Público |
| `/swagger-ui.html` | Público |
| `GET /habitacion` | Público |
| `/habitacion/**` (resto de métodos) | Requiere rol `ADMINISTRADOR` |
| `/reserva/**` | Requiere rol `CLIENTE` |
| Cualquier otra ruta | Requiere autenticación |


## Manejo de errores

Los errores de negocio (`ErrorNegocio`) devuelven el código HTTP correspondiente junto con:

```json
{
  "mensaje": "Descripción del error",
  "fecha": "2026-08-20T10:00:00"
}
```

Los errores de validación de un DTO (`@Valid`) devuelven `400 Bad Request` con un mapa `campo -> mensaje`:

```json
{
  "email": "El email no puede estar en blanco"
}
```

## Endpoints

### Auth — `/auth`

Todas las rutas son públicas.

| Método | Ruta | Body | Respuesta |
|---|---|---|---|
| `POST` | `/auth/login` | `LoginRequest` | `200 OK` → `AuthResponse` |
| `POST` | `/auth/register` | `RegisterRequest` | `200 OK` → `AuthResponse` |
| `POST` | `/auth/refresh` | — (usa header `Authorization: Bearer <refreshToken>`) | `200 OK` → `AuthResponse` |

**Ejemplo `POST /auth/login`**
```json
{
  "email": "stefano@gmail.com",
  "password": "Stefano"
}
```

**Ejemplo `POST /auth/register`**
```json
{
  "nombres": "Stefano Alexandro",
  "apellidos": "Gonzales Reyna",
  "email": "nuevo@correo.com",
  "dni": "12345678",
  "password": "unaClave"
}
```

**Respuesta (`AuthResponse`)**
```json
{
  "token": "eyJhbGciOi...",
  "refreshToken": "eyJhbGciOi..."
}
```

### Habitación — `/habitacion`

| Método | Ruta | Rol requerido | Body | Respuesta |
|---|---|---|---|---|
| `POST` | `/habitacion` | `ADMINISTRADOR` | `HabitacionDtoRequest` | `201 Created` → `HabitacionDtoResponse` |
| `GET` | `/habitacion/{id}` | `ADMINISTRADOR` | — | `200 OK` → `HabitacionDtoResponse` |
| `GET` | `/habitacion` | Público | — (query params de paginación) | `200 OK` → `Page<HabitacionDtoResponse>` |
| `PUT` | `/habitacion/{id}` | `ADMINISTRADOR` | `HabitacionDtoRequest` | `200 OK` → `HabitacionDtoResponse` |
| `PATCH` | `/habitacion/{id}` | `ADMINISTRADOR` | `HabitacionDtoEstadoHabitacionRequest` | `200 OK` → `HabitacionDtoResponse` |
| `GET` | `/habitacion/estado/{estadoHabitacion}` | `ADMINISTRADOR` | — (query params de paginación) | `200 OK` → `Page<HabitacionDtoResponse>` |
| `DELETE` | `/habitacion/{id}` | `ADMINISTRADOR` | — | `204 No Content` (baja lógica: `activo = false`) |

Reglas de negocio destacadas en `HabitacionServiceImpl`:
- La `capacidad` no puede ser negativa.
- La `tarifaDiaria` no puede ser menor a 20.
- No se permite registrar dos habitaciones con el mismo `numeroHabitacion`.
- `estadoHabitacion` (en el filtro por estado) debe coincidir con un valor válido del enum `EstadoHabitacion`, si no, se devuelve `400 Bad Request`.

**Ejemplo `POST /habitacion`**
```json
{
  "urlImagePrincipal": "https://...",
  "listaImagenes": [
    { "url": "https://..." }
  ],
  "numeroHabitacion": 101,
  "cantidadCamas": 2,
  "numeroPiso": 1,
  "tipoHabitacion": "DOBLE",
  "tarifaDiaria": 80,
  "descripcion": "Habitación doble con vista al jardín",
  "capacidad": 3
}
```

**Ejemplo `PATCH /habitacion/{id}`**
```json
{
  "estadoHabitacion": "EN_MANTENIMIENTO"
}
```

### Reserva — `/api/reserva`

| Método | Ruta | Body | Respuesta |
|---|---|---|---|
| `POST` | `/api/reserva` | `ReservaDtoRequest` | `201 Created` → `ReservaDtoResponse` |
| `GET` | `/api/reserva/{id}` | — | `200 OK` → `ReservaDtoResponse` |
| `GET` | `/api/reserva` | — (query params de paginación) | `200 OK` → `Page<ReservaDtoResponse>` |
| `PATCH` | `/api/reserva/{id}` | `DetalleReservaModificarEstadoDtoRequest` | `200 OK` → `DetalleReservaDtoResponse` |
| `PUT` | `/api/reserva/{id}` | `ReservaDtoRequest` | `200 OK` → `ReservaDtoResponse` |

Reglas de negocio destacadas en `ReservaServiceImpl`:
- Al crear o modificar una reserva, cada `DetalleReservaDtoRequests` valida que la habitación exista.
- Se verifica que la habitación no esté ya reservada en el rango de fechas indicado (`fechaInicio` / `fechaFin`); si lo está, se devuelve `409 Conflict`.
- El monto total de cada detalle se calcula como `tarifaDiaria * días`, y el `montoTotalReservas` es la suma de todos los detalles.
- Al crear un detalle nuevo, la habitación pasa a estado `OCUPADA` y el detalle queda en estado `CONFIRMADA`.
- Al modificar una reserva (`PUT`), se liberan los detalles anteriores (`LIBRE` / habitación `DISPONIBLE`) antes de recalcular los nuevos.
- El `PATCH` permite cambiar únicamente el `estadoReserva` de un detalle puntual (`DetalleReserva`), identificado por `idDetalleReserva` en la ruta.

**Ejemplo `POST /api/reserva`**
```json
{
  "idUsuario": 1,
  "reservaDetalleDtoRequests": [
    {
      "idHabitacion": 5,
      "fechaInicio": "2026-09-01",
      "fechaFin": "2026-09-05"
    }
  ]
}
```

**Ejemplo `PATCH /api/reserva/{idDetalleReserva}`**
```json
{
  "estadoReserva": "CANCELADA"
}
```

**Ejemplo de respuesta (`ReservaDtoResponse`)**
```json
{
  "idReserva": 10,
  "idUsuario": 1,
  "montoTotalReservas": 320.0,
  "detallesReserva": [
    {
      "idDetalleReserva": 22,
      "idReserva": 10,
      "idHabitacion": 5,
      "dias": 4,
      "fechaInicio": "2026-09-01",
      "fechaFin": "2026-09-05",
      "montoTotalHabitacion": 320.0,
      "estadoReserva": "CONFIRMADA"
    }
  ]
}
```
