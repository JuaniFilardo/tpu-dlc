<%-- 
    Document   : resultados
    Created on : 18-Apr-2017, 16:36:13
    Author     : filardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <meta name="viewport" http-equiv="Content-Type" content="text/html; charset=UTF-8;width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="shortcut icon" href="favicon.ico" />
        <title>Resultados</title>
    </head>
    <body class="w3-light-grey">
        <div class="w3-container w3-center">
            <h1>Buscador</h1>
           <!-- Este form le hace un request por POST a servletPrueba-->
           <form action="Buscador" method="POST">
               <input type="text" size="100" placeholder="Ingrese aquí su búsqueda..." name="campoBusqueda" required>
               <input type="submit" value="Buscar">
           </form>
        </div>
        <h3 class="w3-center">La búsqueda de "<%=request.getAttribute("campoBusqueda")%>" arrojó estos resultados:</h3>
            <% String s[] = (String [])request.getAttribute("resultadoConsulta");%>
            <div id="resultadosHolder" class="w3-full">
                <div class="w3-container">
                    <div class="w3-half">
                        <ul class="w3-ul w3-hoverable">
                        <% for (int i = 0; i < s.length/2; i++) { %>
                            <% if (s[i] != null) {%>
                            <%String ruta = "localhost:8080/tpu-dlc/libros/" + s[i];%>
                            <li><a href=<%=ruta%> target="_blank"><%=s[i]%></a></li>
                            <%}%>
                        <%}%>
                        </ul>
                    </div>
                    <div class="w3-half">
                        <ul class="w3-ul w3-hoverable">
                        <% for (int i = s.length/2; i < s.length; i++) { %>
                            <% if (s[i] != null) {%>
                            <%String ruta = "localhost:8080/tpu-dlc/libros/" + s[i];%>
                            <li ><a href=<%=ruta%> target="_blank"><%=s[i]%></a></li>
                            <%}%>
                        <%}%>
                        </ul>
                    </div>
                </div>
            </div>
    </body>

