/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    /**
    * Muestra la preview de un resultado
    * @param {String} nombre El nombre del archivo cuya preview se va a
    * mostrar, lo cual facilita manejar cuando el mouse se corre. Ver hidePreview()
    * @param {String} preview La preview a mostrar
    * @returns {undefined}
    */
    function showPreview (nombre,preview) {
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
        ruta = "localhost:8080/tpu-dlc/libros/" + nombreArchivo;
        $('#'+nombre).text("");
        $('#'+nombre).append("<a href='" + ruta +"' target='_blank'>" + nombreArchivo + "</a>");
    }
    
    /**
     * 
     * @returns {undefined} muestra el spinner mientras se cargan los resultados
     */
    function showSpinner() {
        $('#formHolder').hide();
        $('#spinnerHolder').show();
    }