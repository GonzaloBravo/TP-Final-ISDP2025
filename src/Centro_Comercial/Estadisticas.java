package Centro_Comercial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase Estadísticas
 * Lleva un registro concurrente de las visitas a los locales del centro comercial
 * y de los autos que se estacionan.
 */
public class Estadisticas {
    // Mapa concurrente que asocia el nombre del local con la cantidad de visitas
    private final ConcurrentHashMap<String, AtomicInteger> visitasPorLocal;
    // Contador total de visitas a todos los locales
    private final AtomicInteger totalVisitas;
    // Contador de autos que estacionaron
    private AtomicInteger autosEstacionados = new AtomicInteger(0);

    /**
     * Constructor que inicializa los contadores
     */
    public Estadisticas() {
        this.visitasPorLocal = new ConcurrentHashMap<>();
        this.totalVisitas = new AtomicInteger(0);
        this.autosEstacionados = new AtomicInteger(0);
    }

    /**
     * Registra una visita a un local determinado
     *
     * @param local el nombre del local visitado
     */
    public void registrarVisita(String local) {
        // Si el local no existe, lo crea con contador 0 y luego incrementa
        visitasPorLocal.computeIfAbsent(local, k -> new AtomicInteger(0)).incrementAndGet();
        totalVisitas.incrementAndGet(); // Aumenta el total general de visitas
    }

    /**
     * Registra un auto estacionado
     */
    public void registrarAuto() {
        autosEstacionados.incrementAndGet();
    }


    /**
     * Imprime el informe estadístico por consola (para la clase main)
     */
    public void imprimirEstadisticas() {
        System.out.println("\n=== Informe de actividad diaria Centro Comercial ===");
        for (Map.Entry<String, AtomicInteger> entry : visitasPorLocal.entrySet()) {
            System.out.println("Visitas a " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Vehiculos que estacionaron: " + autosEstacionados.get());
    }

    /**
     * Devuelve un String con el informe (para la interfaz gráfica)
     *
     * @return informe con visitas y autos estacionados
     */
    public String obtenerInforme() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Informe de actividad diaria Centro Comercial ===\n\n");
        for (Map.Entry<String, AtomicInteger> entry : visitasPorLocal.entrySet()) {
            sb.append("Visitas a ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        sb.append("\n");  // Salto de línea extra para separar
        sb.append("Vehiculos que estacionaron: ").append(autosEstacionados.get()).append("\n");
        return sb.toString();
    }

}
