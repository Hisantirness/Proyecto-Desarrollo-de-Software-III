# 💈 Sistema de Gestión de Barbería - "The One"

> **Proyecto Final - Desarrollo de Software III**
> 
> **Facultad de Ingeniería - Tecnología en Sistemas - Universidad del Valle**

Este repositorio contiene la implementación completa de una plataforma de agendamiento de citas basada en **Microservicios**, **Spring Boot** y **Docker**.

## 👥 Equipo de Desarrollo
*   **Santiago Villa Salazar**
*   **Edgar Fabian Rueda Colonia**
*   **Manuel Alexander Serna Jaraba**
*   **Adrian Felipe Velasquez Arias**

---

## 🚀 Guía de Inicio Rápido (Para el Docente)

El proyecto está totalmente dockerizado. Para ejecutarlo, solo requiere tener instalado **Docker Desktop**.

### 1. Despliegue del Sistema
Abra una terminal en la raíz del proyecto y ejecute:

```bash
docker compose up --build
```

_Espere aproximadamente 1-2 minutos mientras se descargan las imágenes, se compilan los servicios y se inician las bases de datos._

### 2. Acceso a la Aplicación
Una vez finalizado el despliegue, el sistema estará disponible en:

👉 **[http://localhost:8084](http://localhost:8084)**

---

## 🔑 Credenciales de Prueba (Demo)

Para facilitar la revisión, el sistema ya cuenta con usuarios precargados en la base de datos:

| Rol | Usuario (Email) | Contraseña | Funcionalidad |
| :--- | :--- | :--- | :--- |
| **Administrador** | `admin@barberia.com` | `admin123` | Puede gestionar citas (Aceptar/Rechazar) y ver el panel global. |
| **Cliente** | `user@test.com` | `123456` | Puede ver barberos, servicios y agendar citas personales. |

> **Nota:** También puede registrar un nuevo usuario desde la opción **"Registrarse"** en la pantalla de inicio.

---

## 🏗️ Arquitectura del Sistema

El proyecto implementa los siguientes patrones de microservicios:

*   **API Gateway (Puerto 8080):** Centraliza el tráfico y enruta peticiones.
*   **Service Discovery (Eureka - Puerto 8761):** Registro dinámico de instancias.
*   **Microservicios de Dominio:**
    *   `auth-service`: Autenticación y Usuarios.
    *   `catalog-service`: Gestión de Barberos y Servicios.
    *   `booking-service`: Lógica de reservas y disponibilidad.
*   **Frontend (Puerto 8084):** Interfaz web renderizada con Thymeleaf.

### 💾 Base de Datos
Se utiliza **MySQL 8.0** en contenedores separados para cada servicio, garantizando el principio de *Database per Service*.

---

## 📂 Estructura del Repositorio

*   `/docker-compose.yml`: Orquestación de todos los contenedores.
*   `/api-gateway`: Configuración del Gateway.
*   `/auth-service`, `/catalog-service`, `/booking-service`: Código fuente Backend.
*   `/frontend-service`: Código fuente Frontend (Vistas HTML/CSS).
*   `/INFORME_FINAL.tex`: Documentación académica del proyecto.
