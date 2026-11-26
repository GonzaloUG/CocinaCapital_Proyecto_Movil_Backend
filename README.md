# Cocina Capital — Backend

Backend de Cocina Capital, una aplicación Android que conecta a usuarios con restaurantes de la Región de Valparaíso mediante tickets canjeables por beneficios.

**Asignatura:** DSY1105 Desarrollo de Aplicaciones Móviles — Evaluación Parcial 4

## Integrantes

- Oscar Torres — `usuarios-service`
- Gonzalo Urtubia — `locales-service`
- Malcolm Ruz — `tickets-service`

## Microservicios

| Microservicio | Puerto | Base de datos | Funcionalidad |
| --- | --- | --- | --- |
| `usuarios-service` | `8081` | `cocina_usuarios` | Registro, inicio de sesión y gestión del perfil. |
| `locales-service` | `8082` | `cocina_locales` | Listar, consultar, crear, editar y eliminar locales. |
| `tickets-service` | `8083` | `cocina_tickets` | Comprar tickets de forma simulada, consultar saldo e historial, emitir y validar cupones. |

## Tecnologías

- Java 17 como objetivo de compilación. La ejecución local se probó con JDK 21.
- Spring Boot 3.3.5 y Spring Data JPA.
- Maven.
- MySQL. El equipo probó la conexión con el módulo MySQL de XAMPP.

## Endpoints principales

| Servicio | Método y ruta | Operación |
| --- | --- | --- |
| Usuarios | `POST /api/usuarios` | Registrar una cuenta. |
| Usuarios | `POST /api/usuarios/login` | Iniciar sesión. |
| Usuarios | `GET`, `PUT`, `DELETE /api/usuarios/{id}` | Consultar, editar o eliminar un perfil. |
| Locales | `GET`, `POST /api/locales` | Listar o crear locales. |
| Locales | `GET`, `PUT`, `DELETE /api/locales/{id}` | Consultar, editar o eliminar un local. |
| Tickets | `GET /api/tickets/saldo/{usuarioId}` | Consultar el saldo. |
| Tickets | `POST /api/tickets/compras` | Registrar una compra simulada. |
| Tickets | `GET /api/tickets/compras/usuario/{usuarioId}` | Consultar el historial de compras. |
| Tickets | `POST /api/cupones` | Emitir un cupón. |
| Tickets | `GET /api/cupones/usuario/{usuarioId}` | Consultar los cupones de un usuario. |
| Tickets | `PUT /api/cupones/{codigo}/canjear` | Marcar un cupón como utilizado. |

## Cómo ejecutar el backend

1. Clona el repositorio y entra a la rama `main`.
2. Inicia MySQL en XAMPP. Los servicios esperan una base de datos en `localhost:3306`. Si tu instalación usa otras credenciales, ajusta la configuración local de cada servicio.
3. Abre una terminal por microservicio. Desde la raíz del repositorio, ejecuta un bloque en cada terminal.

**Usuarios**

```powershell
cd .\usuarios-service
mvn spring-boot:run
```

**Locales**

```powershell
cd .\locales-service
mvn spring-boot:run
```

**Tickets**

```powershell
cd .\tickets-service
mvn spring-boot:run
```

4. Deja las tres terminales abiertas y comprueba que [el listado de locales](http://localhost:8082/api/locales) devuelva un JSON.
5. Abre la app en Android Studio y ejecútala en el emulador. La app accede al backend del PC mediante `10.0.2.2` y los puertos de la tabla.

## Aplicación Android

[Repositorio de Cocina Capital](https://github.com/os-TorresR/CocinaCapital_Proyecto_Movil)
