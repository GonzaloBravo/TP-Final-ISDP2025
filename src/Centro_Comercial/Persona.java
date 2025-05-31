package Centro_Comercial;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase Persona
 * Representa una persona que visita el centro comercial, recorriendo distintos locales de manera aleatoria.
 */
public class Persona extends Thread {
    /**
     * Nombre de la persona (identificador).
     */
    private final String nombre;
    /**
     * Referencia al centro comercial que visita la persona.
     */
    private final CentroComercial centroComercial;
    /**
     * Conjunto de lugares ya visitados por la persona.
     */
    private final Set<String> lugaresVisitados = new HashSet<>();

    /**
     * Constructor que inicializa la persona con su nombre y una referencia al centro comercial.
     *
     * @param nombre nombre o identificador de la persona
     * @param centroComercial instancia del centro comercial que la persona visitará
     */
    public Persona(String nombre, CentroComercial centroComercial) {
        this.nombre = nombre;
        this.centroComercial = centroComercial;
    }

    /**
     * Método que representa el comportamiento de la persona durante su visita al centro comercial.
     * Visita tiendas y otros lugares aleatoriamente hasta que decide irse.
     */
    @Override
    public void run() {
        try {
            centroComercial.ingresar(nombre); // intenta ingresar al centro comercial
            List<String> opciones = new ArrayList<>(Arrays.asList(   // Lista de lugares disponibles para visitar
                    "ropa", "jugueteria", "deportiva", "electronica", "cine", "comidas"
            ));

            while (!opciones.isEmpty()) {
                Thread.sleep(random(500, 1000)); // pausa antes de decidir
                String eleccion = elegirLugar(opciones);

                switch (eleccion) {
                    case "ropa":
                        centroComercial.tiendaRopa.entrar(nombre);
                        centroComercial.estadisticas.registrarVisita("Tienda de Ropa");
                        visitar(); // simula duración de la visita
                        centroComercial.tiendaRopa.salir(nombre);
                        break;
                    case "jugueteria":
                        centroComercial.jugueteria.entrar(nombre);
                        centroComercial.estadisticas.registrarVisita("Jugueteria");
                        visitar();
                        centroComercial.jugueteria.salir(nombre);
                        break;
                    case "deportiva":
                        centroComercial.tiendaDeportiva.entrar(nombre);
                        centroComercial.estadisticas.registrarVisita("Tienda Deportiva");
                        visitar();
                        centroComercial.tiendaDeportiva.salir(nombre);
                        break;
                    case "electronica":
                        centroComercial.tiendaElectronica.entrar(nombre);
                        centroComercial.estadisticas.registrarVisita("Tienda de Electronica");
                        visitar();
                        centroComercial.tiendaElectronica.salir(nombre);
                        break;
                    case "cine":
                        centroComercial.cine.entrarSala(nombre);
                        centroComercial.estadisticas.registrarVisita("Cine");
                        centroComercial.cine.verPelicula(nombre); // simula duración de la película
                        break;
                    case "comidas":
                        centroComercial.patioComidas.ingresar(nombre);
                        Thread.sleep((int)(Math.random() * 4000 + 2000)); // simula el tiempo que la persona ocupa mesa
                        centroComercial.estadisticas.registrarVisita("Patio de Comidas");
                        centroComercial.patioComidas.salir(nombre);
                        break;
                }


                lugaresVisitados.add(eleccion); // registra el lugar como visitado

                // Simula decisión aleatoria de irse, o si ya visitó todos los lugares
                if (Math.random() < 0.2 || lugaresVisitados.size() == 6) break;
            }

            centroComercial.salir(nombre); // sale del centro comercial

        } catch (InterruptedException e) { // Manejo de interrupción del hilo
            System.out.println("La " + nombre + " esta terminando sus tareas y saliendo del centro comercial...");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Método para elegir un lugar aleatoriamente de la lista de opciones restantes.
     *
     * @param opciones lista de lugares aún no visitados
     * @return lugar elegido para visitar
     */
    private String elegirLugar(List<String> opciones) {
        opciones.removeAll(lugaresVisitados); // elimina los lugares ya visitados
        int index = ThreadLocalRandom.current().nextInt(opciones.size());
        return opciones.get(index);
    }

    /**
     * Método que simula la duración de una visita a un lugar.
     *
     * @throws InterruptedException si el hilo es interrumpido
     */
    private void visitar() throws InterruptedException {
        Thread.sleep(random(1000, 2000)); // Simula duración de la visita. Duración entre 1 y 2 segundos
    }


    /**
     * Método que genera un número aleatorio entre un mínimo y un máximo.
     *
     * @param min valor mínimo
     * @param max valor máximo
     * @return número aleatorio en el rango [min, max]
     */
    private int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
