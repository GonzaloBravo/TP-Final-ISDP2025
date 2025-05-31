package Centro_Comercial.Comercios;

import Centro_Comercial.Comercio;

/**
 * Clase TiendaElectronica
 * Representa una tienda de productos electrónicos dentro del centro comercial, hereda de Comercio.
 */
public class TiendaElectronica extends Comercio {
    /**
     * Constructor que crea una tienda electrónica con capacidad máxima especificada.
     *
     * @param capacidad capacidad máxima de personas que pueden ingresar simultáneamente
     */
    public TiendaElectronica(int capacidad) {
        super("Tienda de Electronica", capacidad);
    }

}
