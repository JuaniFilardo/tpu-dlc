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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
        <script src="app.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <title>Bookipedia - Resultados</title>
    </head>
    <body class="w3-light-grey">
        <div class="w3-container w3-center">
            <div class="w3-container w3-center w3-hide-small">
                <a href="index.html" target="_self"><img src="Bookipedia.png" class="w3-padding w3-image" alt="logo2" style="max-width: 25%; max-height: 25%"/></a>
            </div>
            <div class="w3-container w3-center w3-hide-large w3-hide-medium">
                <h4><a href="index.html" target="_self">Bookipedia</a></h4>
            </div>
                <div id="formHolder" style="margin-left: 0%; margin-right: 0%">
                <!-- Este form le hace un request por POST a servletPrueba-->
                <form action="Buscador" method="POST" onsubmit="showSpinner()">
                    <input class="w3-input" type="text" placeholder="Ingrese aquí su búsqueda..." name="campoBusqueda" autofocus required>
                    <!--<input class="w3-button w3-hover-blue-grey w3-block w3-xlarge" type="submit" value="Buscar">-->
                </form>
           </div>
        </div>

        <% String s[] = (String [])request.getAttribute("resultadoConsulta");%>
        <% String t[] = (String [])request.getAttribute("previewsConsulta");%>
        <% String foo = " arrojó los siguientes resultados:";%>
        <% String margen = "2%";%>
        <% // De este modo muestro un mensaje más acorde cuando no hay ningún resultado%>
        <% if (s.length == 0) { foo = " no produjo ningún resultado :("; margen = "15%";}%>

        <h4 id="busqueda" class="w3-center" style="margin:<%=margen%>">La búsqueda de <b><%=request.getAttribute("textoConsulta")%></b><%=foo%></h4><br>
            <div id="resultadosHolder" class="w3-full">
                <div class="w3-container">
                    <div class="w3-half">
                        <ul class="w3-ul w3-hoverable">
                            <!--Imprimo los impares-->
                        <% for (int i = 0; i < s.length; i+=2) { %>
                            <% if (s[i] != null) {%>
                            <% String nombre = s[i].substring(0, s[i].length()-4);%>
                            <% String preview = t[i];%>
                            <%String ruta = "libros/" + s[i];%>
                            
                            
                            <li id="<%=nombre%>" onmouseover="showPreview('<%=nombre%>','<%=preview%>')" onmouseout="hidePreview('<%=nombre%>')">
                                <i class="fa fa-book">&nbsp;</i><a href="<%=ruta%>" target="_blank"><b><%=nombre%></b></a>
                                <a href=<%=ruta%> download="<%=ruta%>" target="_blank"><i class="fa fa-download w3-right"></i></a>
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
                            <%String ruta = "libros/" + s[i];%>
                            <li id="<%=nombre%>" onmouseover="showPreview('<%=nombre%>','<%=preview%>')" onmouseout="hidePreview('<%=nombre%>')">
                                <i class="fa fa-book">&nbsp;</i><a href="<%=ruta%>" target="_blank"><b><%=nombre%></b></a>
                                <a href=<%=ruta%> download="<%=ruta%>" target="_blank"><i class="fa fa-download w3-right"></i></a>
                            </li>
                            <%}%>
                        <%}%>
                        </ul>
                    </div>
                </div>
            </div>
                <div id="spinnerHolder" class="sk-cube-grid" style="display:none">
                    <div class="sk-cube sk-cube1"></div>
                    <div class="sk-cube sk-cube2"></div>
                    <div class="sk-cube sk-cube3"></div>
                    <div class="sk-cube sk-cube4"></div>
                    <div class="sk-cube sk-cube5"></div>
                    <div class="sk-cube sk-cube6"></div>
                    <div class="sk-cube sk-cube7"></div>
                    <div class="sk-cube sk-cube8"></div>
                    <div class="sk-cube sk-cube9"></div>
                </div>                                  
    </body>
