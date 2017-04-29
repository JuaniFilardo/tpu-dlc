package arbolAVL;

/**
 * Clase para representar un Arbol Binario de Busqueda.
 * @author Ing. Valerio Frittelli.
 * @version Septiembre de 2008.
 */
public class TreeSearch
{
    protected NodeTree raiz;
    protected int cantidad;
        
    /**
      Constructor. Garantiza que el arbol arranca vacio.
    */
    public TreeSearch ()
    {
       raiz = null;
       cantidad = 0;
    }
    
    /**
      Inserta un objeto en el arbol. Si el objeto a insertar ya existia, no lo inserta y sale
      retornando false. Si no existia, lo inserta y retorna true. El metodo cuida que el arbol 
      se mantenga homogeneo, retornando false sin hacer nada si se intenta insertar un objeto 
      cuya clase no coincida con la de los que ya estan en el arbol.
      @return true si el objeto pudo insertarse - false en caso contrario
      @param x el objeto a insertar
    */
    public boolean add (Comparable x)
    {

       if(!isOk(x)) return false;

       NodeTree p = raiz, q = null;
       while (p != null)
       {
            Comparable y = p.getInfo();
            if( x.compareTo(y) == 0 ) { break; }
            
            q = p;
            if ( x.compareTo(y) < 0 ) { p = p.getIzquierdo(); }
            else { p = p.getDerecho(); }
       }
       
       // si ya existia... retorne false.
       if (p != null) return false;
       
       // si no existia... hay que insertarlo.
       NodeTree nuevo = new NodeTree(x, null, null);
       if (q == null) { raiz = nuevo; }
       else 
       {
          if ( x.compareTo(q.getInfo() ) < 0) { q.setIzquierdo(nuevo); }
          else { q.setDerecho(nuevo); }
       }
       cantidad++;
       
       return true;
    }

    /**
     *  Remueve todos los elementos del arbol.
     */
    public void clear( )
    {
        raiz = null;
        cantidad = 0;
    }
    
    /**
     *  Determina si en el arbol existe un elemento que coincida con x. 
     *  @return true si x esta en el arbol - false si x no esta o si x es null.
     *  @param x el objeto a buscar.
     */
    public boolean contains(Comparable x)
    {
        return (this.search(x) != null); 
    }
    
    /**
     * Retorna true si el arbol esta vacio.
     * @return true si el arbol esta vacio - false en caso contrario.
     */
    public boolean isEmpty()
    {
        return (raiz == null);    
    }
    
    /**
      Borra el nodo del Arbol que contiene al objeto x. Si el objeto x no es compatible con la
      clase de los nodos del arbol, el metodo sale sin hacer nada y retorna false.
      @param x el objeto a borrar.
      @return true si la eliminacion pudo hacerse, o false en caso contrario.
    */
    public boolean remove (Comparable x)
    {
       if(!isOk(x)) return false;
       
       int ca = this.size();
       raiz = eliminar(raiz, x);
       return (this.size() != ca);
    }

    /**
      Busca un objeto en el arbol y retorna la direccion del nodo que lo contiene,
      o null si no lo encuentra. Se considera que el arbol es de busqueda, y por lo
      tanto no es heterogeneo. Se supone que el metodo de insercion usado para
      crear el arbol es el provisto por esta clase, el cual verifica que el arbol se
      mantenga homogeneo.
      @param x el objeto a buscar.
      @return la direccion del objeto encontrado que coincide con x, o null si x no se 
              encuentra. Tambi�n sale con null si se detecta que el objeto x no es 
              compatible con el tipo del info en los nodos del arbol.
    */
    public Comparable search (Comparable x)
    {
       if(!isOk(x)) return null;
        
       NodeTree p = raiz;
       while (p != null)
       {
              Comparable y = p.getInfo();
              if (x.compareTo(y) == 0) { break; }
              if (x.compareTo(y) <  0) { p = p.getIzquierdo(); }
              else { p = p.getDerecho(); }
       }
       return (p != null)? p.getInfo() : null;
    }   
    
    /**
     * Retorna la cantidad de elementos del arbol.
     * @return la cantidad de elementos del arbol.
     */
    public int size()
    {
        return cantidad;
    }
    
