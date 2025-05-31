package Centro_Comercial;

import Centro_Comercial.Comercios.*;

import java.util.concurrent.Semaphore;

/**
 * Clase CentroComercial
 * Representa un centro comercial que gestiona la capacidad total de visitantes
 * y contiene distintas tiendas, un cine, un patio de comidas y un estacionamiento.
 */
public class CentroComercial {
    /**
     * Semáforo que controla la capacidad total de personas dentro del centro comercial.
     */
    private final Semaphore capacidadTotal;

    /**
     * Instancia de la clase Estadísticas para registrar eventos del centro comercial.
     */
    public final Estadisticas estadisticas = new Estadisticas();

    /**
     * Variable booleana de control para detener o continuar la ejecución del centro comercial.
     */
    public volatile boolean running = true;

    /**
     * Espacios que componen del centro comercial.
     */
    public final TiendaRopa tiendaRopa;
    public final Jugueteria jugueteria;
    public final TiendaDeportiva tiendaDeportiva;
    public final TiendaElectronica tiendaElectronica;
    public final Cine cine;
    public final PatioComidas patioComidas;
    public final Estacionamiento estacionamiento;

    /**
     * Constructor que inicializa la capacidad total del centro comercial y
     * crea las distintas tiendas, cine, patio de comidas y estacionamiento.
     */
    public CentroComercial() {
        capacidadTotal = new Semaphore(100, true); // capacidad del centro comercial

        tiendaRopa = new TiendaRopa(20);
        jugueteria = new Jugueteria(20);
        tiendaDeportiva = new TiendaDeportiva(20);
        tiendaElectronica = new TiendaElectronica(20);

        cine = new Cine(30);
        patioComidas = new PatioComidas(30);
        estacionamiento = new Estacionamiento(50);

    }

    /**
     * Método que permite a una persona ingresar al centro comercial,
     * esperando si la capacidad máxima ha sido alcanzada.
     *
     * @param personaId identificador de la persona que intenta ingresar al centro comercial
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void ingresar(String personaId) throws InterruptedException {
        capacidadTotal.acquire();
        System.out.println("La " + personaId + " entro al Centro Comercial");
    }

    /**
     * Método que permite a una persona salir del centro comercial,
     * liberando un lugar en el semáforo de capacidad.
     *
     * @param personaId es el identificador de la persona que sale del centro comercial
     */
    public void salir(String personaId) {
        System.out.println("La " + personaId + " salio del Centro Comercial");
        capacidadTotal.release();
    }

}


