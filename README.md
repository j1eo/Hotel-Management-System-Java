# Sistema de Gestión de Pagos para Empleados - Cadena de Hoteles UR

## Tabla de Contenidos
- [Características Principales](#características-principales)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación y Configuración](#instalación-y-configuración)

## Características Principales

### Módulo de Catálogos
- **Gestión de Empleados**:  
  Registro de Gerentes, Recamareras y Vendedores con cálculo automático de salarios y comisiones.  
  ![Empleados](https://github.com/user-attachments/assets/20c244dd-e1e5-4303-b908-3f5a93752b41)  
  ![Detalle Empleado](https://github.com/user-attachments/assets/72b72a39-ed98-4df5-bc7a-176269d16755)

- **Habitaciones**:  
  Tipos: sencilla, doble y penthouse con precios según categoría del hotel.  
  ![Habitaciones](https://github.com/user-attachments/assets/30bd1057-5ed4-4558-aef1-c11493d39c0d)

- **Configuración de Hoteles**:  
  Registro por nivel de estrellas (3-5) y asignación de precios base.  
  ![Hoteles](https://github.com/user-attachments/assets/82afef52-0c4e-4b8f-87f4-f4872fecdcd4)  
  ![Editar Hotel](https://github.com/user-attachments/assets/a976d488-cd76-406e-8fe9-eaf2668a5e85)

### Módulo de Operaciones
- **Registro de Ventas**:  
  Sistema completo para renta de habitaciones y asignación de clientes.  
  ![Ventas](https://github.com/user-attachments/assets/584ede1a-35e1-4d1a-a117-c1c57175e4ed)

- **Reservaciones**:  
  Calendario integrado y control de ocupación.  
  ![Calendario](https://github.com/user-attachments/assets/725e0222-a119-4f9c-80cc-8d1438ad3d66)  
  ![Detalle Reserva](https://github.com/user-attachments/assets/05a64ea0-de55-4a66-825a-d2d92bcaa0d3)

## Tecnologías Utilizadas

| Componente       | Tecnologías                                                                 |
|------------------|-----------------------------------------------------------------------------|
| Frontend         | Java Swing                                                                  |
| Backend          | Java 11, JDBC, MySQL Connector/J 8.0                                       |
| Base de Datos    | MySQL 8.0                                                                  |
| Arquitectura     | MVC (Modelo-Vista-Controlador)                                             |
| Herramientas     | Git                                                                        |

## Instalación y Configuración

### Requisitos
- Java JDK 11+
- MySQL Server 8.0+


### Pasos de Instalación
1. Clonar repositorio:
   ```bash
   git clone https://github.com/j1eo/hotel-payment-system.git
   cd hotel-payment-system
2. Crear Base de datos
3. Configurar Conexion:
Editar config/database.properties:

properties
 ```bash
db.url=jdbc:mysql://localhost:3306/NOMBRE?useSSL=false
db.username=USER
db.password=PASS
db.auto_create_tables=true
