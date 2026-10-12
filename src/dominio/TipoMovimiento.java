package dominio;


/** Tipo de movimiento con comportamiento propio (sin String ni switch). */
public enum TipoMovimiento { //Enum que representa los tipos de movimiento que puede tener un paquete, cada tipo de movimiento tiene un comportamiento propio definido en el método aplicarA(Paquete p).
   
    RECIBIDO("Recibido"){ //Cada tipo de movimiento tiene un comportamiento propio definido en el método aplicarA(Paquete p).
        void aplicarA(Paquete p){ 
            p.recibir(); 
        } 
    },

    DESPACHADO("Despachado"){ 
        void aplicarA(Paquete p){ 
            p.despachar(); 
        } 
    },

    ENTREGADO("Entregado"){ 
        void aplicarA(Paquete p){ 
            p.entregar(); 
        } 
    };

    private final String descripcion; //Atributo que almacena la descripción del tipo de movimiento.

    TipoMovimiento(String descripcion){ //Constructor que recibe la descripción del tipo de movimiento y la asigna al atributo descripcion.
        this.descripcion = descripcion; 
    }

    abstract void aplicarA(Paquete paquete); // método abstracto que cada tipo de movimiento debe implementar para aplicar el movimiento a un paquete.

    public String getDescripcion(){ //Devuelve la descripción del tipo de movimiento.
        return descripcion; 
    }
}
