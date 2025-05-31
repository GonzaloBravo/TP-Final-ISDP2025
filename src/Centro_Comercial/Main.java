/*
Ingeniería en Computación - UNRN Sede Andina - Introducción a los Sistemas Distribuidos y Paralelos -
Trabajo Final - Simulador de Gestión para Centros Comerciales -
Alumno: GONZALO EZEQUIEL BRAVO - Año 2025 -
*/

package Centro_Comercial;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Main
 * Punto de entrada del sistema del centro comercial. Simula la concurrencia de personas,
 * vehículos y el control del cine con múltiples hilos. Además, maneja un cierre ordenado.
 */
public class Main {
    public static void main(String[] args) {
        // Crear la instancia principal del centro comercial
        CentroComercial centro = new CentroComercial();
        Estacionamiento estacionamiento = centro.estacionamiento;

        // Listas para almacenar los hilos de personas y vehículos
        List<Thread> personas = new ArrayList<>();
        List<Thread> vehiculos = new ArrayList<>();

        // Crear y lanzar hilos que representan personas que visitan el centro
        for (int i = 0; i < 120; i++) {
            String nombre = "Persona " + (i + 1);
            Thread t = new Persona(nombre, centro);
            personas.add(t);
            t.start();
        }

        /// Crear y lanzar hilos que representan vehículos que intentan estacionar
        for (int i = 0; i < 60; i++) {
            String id = "Vehiculo " + (i + 1);
            Thread t = new Vehiculo(id, estacionamiento, centro.estadisticas);
            vehiculos.add(t);
            t.start();
        }

        // Iniciar el hilo del acomodador del cine (controla la lógica de proyección)
        AcomodadorCine acomodador = new AcomodadorCine(centro.cine);
        acomodador.start();

        // Agregar un Shutdown Hook que se activa cuando se cierra el programa
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[SISTEMA] Señal TERM recibida. Cerrando Centro Comercial...\n");

            // Interrumpir todos los hilos de personas y vehículos
            for (int i = 0; i < personas.size(); i++) {
                personas.get(i).interrupt();
            }
            for (int i = 0; i < vehiculos.size(); i++) {
                vehiculos.get(i).interrupt();
            }
            acomodador.interrupt(); // Interrumpir al acomodador

            // Esperar a que todos los hilos terminen antes de continuar
            try {
                for (int i = 0; i < personas.size(); i++) {
                    personas.get(i).join();
                }
                for (int i = 0; i < vehiculos.size(); i++) {
                    vehiculos.get(i).join();
                }

                acomodador.join();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaurar estado de interrupción si ocurre mientras esperamos
            }

            // Una vez que todos los hilos terminaron, mostrar estadísticas finales
            System.out.println();
            centro.estadisticas.imprimirEstadisticas();
        }));
    }

}
