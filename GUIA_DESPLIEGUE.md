# GUÍA DE DESPLIEGUE - SISTEMA ACADÉMICO WEB

## REQUISITOS DEL SISTEMA

Este sistema requiere las siguientes tecnologías instaladas en el equipo de destino:

- Java Development Kit (JDK) 11 o superior
- Apache Maven 3.6 o superior
- MySQL Server 8.0 o superior
- Apache Tomcat 9.0 o superior

## INSTALACIÓN PASO A PASO

### 1. INSTALACIÓN DE JAVA JDK

#### 1.1 Descargar Java JDK
- Acceder a https://www.oracle.com/java/technologies/downloads/
- Descargar JDK 11 o superior para Windows
- Ejecutar el instalador y seguir las instrucciones

#### 1.2 Configurar variables de entorno
- Abrir "Variables de entorno del sistema"
- Crear nueva variable del sistema:
  - Nombre: JAVA_HOME
  - Valor: C:\Program Files\Java\jdk-11.x.x (ruta de instalación)
- Editar variable PATH:
  - Agregar: %JAVA_HOME%\bin

#### 1.3 Verificar instalación
Abrir símbolo del sistema y ejecutar:
```
java -version
javac -version
```

### 2. INSTALACIÓN DE APACHE MAVEN

#### 2.1 Descargar Maven
- Acceder a https://maven.apache.org/download.cgi
- Descargar "Binary zip archive"
- Extraer en C:\apache-maven-3.x.x

#### 2.2 Configurar variables de entorno
- Crear nueva variable del sistema:
  - Nombre: MAVEN_HOME
  - Valor: C:\apache-maven-3.x.x
- Editar variable PATH:
  - Agregar: %MAVEN_HOME%\bin

#### 2.3 Verificar instalación
```
mvn -version
```

### 3. INSTALACIÓN DE MYSQL SERVER

#### 3.1 Descargar MySQL
- Acceder a https://dev.mysql.com/downloads/mysql/
- Descargar MySQL Community Server 8.0
- Ejecutar el instalador MySQL Installer

#### 3.2 Configuración durante instalación
- Tipo de configuración: Developer Default
- Método de autenticación: Use Strong Password Encryption
- Configurar contraseña root: 123456
- Configurar como servicio de Windows
- Puerto: 3306 (por defecto)

#### 3.3 Verificar instalación
Abrir símbolo del sistema y ejecutar:
```
mysql -u root -p123456 -e "SELECT VERSION();"
```

### 4. INSTALACIÓN DE APACHE TOMCAT

#### 4.1 Descargar Tomcat
- Acceder a https://tomcat.apache.org/download-90.cgi
- Descargar "32-bit/64-bit Windows Service Installer"
- Ejecutar el instalador

#### 4.2 Configuración durante instalación
- Puerto HTTP: 8080
- Usuario administrador: admin
- Contraseña: admin123
- Instalar como servicio de Windows

#### 4.3 Verificar instalación
- Iniciar servicio Apache Tomcat
- Acceder a http://localhost:8080
- Debe mostrar la página de bienvenida de Tomcat

### 5. PREPARACIÓN DE LA BASE DE DATOS

#### 5.1 Crear base de datos
Ejecutar en símbolo del sistema:
```
mysql -u root -p123456 -e "CREATE DATABASE app_administrativa;"
```

#### 5.2 Verificar creación
```
mysql -u root -p123456 -e "SHOW DATABASES;"
```

### 6. DESPLIEGUE DEL SISTEMA

#### 6.1 Obtener archivos del proyecto
- Copiar la carpeta completa del proyecto al equipo destino
- Ubicar en: C:\mi-proyecto-java

#### 6.2 Compilar el proyecto
Abrir símbolo del sistema en la carpeta del proyecto:
```
cd C:\mi-proyecto-java
mvn clean package
```

#### 6.3 Verificar compilación exitosa
- Debe mostrar "BUILD SUCCESS"
- Verificar que existe: target\mi-proyecto-web.war

#### 6.4 Desplegar en Tomcat
```
copy target\mi-proyecto-web.war "C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\"
```

#### 6.5 Verificar despliegue
- Esperar 30-60 segundos
- Verificar que existe la carpeta: webapps\mi-proyecto-web

### 7. CONFIGURACIÓN INICIAL

#### 7.1 Acceder al sistema
- Abrir navegador web
- Acceder a: http://localhost:8080/mi-proyecto-web

#### 7.2 Crear primer usuario
- Ir a la opción "Registrarse"
- Completar formulario de registro
- Verificar que se crea correctamente

#### 7.3 Verificar funcionalidades
- Login con usuario creado
- Completar información personal
- Completar información académica
- Probar sistema de radicación

## SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "java no se reconoce como comando"
- Verificar que JAVA_HOME esté configurado correctamente
- Verificar que %JAVA_HOME%\bin esté en PATH
- Reiniciar símbolo del sistema

### Error: "mvn no se reconoce como comando"
- Verificar que MAVEN_HOME esté configurado correctamente
- Verificar que %MAVEN_HOME%\bin esté en PATH
- Reiniciar símbolo del sistema

### Error de conexión a MySQL
- Verificar que el servicio MySQL esté iniciado
- Verificar contraseña root (debe ser: 123456)
- Verificar puerto 3306 esté disponible

### Error en Tomcat
- Verificar que el servicio Apache Tomcat esté iniciado
- Verificar puerto 8080 esté disponible
- Revisar logs en: logs\catalina.out

### Error "BUILD FAILURE" en Maven
- Verificar conexión a internet
- Limpiar caché de Maven: mvn clean
- Ejecutar: mvn dependency:resolve

## CONFIGURACIÓN AVANZADA

### Cambiar puerto de Tomcat
- Editar: conf\server.xml
- Buscar: Connector port="8080"
- Cambiar a puerto deseado
- Reiniciar servicio Tomcat

### Cambiar credenciales de base de datos
- Editar: src\main\java\utils\DatabaseConnection.java
- Modificar: URL, USER, PASSWORD
- Recompilar proyecto

### Configurar como servicio de producción
- Configurar Tomcat con certificado SSL
- Cambiar contraseñas por defecto
- Configurar backup automático de base de datos
- Configurar logs de auditoría

## MANTENIMIENTO

### Backup de base de datos
```
mysqldump -u root -p123456 app_administrativa > backup.sql
```

### Restaurar base de datos
```
mysql -u root -p123456 app_administrativa < backup.sql
```

### Actualizar aplicación
1. Compilar nueva versión: mvn clean package
2. Detener Tomcat
3. Eliminar carpeta: webapps\mi-proyecto-web
4. Copiar nuevo WAR: target\mi-proyecto-web.war
5. Iniciar Tomcat

### Monitoreo del sistema
- Revisar logs de Tomcat regularmente
- Monitorear uso de memoria y CPU
- Verificar conectividad de base de datos
- Probar funcionalidades críticas periódicamente
