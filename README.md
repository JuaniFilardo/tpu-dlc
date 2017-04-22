
# Trabajo Práctico Único de Diseño de Lenguajes de Consulta.
    UTN - FRC - 2017
<hr>

El trabajo para el práctico integrador será el desarrollo de un motor de búsqueda, en base a los principios enunciados en las clases del teórico de la materia. Los requisitos básicos para ese motor serán los siguientes:

1.) El motor debe diseñarse para ser usado sobre una base de datos documental, que contendrá sólo archivos de texto plano (extensión .txt). La carpeta original con todos los documentos a indexar está en la diracción: https://drive.google.com/folderview?id=0B_R7SeoAotsmUUtYendIX04zRjA&usp=sharing.  La carpeta indicada contiene 500 documentos y cada uno de ellos es una obra literaria en idioma inglés. No hay links a otros documentos dentro de cada uno de los archivos de texto provistos.

2.) Se pide usar el modelo vectorial para el desarrollo del motor. El modelo vectorial es muy conocido, y nuestra exigencia  se justifica en cuanto a que los alumnos dominen una tecnología muy difundida y básica antes de lanzarse a otras más novedosas.

3.) Los alumnos podrían descargar los documentos e indexarlos y buscarlos en forma local. Lo ideal sería que al momento de la entrega el motor indexe los documentos directamente desde la carpeta cuyo url se indica más arriba, y proceder a responder consultas tomando los archivos desde la misma carpeta, pero como en la práctica se está pidiendo un prototipo, se aceptará que los documentos sean contenidos localmente.

4.) El Trabajo Práctico Único desarrollado en la asignatura TSB preveía la implementación del Vocabulario para un subconjunto de documentos de esta misma base de datos documental. Ahora, en DLC, se pide que reusen lo hecho en TSB, lo complementen y lo incorporen al diseño del prototipo del Motor de Búsqueda completo.

5.) Deberán tener especial cuidado en el diseño y la implementación de la lista de posteo de cada término, para evitar una ocupación de disco exagerada. Las listas de posteo pueden venir implementadas en bases de datos (quedando para el alumno el diseño de las mismas), aunque aclaramos que en una situación real, y por razones de performance, las listas de posteo suelen implementarse en archivos binarios gestionados directamente por el programador, y no en bases de datos.

6.) Se supone que la interfaz del sistema debe permitir una consulta abierta, y el buscador debe mostrar un listado con los títulos de los documentos más relevantes para esa consulta. Cada título debe venir como hipervínculo (link) al documento real, el cual debe abrirse (o descargarse) si el link es activado. Sin embargo, no es obligatorio el diseño de una interfaz web para el usuario final.  

7.) Deben usarse elementos básicos de la plataforma JEE para el desarrollo del Motor, de modo que se apliquen al mismo tiempo los conceptos aprendidos en el práctico y en el teórico. Se proveerá a los alumnos de un par de modelos conteniendo la arquitectura básica de arranque, de forma que puedan completarla incorporando en ella el  módulo de indexación y el módulo de búsqueda.

8.) Cualquier agregado será bienvenido, siempre y cuando el trabajo se termine completamente dentro de un período prudencial de tiempo en el cursado de la materia. Los estudiantes deben calcular que el desarrollo completo no les insuma más de un mes de trabajo durante el cursado.
