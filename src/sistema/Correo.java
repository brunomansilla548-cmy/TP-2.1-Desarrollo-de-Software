package sistema;

import dominio.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Correo {
    private final Map<String, Envio> envios = new HashMap<>(); // HashMap que almacena los envíos registrados en el sistema, donde la clave es el código del envío y el valor es el objeto Envio correspondiente.

    public void registrarEnvio(Envio envio, Sucursal origen) throws IllegalStateException {
        origen.recibir(envio);              // valida (envío sin paquetes) antes de registrar
        envios.put(envio.getCodigo(), envio);
    }

    // Colaboración con las sucursales
    public void recibir(String codigo, Sucursal s) 
    throws NoSuchElementException{ s.recibir(buscarEnvio(codigo)); 
    }

    public void despachar(String codigo, Sucursal s)
    throws NoSuchElementException{ s.despachar(buscarEnvio(codigo)); 
    }

    public void entregar(String codigo, Sucursal s)
    throws NoSuchElementException{ s.entregar(buscarEnvio(codigo)); 
    }


    // Consultas: devuelven datos, no imprimen
    public List<Movimiento> historial(String codigo){ 
        return buscarEnvio(codigo).obtenerHistorial(); 
    }
    public List<Sucursal> sucursalesVisitadas(String codigo){ 
        return buscarEnvio(codigo).obtenerSucursalesVisitadas(); 
    }
    public Movimiento ultimoMovimiento(String codigo){ 
        return buscarEnvio(codigo).obtenerUltimoMovimiento(); 
    }

    // Colaboración con los envíos
    private Envio buscarEnvio(String codigo) throws NoSuchElementException { // Busca un envío por su código en el HashMap de envíos registrados. Si el envío no existe, lanza una excepción NoSuchElementException con un mensaje indicando que no existe el envío.
        Envio envio = envios.get(codigo);
        if (envio == null)
            throw new NoSuchElementException("No existe el envío " + codigo);
        return envio;
    }
}
