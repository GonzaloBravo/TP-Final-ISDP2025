package Centro_Comercial;

import java.util.concurrent.Semaphore;

/**
 * Clase Estacionamiento
 * Representa el estacionamiento del centro comercial con una capacidad limitada de lugares.
 * Utiliza un semáforo para controlar el acceso concurrente a los espacios disponibles.
 */
public class Estacionamiento {
    private final Semaphore lugares;
    private int autosEstacionados = 0;

    /**
     * Constructor que inicializa el estacionamiento con una capacidad máxima.
     *
     * @param capacidad número máximo de vehículos que pueden estacionar simultáneamente
     */
    public Estacionamiento(int capacidad) {
        this.lugares = new Semaphore(capacidad, true);
    }

    /**
     * Método para que un vehículo ingrese al estacionamiento.
     * Espera si no hay lugares disponibles.
     *
     * @param vehiculoId identificador del vehículo
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void ingresar(String vehiculoId) throws InterruptedException {
        lugares.acquire(); // Espera hasta que haya lugar
        synchronized (this) {
            autosEstacionados++;
        }
        System.out.println("El " + vehiculoId + " estaciono. Lugares ocupados: " + autosEstacionados);
    }

    /**
     * Método para que un vehículo salga del estacionamiento.
     * Libera un lugar y actualiza el contador.
     *
     * @param vehiculoId identificador del vehículo
     */
    public void salir(String vehiculoId) {
        synchronized (this) {
            autosEstacionados--;
        }
        lugares.release(); // Libera un lugar
        System.out.println("El " + vehiculoId + " salio del estacionamiento. Lugares ocupados: " + autosEstacionados);
    }

}
