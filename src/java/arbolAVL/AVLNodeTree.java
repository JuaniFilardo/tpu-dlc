package arbolAVL;

/**
 * Clase para representar un nodo de un arbol AVL.
 * @author  Ing. Valerio Frittelli.
 * @version Septiembre de 2008.
 */
public class AVLNodeTree extends NodeTree
{
   private int equi;  // -1, 0, 1 segun sea el factor de equilibrio del nodo
   
   /**
      Constructor por defecto
   */
   public AVLNodeTree ()
   {
   }
   
   /**
      Constructor. Inicializa los atributos tomando los valores de los parametros, salvo el 
      factor de equilibrio que se inicia en cero.
   */
   public AVLNodeTree (Comparable c, NodeTree iz, NodeTree de)
   {
       super(c,iz,de);
       equi = 0;
   }

   /**
    * Modificador del factor de equilibrio.
    * @param e nuevo valor del atributo equilibrio.
    */
   public void setEquilibrio (int e)
   {
      equi = e;
   }

   /**
    * Acceso al factor de equilibrio.
    * @return valor del atributo equilibrio. 
    */
   public int getEquilibrio ()
   {
      return equi;
   }
}