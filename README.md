# Sistema de Gestión de Barbería - "The One"

Plataforma integral para la gestión y agendamiento de citas en barberías, basada en una arquitectura escalable de **Microservicios** con **Spring Boot** y **Docker**.

## Equipo de Ingeniería
*   **Santiago Villa Salazar**
*   **Edgar Fabian Rueda Colonia**
*   **Manuel Alexander Serna Jaraba**
*   **Adrian Felipe Velasquez Arias**

---

## Guía de Despliegue

El sistema está diseñado para ser agnóstico del entorno mediante contenedorización. Para ejecutar la suite completa en un entorno local o de servidor:

### 1. Inicialización de Servicios
Desde la raíz del proyecto, ejecute el orquestador:

```bash
docker compose up --build -d
```

_Este comando compilará los artefactos, construirá las imágenes Docker e iniciará el cluster de microservicios en segundo plano._

### 2. Acceso a la Plataforma
Una vez que el health-check de los servicios sea positivo, el portal web estará disponible en:

**[http://localhost:8084](http://localhost:8084)**

---

## Credenciales de Acceso

El sistema se despliega con un conjunto de datos semilla para pruebas de aceptación:

| Perfil | Usuario | Contraseña | Capacidades |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin@barberia.com` | `admin123` | Gestión de agenda global, aprobación de citas y control de catálogo. |
| **Cliente** | `user@test.com` | `123456` | Exploración de servicios, selección de profesionales y reserva de turnos. |

> **Nota:** El sistema permite el auto-registro de nuevos usuarios desde el portal de inicio.

---

## Arquitectura de Software

La solución implementa una arquitectura distribuida robusta utilizando los siguientes componentes:

*   **API Gateway (Spring Cloud Gateway):** Punto de entrada único que gestiona el enrutamiento, seguridad perimetral y balanceo de carga.
*   **Service Discovery (Netflix Eureka):** Registro y descubrimiento dinámico de instancias para alta disponibilidad.
*   **Microservicios de Negocio:**
    *   `auth-service`: Gestión de identidad y control de acceso.
    *   `catalog-service`: Administración de inventario de profesionales y servicios.
    *   `booking-service`: Motor de reglas de negocio para reservas y validación de disponibilidad.
*   **Frontend Service:** Aplicación web renderizada en servidor (SSR) con Thymeleaf.

### Base de Datos
Se utiliza **MySQL 8.0** en contenedores separados para cada servicio, garantizando el principio de *Database per Service*.

---

## Estructura del Repositorio

*   `/docker-compose.yml`: Orquestación de todos los contenedores.
*   `/api-gateway`: Configuración del Gateway.
*   `/auth-service`, `/catalog-service`, `/booking-service`: Código fuente Backend.
*   `/frontend-service`: Código fuente Frontend (Vistas HTML/CSS).
*   `/INFORME_FINAL.tex`: Documentación académica del proyecto.
