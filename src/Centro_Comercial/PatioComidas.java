package Centro_Comercial;

import java.util.concurrent.Semaphore;

/**
 * Clase PatioComidas
 * Representa el patio de comidas del centro comercial, con una cantidad limitada de mesas disponibles.
 * Controla el ingreso y salida de personas utilizando un semáforo para manejar la concurrencia.
 */
public class PatioComidas {
    private final Semaphore mesas;
    private int personasComiendo = 0;

    /**
     * Constructor que inicializa el patio de comidas con una capacidad máxima de mesas.
     *
     * @param capacidad número máximo de personas que pueden sentarse a comer al mismo tiempo
     */
    public PatioComidas(int capacidad) {
        this.mesas = new Semaphore(capacidad, true); // true para asegurar orden de llegada
    }

    /**
     * Método para que una persona se siente a comer.
     * Espera si no hay mesas disponibles.
     *
     * @param personaId identificador de la persona
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void ingresar(String personaId) throws InterruptedException {
        mesas.acquire();
        synchronized (this) {
            personasComiendo++;
        }
        System.out.println("La " + personaId + " se sento a comer. Mesas ocupadas: " + personasComiendo);
    }

    /**
     * Método para que una persona termine de comer y deje libre su lugar.
     *
     * @param personaId identificador de la persona
     */
    public void salir(String personaId) {
        synchronized (this) {
            personasComiendo--;
        }
        mesas.release(); // Libera una mesa
        System.out.println("La " + personaId + " termino de comer. Mesas ocupadas: " + personasComiendo);
    }

}
