package dominio;


public class Sucursal {

    //Atributos
    private final String nombre; // Usamos Final para que no se pueda cambiar el nombre de la sucursal una vez creado el objeto.

    //Constructor
    public Sucursal(String nombre){
        this.nombre = nombre; 
    };


    public void recibir(Envio envio){ //Creamos un método recibir que recibe un objeto de tipo Envio y llama al método recibir del objeto Envio, pasando la sucursal actual como parámetro.
        envio.recibir(this); 
    }

    public void despachar(Envio envio){ //Creamos un método despachar que recibe un objeto de tipo Envio y llama al método despachar del objeto Envio, pasando la sucursal actual como parámetro.
        envio.despachar(this); 
    }

    public void entregar(Envio envio){ //Lo mismo 
        envio.entregar(this); 
    }

    public String getNombre(){ //Devuelve el nombre de la sucursal.
        return nombre; 
    }
}
