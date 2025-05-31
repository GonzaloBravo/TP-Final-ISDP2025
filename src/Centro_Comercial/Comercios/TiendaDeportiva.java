package Centro_Comercial.Comercios;

import Centro_Comercial.Comercio;

/**
 * Clase TiendaDeportiva
 * Representa una tienda de artículos deportivos dentro del centro comercial, hereda de Comercio.
 */
public class TiendaDeportiva extends Comercio {
    /**
     * Constructor que crea una tienda deportiva con capacidad máxima especificada.
     *
     * @param capacidad capacidad máxima de personas que pueden ingresar simultáneamente
     */
    public TiendaDeportiva(int capacidad) {
        super("Tienda Deportiva", capacidad);
    }

}
