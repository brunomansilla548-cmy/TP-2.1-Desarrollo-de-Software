package dominio;

public class Paquete {

    //Atributos 
    private final String id;
    private final String descripcion;
    private EstadoPaquete estado = EstadoPaquete.EN_SUCURSAL; //Inicializamos el estado del paquete en EN_SUCURSAL, ya que cuando se crea un paquete, este se encuentra en la sucursal de origen.

    //Constructor
    public Paquete(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Package-private: solo el Envio le envía estos mensajes.
    void recibir() throws IllegalStateException {
        exigirNoFinalizado();
        estado = EstadoPaquete.EN_SUCURSAL;
    }

    void despachar() throws IllegalStateException {
        exigirNoFinalizado();
        estado = EstadoPaquete.EN_TRANSITO;
    }

    void entregar() throws IllegalStateException {
        if (!estaEnTransito())
            throw new IllegalStateException("El paquete " + id + " todavía no fue despachado"); //Creamo una excepción para que no se pueda entregar un paquete que no ha sido despachado.
        estado = EstadoPaquete.ENTREGADO;
    }

    private boolean estaFinalizado(){ 
        return estado == EstadoPaquete.ENTREGADO; 
    }
    private boolean estaEnTransito(){ 
        return estado == EstadoPaquete.EN_TRANSITO; 
    }

    private void exigirNoFinalizado() throws IllegalStateException {
        if (estaFinalizado())
            throw new IllegalStateException("El paquete " + id + " ya fue entregado"); //Creamos una excepción para que no se pueda recibir o despachar un paquete que ya fue entregado.
    }

    public String getId(){ 
        return id; 
    }

    public String getDescripcion(){ 
        return descripcion; 
    }
    public EstadoPaquete getEstado(){ 
        return estado; 
    }
}
