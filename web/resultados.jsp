<%-- 
    Document   : resultados
    Created on : 18-Apr-2017, 16:36:13
    Author     : filardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
    </head>
    <body>
        <h1>
            La consulta arrojó estos resultados:
        </h1>
            <pre><%=request.getAttribute("resultadoConsulta")%></pre>
    </body>
</html>
