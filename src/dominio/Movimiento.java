package dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Objeto de dominio: un acontecimiento del recorrido de un envío. */
public class Movimiento {
    //Atributos de clase 
    private final TipoMovimiento tipo; 
    private final Sucursal sucursal;
    private final LocalDateTime fechaHora;

    //Constructor
    Movimiento(TipoMovimiento tipo, Sucursal sucursal) throws IllegalArgumentException {
        if (tipo == null || sucursal == null) 
            throw new IllegalArgumentException("Un movimiento requiere tipo y sucursal"); //Creamos una excepción para que no se pueda crear un movimiento sin tipo o sucursal.
        this.tipo = tipo;
        this.sucursal = sucursal;
        this.fechaHora = LocalDateTime.now();
    }

    //Metodos
    public TipoMovimiento getTipo(){ 
        return tipo; 
    }
    public Sucursal getSucursal(){ 
        return sucursal; 
    }
    public LocalDateTime getFechaHora(){ 
        return fechaHora; 
    }

    @Override 
    public String toString() {
        return fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) // Formateamos la fecha y hora del movimiento en el formato "dd/MM/yyyy HH:mm:ss" y lo devolvemos como un String junto con el tipo de movimiento y la sucursal donde se realizó.
                + " - " + tipo.getDescripcion() + " en " + sucursal.getNombre();
    }
}
