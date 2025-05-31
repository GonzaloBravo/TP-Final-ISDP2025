package Centro_Comercial.Comercios;

import Centro_Comercial.Comercio;

/**
 * Clase TiendaRopa
 * Representa una tienda de ropa dentro del centro comercial, hereda de Comercio.
 */
public class TiendaRopa extends Comercio {
    /**
     * Constructor que crea una tienda de ropa con capacidad máxima especificada.
     *
     * @param capacidad capacidad máxima de personas que pueden ingresar simultáneamente
     */
    public TiendaRopa(int capacidad) {
        super("Tienda de Ropa", capacidad);
    }

}
