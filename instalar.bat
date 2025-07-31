@echo off
echo ==========================================
echo INSTALACION AUTOMATICA - APLICACION WEB
echo ==========================================
echo.

echo [1/5] Verificando Java...
java -version
if %errorlevel% neq 0 (
    echo ERROR: Java no encontrado. Instale Java 11+ desde https://adoptium.net/
    pause
    exit /b 1
)

echo.
echo [2/5] Verificando Maven...
mvn -version
if %errorlevel% neq 0 (
    echo ERROR: Maven no encontrado. 
    echo Opciones:
    echo 1. Instalar desde https://maven.apache.org/download.cgi
    echo 2. O usar: choco install maven -y
    pause
    exit /b 1
)

echo.
echo [3/5] Limpiando proyecto...
mvn clean

echo.
echo [4/5] Compilando proyecto...
mvn compile
if %errorlevel% neq 0 (
    echo ERROR: Falló la compilación
    pause
    exit /b 1
)

echo.
echo [5/5] Generando WAR...
mvn package
if %errorlevel% neq 0 (
    echo ERROR: Falló la generación del WAR
    pause
    exit /b 1
)

echo.
echo ==========================================
echo INSTALACION COMPLETADA EXITOSAMENTE!
echo ==========================================
echo.
echo Archivo generado: target\mi-proyecto-java.war
echo.
echo PROXIMOS PASOS:
echo 1. Iniciar Tomcat
echo 2. Copiar target\mi-proyecto-java.war a webapps\
echo 3. Acceder a http://localhost:8080/mi-proyecto-java
echo.
pause
