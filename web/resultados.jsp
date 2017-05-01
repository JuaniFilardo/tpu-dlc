<%-- 
    Document   : resultados
    Created on : 18-Apr-2017, 16:36:13
    Author     : filardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <meta name="viewport" http-equiv="Content-Type" content="text/html, charset=UTF-8, width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="spinner.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="shortcut icon" href="favicon.ico" />
        <script src="app.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        
        <title>Resultados</title>
    </head>
    <body class="w3-light-grey">
        <div class="w3-container w3-center">
            <h1>Bookiepedia</h1>
           <!-- Este form le hace un request por POST a servletPrueba-->
           <form action="Buscador" method="POST">
               <input class="w3-input" type="text" placeholder="Ingrese aquí su búsqueda..." name="campoBusqueda" autofocus required>
               <!--<input class="w3-button w3-hover-blue-grey w3-block w3-xlarge" type="submit" value="Buscar">-->
           </form>
        </div>
                                                       
        <% String s[] = (String [])request.getAttribute("resultadoConsulta");%>
        <% String t[] = (String [])request.getAttribute("previewsConsulta");%>
        <% String foo = " arrojó los siguientes resultados:";%>
        <% String margen = "0%";%>
        <% // De este modo muestro un mensaje más acorde cuando no hay ningún resultado%>
        <% if (s.length == 0) { foo = " no produjo ningún resultado."; margen = "15%";}%>
        
        <h3 class="w3-center" style="margin:<%=margen%>">La búsqueda de "<%=request.getAttribute("consulta")%>"<%=foo%></h3><br
            
            <div id="resultadosHolder" class="w3-full">
                <div class="w3-container">
                    <div class="w3-half">
                        <ul class="w3-ul w3-hoverable">
                            <!--Imprimo los impares-->
                        <% for (int i = 0; i < s.length; i+=2) { %>
                            <% if (s[i] != null) {%>
                            <% String nombre = s[i].substring(0, s[i].length()-4);%>
                            <% String preview = t[i];%>
                            <%String ruta = "localhost:8080/tpu-dlc/libros/" + s[i];%>
                            <li id="<%=nombre%>" onmouseover="showPreview('<%=nombre%>','<%=preview%>')" onmouseout="hidePreview('<%=nombre%>')">
                                <a href=<%=ruta%> target="_blank"><%=s[i]%></a>
                                <p></p>
                            </li>
                            <%}%>
                        <%}%>
                        </ul>
                    </div>
                    <div class="w3-half">
                        <ul class="w3-ul w3-hoverable">
                            <!--Imprimo los impares-->
                        <% for (int i = 1; i < s.length; i+=2) { %>
                            <% if (s[i] != null) {%>
                            <!-- Hago esto para no tener problema con los nombres, que tienen un punto entre medio-->
                            <% String nombre = s[i].substring(0, s[i].length()-4);%>
                            <% String preview = t[i];%>
                            <%String ruta = "localhost:8080/tpu-dlc/libros/" + s[i];%>
                            <li id="<%=nombre%>" onmouseover="showPreview('<%=nombre%>','<%=preview%>')" onmouseout="hidePreview('<%=nombre%>')">
                                <a href=<%=ruta%> target="_blank"><%=s[i]%></a>
                            </li>
                            <%}%>
                        <%}%>
                        </ul>
                    </div>
                </div>
    </body>

