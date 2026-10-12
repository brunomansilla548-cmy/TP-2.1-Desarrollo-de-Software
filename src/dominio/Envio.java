package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Envio {
    //Atributos 
    private final String codigo;
    private final Sucursal destino;
    private final int capacidad;
    private final List<Paquete> paquetes = new ArrayList<>();
    private final List<Movimiento> movimientos = new ArrayList<>();
    
    //Constructor
    public Envio(String codigo, Sucursal destino, int capacidad) {
        this.codigo = codigo;
        this.destino = destino;
        this.capacidad = capacidad;
    }

    //Metodos
    public void agregarPaquete(Paquete paquete) throws IllegalStateException { //Lo que hace iLLegalStateException es que si el envío ya salió o si ya alcanzó su capacidad, no se puede agregar más paquetes y lanza una excepción con un mensaje indicando el problema.
        if (!todaviaNoSalio())
            throw new IllegalStateException("El envío " + codigo + " ya salió: no admite más paquetes");
        if (!hayLugar())
            throw new IllegalStateException("El envío " + codigo + " alcanzó su capacidad (" + capacidad + ")");
        paquetes.add(paquete);
    }

    public void despachar(Sucursal sucursal) throws IllegalStateException { 
        exigirDestino(sucursal); 
        exigirPaquetes();
    }

    public void entregar(Sucursal sucursal) throws IllegalStateException {
        exigirDestino(sucursal);
        exigirPaquetes();
    }

    public void recibir(Sucursal sucursal) throws IllegalStateException {
        exigirDestino(sucursal);
        exigirPaquetes();
    }


    //Consultas: devuelven datos, no imprimen
    public List<Movimiento> obtenerHistorial() {
        return movimientos; //Devuelve la lista de movimientos del envío, que representa el historial de movimientos del envío.
    }

    public List<Sucursal> obtenerSucursalesVisitadas() {
        return movimientos.stream().map(Movimiento::getSucursal).distinct().collect(Collectors.toList()); //Devuelve la lista de sucursales visitadas por el envío, eliminando duplicados.
    }

    public Movimiento obtenerUltimoMovimiento() throws NoSuchElementException {
        if (movimientos.isEmpty())
            throw new NoSuchElementException("El envío " + codigo + " no tiene movimientos");
        return movimientos.get(movimientos.size() - 1);
    }

    public List<Paquete> obtenerPaquetes() {
        return paquetes; //Devuelve la lista de paquetes del envío.
    }

    public String getCodigo(){ 
        return codigo; 
    }
    public Sucursal getDestino(){ 
        return destino; 
    }

    //Colaboración con los estados 
    //Uso exclusivo de los estados (paquete dominio.estado).
    public void registrar(TipoMovimiento tipo, Sucursal sucursal) {
        paquetes.forEach(tipo::aplicarA); // Aplica el tipo de movimiento a cada paquete del envío, utilizando el método aplicarA del tipo de movimiento.
        movimientos.add(new Movimiento(tipo, sucursal));
    }

    //Uso exclusivo de los estados (paquete dominio.estado). 
    public void exigirDestino(Sucursal sucursal) throws IllegalStateException {
        if (!destino.equals(sucursal)) // Si el destino del envío no es igual a la sucursal que se pasa como parámetro, lanza una excepción con un mensaje indicando que el envío no tiene como destino la sucursal indicada.
            throw new IllegalStateException("El envío " + codigo + " no tiene como destino la sucursal " + sucursal.getNombre());
    }


    // Reglas con nombre 
    private boolean hayLugar(){ 
        return paquetes.size() < capacidad;//Devuelve true si la cantidad de paquetes es menor que la capacidad del envío, indicando que hay lugar para agregar más paquetes.
        //Con el size() se obtiene la cantidad de elementos en la lista de paquetes y se compara con la capacidad del envío. Si la cantidad de paquetes es menor que la capacidad, significa que hay lugar para agregar más paquetes.
    }

    private boolean todaviaNoSalio(){
        return movimientos.stream().noneMatch(m -> m.getTipo() == TipoMovimiento.DESPACHADO);
        // Devuelve true si ninguno de los movimientos del envío tiene el tipo DESPACHADO, indicando que el envío todavía no ha salido.
        /*  Con el stream() se obtiene un flujo de los movimientos del envío y se utiliza el método noneMatch() para verificar si ninguno de los movimientos cumple con la condición de tener el tipo DESPACHADO. 
         Si ninguno de los movimientos tiene ese tipo, significa que el envío todavía no ha salido y se devuelve true. 
         Si un movimiento tiene el tipo DESPACHADO, significa que el envío ya ha salido y se devuelve false.*/
    }

    private boolean tienePaquetes(){ 
        return !paquetes.isEmpty();  //Devuelve true si la lista de paquetes del envío no está vacía, indicando que el envío tiene paquetes.
        //Con el isEmpty() se verifica si la lista de paquetes está vacía. Si la lista no está vacía, significa que el envío tiene paquetes y se devuelve true. Si la lista está vacía, significa que el envío no tiene paquetes y se devuelve false.
    }

    private void exigirPaquetes() throws IllegalStateException {
        if (!tienePaquetes())
            throw new IllegalStateException("El envío " + codigo + " no tiene paquetes"); // Genero una excepción con un mensaje indicando que el envío no tiene paquetes.
    }
}
