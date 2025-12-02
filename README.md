Inkaloto 🎉

Inkaloto es una plataforma web para la gestión de sorteos y juegos en línea. Los usuarios pueden registrarse, generar boletos digitales y participar en sorteos automáticos. Además, el sistema envía notificaciones automáticas con los resultados de los sorteos y premios obtenidos.

🚀 Características

🔐 Registro y autenticación de usuarios

🎟 Generación automática de boletos

🎲 Sorteos automáticos

📲 Notificaciones automáticas

📜 Historial de sorteos y resultados

🛠 Tecnologías utilizadas

Frontend: XHTML, CSS (sin Bootstrap)

Backend: Java (Servlets, JSP)

Base de datos: MySQL

Arquitectura: MVC (Modelo-Vista-Controlador)

Servidor: GlassFish

Dependencias:

EclipseLink para persistencia JPA

MySQL Connector/J para la conexión con MySQL

Otras dependencias necesarias para el funcionamiento de la aplicación

🗂 Estructura del proyecto

El proyecto está organizado de la siguiente manera:

inkalotoo/
│
├── /src/                              # Código fuente del proyecto
│   ├── /com/inkaloto/                 # Clases del modelo, servicio y controlador
│   │   ├── /dao/                     # Clases de acceso a datos (DAO)
│   │   ├── /modelo/                  # Clases de modelo (Usuario, Sorteo, etc.)
│   │   ├── /servicio/                # Lógica de negocio (Servicios)
│   │   └── /web/bean/                # Clases de Bean para gestión de la vista
│   │
│   └── /resources/                   # Archivos de configuración (persistence.xml, etc.)
│
├── /Web Pages/                        # Páginas web (XHTML)
│   ├── /WEB-INF/                     # Archivos de configuración de la aplicación
│   ├── /css/                          # Archivos CSS
│   ├── /imagenes/                     # Archivos de imágenes
│   └── /jsp/                          # Páginas XHTML para la interfaz de usuario
│
├── /database/                         # Script SQL para la base de datos
│   └── prueba1.sql                # Script para crear la base de datos
│
├── README.md                          # Este archivo
└── pom.xml                            # Configuración de Maven

📦 Instalación y uso
1. Clonar el repositorio

Para obtener una copia local del proyecto, clona el repositorio:

git clone https://github.com/jeonxvix/inkalotoo.git

2. Configuración del servidor

Para ejecutar la aplicación en GlassFish, sigue estos pasos:

Sube el archivo .war:

En el panel de administración de GlassFish, dirígete a la sección Applications y haz clic en Deploy para subir el archivo .war de la aplicación.

Base de datos:

Crea la base de datos inkaloto en MySQL:

CREATE DATABASE inkaloto;


Importa el archivo inkaloto_db.sql desde el directorio /database/ para crear las tablas necesarias.

Iniciar la aplicación:

Una vez que el archivo .war se haya desplegado, puedes acceder a la aplicación en el navegador en la URL correspondiente (por ejemplo, http://localhost:8080/inkaloto).

3. Ejecutar el proyecto

Accede a la aplicación en tu navegador, regístrate y comienza a participar en los sorteos.

🛠 Dependencias

A continuación, se enumeran las principales dependencias de Maven utilizadas en el proyecto:

EclipseLink: Para la gestión de la persistencia con JPA.

MySQL Connector/J: Para la conexión con la base de datos MySQL.

Jakarta EE: Para servicios web y controladores.

Otros JARs necesarios para la funcionalidad de la aplicación.

🤝 Contribuciones

Si deseas contribuir al proyecto, sigue estos pasos:

Haz un fork del repositorio.

Crea una nueva rama para tu funcionalidad (git checkout -b nueva-funcionalidad).

Realiza tus cambios y haz un commit (git commit -am 'Añadí nueva funcionalidad').

Sube tus cambios a tu repositorio (git push origin nueva-funcionalidad).

Crea un pull request para que revisemos tus cambios.

📜 Licencia

Este proyecto está licenciado bajo la MIT License - consulta el archivo LICENSE
 para más detalles.

📧 Contacto

Si tienes preguntas o deseas más información, contáctanos en [tu-email@dominio.com
].

¡Gracias por tu interés en Inkaloto! 🙌🎉
