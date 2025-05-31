package Centro_Comercial;

import java.util.concurrent.locks.*;

/**
 * Clase Cine
 * Representa una sala de cine en la que se proyectan películas solo cuando la sala está llena.
 * Utiliza locks y condiciones para sincronizar el acceso entre múltiples hilos (personas y acomodador).
 */
public class Cine {
    private final int capacidad;  // Capacidad máxima de espectadores en la sala
    private int espectadoresEnSala = 0;  // Contador de espectadores actuales
    private boolean peliculaEnCurso = false;  // Indica si hay una película en curso

    private final Lock lock = new ReentrantLock(true); // Fair lock para orden justo
    private final Condition salaLlena = lock.newCondition(); // Condición para controlar sala llena
    private final Condition finPelicula = lock.newCondition(); // Condición para controlar inicio/fin de película

    private final AcomodadorCine acomodador;

    /**
     * Constructor del cine. Crea e inicia el hilo del acomodador.
     *
     * @param capacidad cantidad máxima de espectadores que puede tener la sala
     */
    public Cine(int capacidad) {
        this.capacidad = capacidad;
        this.acomodador = new AcomodadorCine(this);
        acomodador.start(); // El acomodador controla el inicio y fin de la película
    }

    /**
     * Método llamado por las personas para ingresar a la sala.
     * Esperan si la película está en curso o si la sala está llena.
     *
     * @param personaId identificador de la persona
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void entrarSala(String personaId) throws InterruptedException {
        lock.lock();
        try {
            while (peliculaEnCurso || espectadoresEnSala >= capacidad) {
                salaLlena.await(); // espera a que se reinicie o haya espacio
            }

            espectadoresEnSala++;
            System.out.println("La " + personaId + " entro a la sala. Total: " + espectadoresEnSala);

            // Si con este ingreso se llena la sala, se notifica al acomodador
            if (espectadoresEnSala == capacidad) {
                salaLlena.signal(); // notificar al acomodador que la sala está llena
            }

            // Espera a que comience la película
            while (!peliculaEnCurso) {
                finPelicula.await(); // espera que comience la película
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Método llamado por las personas una vez que la película comienza.
     * Esperan a que termine para salir.
     *
     * @param personaId identificador de la persona
     * @throws InterruptedException si el hilo es interrumpido mientras espera
     */
    public void verPelicula(String personaId) throws InterruptedException {
        // Aquí la persona ya entró y está viendo la película
        lock.lock();
        try { // Espera a que la película termine
            while (peliculaEnCurso) { // Espera si la película ya empezó o si la sala está llena
                finPelicula.await(); // Espera que termine la película
            }
            System.out.println("La " + personaId + " salio del cine");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Método ejecutado por el acomodador.
     * Espera a que la sala se llene, inicia la película, la reproduce y luego reinicia el estado.
     *
     * @throws InterruptedException si el hilo es interrumpido
     */
    public void iniciarPelicula() throws InterruptedException {
        // Bloque 1: Esperar que la sala se llene y comenzar la película
        lock.lock();
        try {
            while (espectadoresEnSala < capacidad) {
                salaLlena.await(); // espera a que se llene
            }

            System.out.println("Comienza la pelicula");
            peliculaEnCurso = true;
            finPelicula.signalAll(); // Permite a todos los espectadores comenzar a ver
        } finally {
            lock.unlock();  // Se libera el lock antes de dormir para no bloquear otros hilos
        }

        // Dormir sin lock para simular duración de la película
        Thread.sleep(5000);

        // Bloque 2: Finalizar la película y preparar para nueva ronda
        lock.lock();
        try {
            peliculaEnCurso = false;
            espectadoresEnSala = 0;
            System.out.println("La pelicula ha terminado");

            finPelicula.signalAll(); // Permite que salgan todas las personas
            salaLlena.signalAll();   // Permite que entren nuevos espectadores
        } finally {
            lock.unlock();
        }
    }

}
