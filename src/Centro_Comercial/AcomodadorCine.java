package Centro_Comercial;

/**
 * Clase AcomodadorCine
 * Hilo encargado de controlar el ciclo de la película en el cine.
 * Espera que la sala se llene, inicia la película, espera que termine y luego reinicia el proceso.
 */
public class AcomodadorCine extends Thread {
    private final Cine cine;

    /**
     * Constructor que recibe la instancia del cine a controlar.
     *
     * @param cine la sala de cine a la que el acomodador pertenece
     */
    public AcomodadorCine(Cine cine) {
        this.cine = cine;
    }

    /**
     * Método run del hilo.
     * Ejecuta un ciclo infinito donde espera que la sala se llene,
     * inicia la película y espera que termine.
     */
    @Override
    public void run() {
        try {
            while (true) {
                cine.iniciarPelicula(); // Controla el inicio, duración y fin de la película en el cine
            }
        } catch (InterruptedException e) {
            System.out.println( "El Acomodador esta terminando sus tareas y saliendo del centro comercial...");
            Thread.currentThread().interrupt(); // Reestablece el estado de interrupción
        }
    }

}
