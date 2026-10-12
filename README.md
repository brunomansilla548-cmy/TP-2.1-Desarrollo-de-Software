# TP N°2.1 – Seguimiento de envíos (Java)

## Estructura

```
tp21/
├── README.md
├── compilar-y-ejecutar.sh
├── diagramas/
│   ├── diagrama-de-clases.mermaid
│   ├── secuencia-1-despachar-envio.mermaid
│   └── secuencia-2-consultar-ultimo-movimiento.mermaid
└── src/
    ├── app/            App: punto de entrada. Solo pide datos y los muestra (try/catch/finally).
    ├── sistema/        Correo: registro de envíos; única puerta de entrada para App.
    └── dominio/        Envio, Sucursal, Paquete, Movimiento, TipoMovimiento, EstadoPaquete
        └── estado/     Patrón State: EstadoEnvio, Creado, EnSucursal, EnTransito, Entregado
```

## Excepciones (sin clases propias)

Se usan las excepciones estándar de Java, lanzadas con `throw` y declaradas con `throws` en cada clase.

| Excepción | Cuándo |
|---|---|
| `IllegalStateException` | La operación no corresponde a la etapa del envío/paquete (entregar sin despachar, despachar uno entregado, envío sin paquetes, sin lugar, ya salió). |
| `IllegalArgumentException` | Movimiento incoherente (sucursal equivocada, datos nulos). |
| `NoSuchElementException` | Envío inexistente o sin movimientos. |

`App.intentar()` las captura con `try / catch / finally`.

## Quién es responsable de qué

| Clase | Responsabilidad |
|---|---|
| `App` | Iniciar operaciones y mostrar resultados. No modifica objetos. |
| `Correo` | Buscar envíos por código. |
| `Sucursal` | Recibir, despachar y entregar envíos (delega en `Envio`). |
| `Envio` | Paquetes, historial y consultas. Delega las transiciones en su estado. |
| `EstadoEnvio` y sus estados | Deciden qué transición es válida y cuál es el siguiente estado. |
| `Paquete` | Protege su propio estado (`EN_SUCURSAL`, `EN_TRANSITO`, `ENTREGADO`). |
| `Movimiento` / `TipoMovimiento` | Registran cada acontecimiento del recorrido. |

## Ejecutar

```
./compilar-y-ejecutar.sh
```
Requiere JDK 8 o superior. En IDE: marcar `src` como carpeta de fuentes y ejecutar `app.App`.
