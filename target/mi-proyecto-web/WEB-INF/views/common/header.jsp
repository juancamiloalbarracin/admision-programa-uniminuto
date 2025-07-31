<%-- 
   Header común con logo para todas las páginas
   Sistema Académico UDC - Migración Web
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Header institucional con logo UNIMINUTO -->
<div class="header">
    <div style="display: flex; align-items: center; justify-content: center; gap: 15px;">
        <!-- Logo UNIMINUTO -->
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/images/logo-uniminuto.png" 
                 alt="Logo UNIMINUTO" 
                 style="max-width: 80px; max-height: 50px; object-fit: contain;">
        </div>
        <!-- Título de la aplicación -->
        <h1>App administrativa</h1>
    </div>
</div>
