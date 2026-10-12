package app;

import dominio.*; // Importa todas las clases del paquete dominio, que contiene las clases relacionadas con el dominio del problema, como Envio, Paquete, Sucursal, Movimiento, etc.
import sistema.Correo;

import java.util.NoSuchElementException;

public class App {
    public static void main(String[] args) {

        // Crear una instancia de la clase Correo, que representa el sistema de correo y permite registrar, despachar, recibir y entregar envíos.
        // Crear dos sucursales: "Viedma" y "Bariloche", que representan los lugares de origen y destino de los envíos.
        Correo correo = new Correo();
        Sucursal viedma = new Sucursal("Viedma");
        Sucursal bariloche = new Sucursal("Bariloche");

        // Crear un envío con código "ENV-1", destino "Bariloche" y capacidad 2, y agregar dos paquetes a ese envío.
        Envio envio = new Envio("ENV-1", bariloche, 2);
        envio.agregarPaquete(new Paquete("P1", "Caja chica"));
        envio.agregarPaquete(new Paquete("P2", "Sobre"));

        // Registrar el envío en la sucursal "Viedma", despacharlo, recibirlo en "Bariloche", despacharlo nuevamente y finalmente entregarlo.
        correo.registrarEnvio(envio, viedma);
        correo.despachar("ENV-1", viedma);
        correo.recibir("ENV-1", bariloche);
        correo.despachar("ENV-1", bariloche);
        correo.entregar("ENV-1", bariloche);

        // Mostrar el historial de movimientos del envío, las sucursales visitadas y el último movimiento realizado.
        System.out.println("== Historial ==");
        correo.historial("ENV-1").forEach(System.out::println);

        // Mostrar las sucursales visitadas por el envío "ENV-1".
        System.out.println("== Sucursales ==");
        correo.sucursalesVisitadas("ENV-1").forEach(s -> System.out.println(s.getNombre()));

        // Mostrar el último movimiento del envío "ENV-1".
        System.out.println("== Último movimiento ==");
        System.out.println(correo.ultimoMovimiento("ENV-1"));

        // Pruebo operaciones inválidas para verificar que el sistema maneja correctamente los errores y excepciones.
        System.out.println("== Operaciones inválidas ==");
        intentar(() -> correo.despachar("ENV-1", bariloche));                         // ya entregado
        intentar(() -> correo.despachar("NO-EXISTE", viedma));                        // inexistente
        intentar(() -> correo.registrarEnvio(new Envio("ENV-2", viedma, 1), viedma)); // sin paquetes

        // Crear un nuevo envío con código "ENV-3", destino "Bariloche" y capacidad 1, agregar un paquete a ese envío y registrar el envío en la sucursal "Viedma".
        Envio e3 = new Envio("ENV-3", bariloche, 1);
        e3.agregarPaquete(new Paquete("P3", "Caja"));
        correo.registrarEnvio(e3, viedma);
        
        // Pruebo operaciones inválidas para el envío "ENV-3" para verificar que el sistema maneja correctamente los errores y excepciones.
        intentar(() -> correo.entregar("ENV-3", bariloche));                          // no despachado
        intentar(() -> correo.recibir("ENV-3", bariloche));                           // etapa incorrecta
        intentar(() -> e3.agregarPaquete(new Paquete("P4", "Otra")));                 // sin lugar
        correo.despachar("ENV-3", viedma);
        intentar(() -> e3.agregarPaquete(new Paquete("P5", "Otra")));                 // ya salió
    }

    // Ejecuta una operación y muestra si el sistema la aceptó o la rechazó. 
    private static void intentar(Runnable operacion) { //uso un metodo estatico que se aplica a cualquier operacion que se le pase como parametro, y maneja las excepciones que puedan surgir durante la ejecucion de la operacion.
        try { 
            operacion.run();
            System.out.println("OK");
        } catch (IllegalStateException | IllegalArgumentException | NoSuchElementException ex) {
            System.out.println("Rechazado: " + ex.getMessage());
        } finally {
            System.out.println("-- operación finalizada");
        }
    }
}
