package arbolAVL;

/**
  Clase para representar al Nodo de un Arbol Binario.
  @author Ing. Valerio Frittelli.
  @version Septiembre de 2008.
*/
public class NodeTree
{
   private Comparable info;
   private NodeTree izq, der;
   
   /**
      Constructor por defecto.
   */
   public NodeTree ()
   {
   }
   
   /**
      Inicializa los atributos tomando los valores de los parametros.
   */
   public NodeTree(Comparable x, NodeTree iz, NodeTree de)
   {
       info = x;
       izq  = iz;
       der  = de;
   }

   /**
      Modificador del info.
      @param x nuevo valor del info.
   */
   public void setInfo (Comparable x)
   {
      info = x;
   }

   /**
      Modificador de la direccion del sub�rbol izquierdo.
      @param iz Nuevo valor del atributo izq.
   */
   public void setIzquierdo (NodeTree iz)
   {
      izq = iz;
   }

   /**
      Modificador de la direccion del subarbol derecho.
      @param de Nuevo valor del atributo der.
   */
   public void setDerecho (NodeTree de)
   {
      der = de;
   }

   /**
      Acceso al info.
      @return valor del info.
   */
   public Comparable getInfo ()
   {
      return info;
   }

   /**
      Acceso a la direccion del subarbol izquierdo.
      @return valor de la direccion del subarbol izquierdo.
   */
   public NodeTree getIzquierdo ()
   {
      return izq;
   }

   /**
      Acceso a la direccion del subarbol derecho.
      @return valor de la direccion del subarbol derecho.
   */
   public NodeTree getDerecho ()
   {
      return der;
   }


   @Override
   public String toString()
   {
     return info.toString();
   }
}
