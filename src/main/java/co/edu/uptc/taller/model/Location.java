package co.edu.uptc.taller.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa una ubicación dentro del sistema, identificada por un nombre único.
 *
 * <p>Esta clase es utilizada como vértice en el grafo de entregas y es serializable,
 * lo que permite su persistencia en formato JSON gracias a las anotaciones de Jackson.</p>
 *
 * @author
 * @version 1.0
 */
public class Location {

    /**
     * Nombre que identifica la ubicación de manera única.
     */
    private String name;

    /**
     * Crea una nueva instancia de {@code Location} con el nombre especificado.
     *
     * <p>Este constructor utiliza la anotación {@link JsonCreator} para habilitar
     * la deserialización desde JSON.</p>
     *
     * @param name El nombre de la ubicación.
     * @throws NullPointerException si {@code name} es {@code null}.
     */
    @JsonCreator
    public Location(@JsonProperty("name") String name) {
        if (name == null) {
            throw new NullPointerException("El nombre de la ubicación no puede ser nulo.");
        }
        this.name = name;
    }

    /**
     * Obtiene el nombre de la ubicación.
     *
     * @return El nombre de la ubicación.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece un nuevo nombre para la ubicación.
     *
     * @param name El nuevo nombre de la ubicación.
     * @throws NullPointerException si {@code name} es {@code null}.
     */
    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("El nombre de la ubicación no puede ser nulo.");
        }
        this.name = name;
    }

    /**
     * Compara esta ubicación con otro objeto para determinar si son iguales.
     *
     * <p>Dos ubicaciones se consideran iguales si sus nombres son iguales.</p>
     *
     * @param o El objeto a comparar.
     * @return {@code true} si los objetos son iguales; {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Mismo objeto
        if (o == null || getClass() != o.getClass()) return false; // Clase diferente
        Location location = (Location) o;
        return Objects.equals(name, location.name);
    }

    /**
     * Calcula el código hash para esta ubicación.
     *
     * <p>El código hash se basa en el nombre de la ubicación.</p>
     *
     * @return El código hash de la ubicación.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Devuelve una representación en forma de cadena de esta ubicación.
     *
     * <p>El formato de la cadena es simplemente el nombre de la ubicación.</p>
     *
     * @return Una representación en forma de cadena de esta ubicación.
     */
    @Override
    public String toString() {
        return name;
    }
}
