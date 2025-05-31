package Centro_Comercial.Comercios;

import Centro_Comercial.Comercio;

/**
 * Clase Jugueteria
 * Representa una juguetería dentro del centro comercial, hereda de Comercio.
 */
public class Jugueteria extends Comercio {
    /**
     * Constructor que crea una juguetería con capacidad máxima especificada.
     *
     * @param capacidad capacidad máxima de personas que pueden ingresar simultáneamente
     */
    public Jugueteria(int capacidad) {
        super("Jugueteria", capacidad);
    }

}
