package Centro_Comercial;

/**
 * Clase Vehículo
 * Representa un vehículo que ingresa al estacionamiento del centro comercial y permanece allí un tiempo determinado.
 */
public class Vehiculo extends Thread {
    /**
     * Identificador del vehículo.
     */
    private final String id;
    /**
     * Referencia al estacionamiento del centro comercial.
     */
    private final Estacionamiento estacionamiento;
    /**
     * Referencia al módulo de estadísticas para registrar el ingreso del vehículo.
     */
    private final Estadisticas estadisticas;

    /**
     * Constructor de la clase Vehículo.
     *
     * @param id identificador del vehículo (ej. "Auto 1")
     * @param estacionamiento objeto que representa el estacionamiento
     * @param estadisticas objeto que permite registrar estadísticas
     */
    public Vehiculo(String id, Estacionamiento estacionamiento, Estadisticas estadisticas) {
        this.id = id;
        this.estacionamiento = estacionamiento;
        this.estadisticas = estadisticas;
    }


    /**
     * Método que ejecuta el comportamiento del vehículo al ser iniciado como hilo.
     * Simula el ingreso al estacionamiento, la permanencia por un tiempo aleatorio, y su posterior salida.
     */
    @Override
    public void run() {
        try {
            estacionamiento.ingresar(id); // Intenta ingresar al estacionamiento
            estadisticas.registrarAuto();  // Registro de ingreso de vehículos en Estadísticas
            Thread.sleep((int)(Math.random() * 5000 + 2000)); // Tiempo que permanece estacionado, de 2 a 7 segundos
            estacionamiento.salir(id);  // sale del estacionamiento
        } catch (InterruptedException e) { // Manejo de interrupciones del hilo
            System.out.println("El " + id + " esta terminado sus tareas y saliendo del centro comercial...");
            Thread.currentThread().interrupt();
        }
    }

}
