/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    window.addEventListener('load', function() {
       $.post("Buscador",{campoBusqueda:""}, function() {console.log("Success!");});
    });    
        /**
        * Muestra la preview de un resultado
        * @param {String} nombre El nombre del archivo cuya preview se va a
        * mostrar, lo cual facilita manejar cuando el mouse se corre. Ver hidePreview()
        * @param {String} preview La preview a mostrar
        * @returns {undefined}
        */
        function showPreview(nombre,preview) {
            $('#'+nombre).append("<p class='preview'>" + preview + "</p>");
        }

        /**
         *
         * @param {String} nombre El nombre del archivo cuya preview se oculta,
         * mostrando sólo el nombre.
         * @returns {undefined}
         */
        function hidePreview(nombre) {
            nombreArchivo = nombre + ".txt";
            ruta = "libros/" + nombreArchivo;
            $('#'+nombre).text("");
            $('#'+nombre).append("<i class='fa fa-book'>&nbsp;<a href='" + ruta +"' target='_blank'><b>" + nombre + "</b></a>");
            $('#'+nombre).append("<a href='" + ruta +"' download='" + ruta + "' target='_blank'><i class='fa fa-download w3-right'></i></a>");

            //$('#'+nombre).css("font-family","Raleway");
        }

        /**
         *
         * @returns {undefined} muestra el spinner mientras se cargan los resultados
         */
        function showSpinner() {
            $('#formHolder').hide();
            $('#busqueda').hide();
            $('#resultadosHolder').hide();
            $('#spinnerHolder').show();
        }