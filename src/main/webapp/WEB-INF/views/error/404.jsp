<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error 404 - Página no encontrada</title>
    <style>
        body {
            font-family: "Dialog", "SansSerif", sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border: 2px inset #d0d0d0;
            border-radius: 5px;
            text-align: center;
        }
        
        .error-code {
            font-size: 4em;
            color: #d32f2f;
            margin: 0;
        }
        
        .error-message {
            font-size: 1.2em;
            color: #666;
            margin: 20px 0;
        }
        
        .btn {
            background: linear-gradient(#4a90e2, #357abd);
            color: white;
            border: 1px outset #357abd;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 3px;
            display: inline-block;
            margin-top: 20px;
        }
        
        .btn:hover {
            background: linear-gradient(#357abd, #2968a3);
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="error-code">404</h1>
        <p class="error-message">Lo sentimos, la página que buscas no existe.</p>
        <p>La página pudo haber sido movida, eliminada o la URL es incorrecta.</p>
        <a href="<%= request.getContextPath() %>/" class="btn">Volver al Inicio</a>
    </div>
</body>
</html>
