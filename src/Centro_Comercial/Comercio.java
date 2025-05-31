package Centro_Comercial;

import java.util.concurrent.Semaphore;

/**
 * Clase abstracta Comercio
 * Representa un comercio genérico dentro del centro comercial con control de capacidad.
 */
public abstract class Comercio {
    /**
     * Nombre del comercio.
     */
    protected final String nombre;
    /**
     * Semáforo que controla la capacidad máxima permitida dentro del comercio.
     */
    protected final Semaphore capacidad;

    /**
     * Constructor que inicializa un comercio con su nombre y capacidad máxima de personas.
     *
     * @param nombre nombre del comercio
     * @param capacidadMaxima cantidad máxima de personas permitidas simultáneamente
     */
    public Comercio(String nombre, int capacidadMaxima) {
        this.nombre = nombre;
        this.capacidad = new Semaphore(capacidadMaxima, true);
    }

    /**
     * Método que permite a una persona ingresar al comercio si hay espacio disponible.
     *
     * @param personaId identificador de la persona que ingresa
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void entrar(String personaId) throws InterruptedException {
        capacidad.acquire(); // espera si está lleno
        System.out.println("La " + personaId + " ingreso a " + nombre);
    }

    /**
     * Método que permite a una persona salir del comercio, liberando su lugar.
     *
     * @param personaId identificador de la persona que sale
     */
    public void salir(String personaId) {
        System.out.println("La " + personaId + " salio de " + nombre);
        capacidad.release(); // libera un lugar
    }

    /**
     * Método que devuelve el nombre del comercio.
     *
     * @return nombre del comercio
     */
    public String getNombre() {
        return nombre;
    }

}