    /**
      Redefinicion de toString.
      @return el contenido del arbol, en secuencia de entre orden, como un String.
    */
    public String toString()
    {
       StringBuffer cad = new StringBuffer("");
       armarEntreOrden(raiz, cad);
       return cad.toString();       
    }

    /**
      Genera un String con el contenido del arbol en pre orden. 
      @return el contenido del arbol, en secuencia de pre orden, como un String.
    */
    public String toPreOrdenString()
    {
       StringBuffer cad = new StringBuffer("");
       armarPreOrden(raiz, cad);
       return cad.toString();       
    }

    /**
      Genera un String con el contenido del arbol en entre orden. Genera el mismo String que el 
      m�todo toString() redefinido en la clase.
      @return el contenido del arbol, en secuencia de entre orden, como un String.
    */
    public String toEntreOrdenString()
    {
       return this.toString();
    }

    /**
      Genera un String con el contenido del arbol en post orden. 
      @return el contenido del arbol, en secuencia de post orden, como un String.
    */
    public String toPostOrdenString()
    {
       StringBuffer cad = new StringBuffer("");
       armarPostOrden(raiz, cad);
       return cad.toString();       
    }

    private void armarPreOrden (NodeTree p, StringBuffer cad)
    {
      if (p != null) 
      {
        cad = cad.append(p.getInfo().toString() + " ");         
        armarPreOrden (p.getIzquierdo(), cad);
        armarPreOrden (p.getDerecho(), cad);
      }
    }

    private void armarEntreOrden (NodeTree p, StringBuffer cad)
    {
      if (p != null) 
      {
        armarEntreOrden (p.getIzquierdo(), cad);
        cad = cad.append(p.getInfo().toString() + " ");         
        armarEntreOrden (p.getDerecho(), cad);
      }
    }

    private void armarPostOrden (NodeTree p, StringBuffer cad)
    {
      if (p != null) 
      {
        armarPostOrden (p.getIzquierdo(), cad);
        armarPostOrden (p.getDerecho(), cad);
        cad = cad.append(p.getInfo().toString() + " ");         
      }
    }

    /*
      Auxiliar del metodo de borrado. Borra un nodo que contenga al objeto x si el mismo 
      tiene un hijo o ninguno.
      @param p nodo que esta siendo procesado.
      @param x el objeto a borrar.
      @return direccion del nodo que quedo en lugar del que venia en "p" al comenzar el proceso.
    */
    private NodeTree eliminar (NodeTree p, Comparable x)
    {
       if (p != null)
       {
         Comparable y = p.getInfo();
         if ( x.compareTo(y) < 0 ) 
         { 
             NodeTree  menor = eliminar(p.getIzquierdo(), x);
             p.setIzquierdo(menor);   
         }
         else
         {
              if ( x.compareTo(y) > 0 ) 
              { 
                 NodeTree mayor = eliminar (p.getDerecho(), x);
                 p.setDerecho(mayor); 
              } 
              else
              {  
                 // Objeto encontrado... debe borrarlo.
                 if (p.getIzquierdo() == null) { p = p.getDerecho(); }
                 else
                 {
                    if (p.getDerecho() == null) { p = p.getIzquierdo(); }
                    else 
                    {
                        // Tiene dos hijos... que lo haga otra!!!
                        NodeTree dos = dosHijos(p.getIzquierdo(), p);
                        p.setIzquierdo(dos);
                    }
                 }
                 cantidad--;
              }
         }
       }
       return p;
    }
    
    /*
      Auxiliar del m�todo de borrado. Reemplaza al nodo que venia en "p" por su mayor descendiente izquierdo "d", 
      y luego borra a "d" de su posicion original.
      @param d nodo que esta siendo procesado.
      @param p nodo a reemplazar por d.
      @return direccion del nodo que quedo en lugar del que venia en "d" al comenzar el proceso.
    */    
    private NodeTree dosHijos (NodeTree d, NodeTree p)
    {
       if (d.getDerecho() != null) 
       { 
         NodeTree der = dosHijos(d.getDerecho(), p);
         d.setDerecho(der); 
       }
       else 
       {
         p.setInfo(d.getInfo());
         d = d.getIzquierdo();
       }
       return d;
    }
    
    protected boolean isOk(Comparable x)
    {
       if(x == null) return false;
       if(raiz != null && x.getClass() != raiz.getInfo().getClass()) return false;
       return true;
    }
}
