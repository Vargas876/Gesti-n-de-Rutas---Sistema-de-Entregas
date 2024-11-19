package co.edu.uptc.taller.model;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;

/**
 * La clase {@code DeliveryGraph} representa un grafo ponderado simple que modela las rutas de entrega
 * entre diferentes ubicaciones. Utiliza la biblioteca JGraphT para gestionar la estructura del grafo
 * y calcular rutas óptimas entre ubicaciones.
 *
 * <p>Esta clase encapsula tanto los datos del grafo como las operaciones necesarias para manipular
 * el grafo, como añadir ubicaciones, establecer rutas entre ellas y calcular la ruta más corta
 * utilizando el algoritmo de Dijkstra.</p>
 *
 * @author
 * @version 1.0
 */
public class DeliveryGraph {
    /**
     * Representa el grafo de entregas, donde los vértices son ubicaciones y las aristas son rutas
     * con pesos que corresponden a las distancias entre las ubicaciones.
     */
    private SimpleWeightedGraph<Location, DefaultWeightedEdge> graph;

    /**
     * Instancia del algoritmo de Dijkstra para calcular la ruta más corta en el grafo.
     */
    private DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstraAlg;

    /**
     * Construye una nueva instancia de {@code DeliveryGraph}, inicializando el grafo y el algoritmo
     * de Dijkstra.
     *
     * <p>El grafo es un {@link SimpleWeightedGraph} que no permite bucles ni aristas paralelas.
     * El algoritmo de Dijkstra se configura para operar sobre este grafo.</p>
     */
    public DeliveryGraph() {
        graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        dijkstraAlg = new DijkstraShortestPath<>(graph);
    }

    /**
     * Añade una nueva ubicación al grafo.
     *
     * <p>Si la ubicación ya existe en el grafo, no se añade de nuevo.</p>
     *
     * @param location La instancia de {@code Location} que representa la nueva ubicación a añadir.
     * @throws NullPointerException si {@code location} es {@code null}.
     */
    public void addLocation(Location location) {
        if (location == null) {
            throw new NullPointerException("La ubicación no puede ser nula.");
        }
        graph.addVertex(location);
    }

    /**
     * Establece una ruta entre dos ubicaciones con una distancia específica.
     *
     * <p>Si ya existe una arista entre las ubicaciones dadas, el peso de la arista se actualiza con
     * la nueva distancia. Si no, se crea una nueva arista con el peso proporcionado.</p>
     *
     * @param from     La ubicación de origen.
     * @param to       La ubicación de destino.
     * @param distance La distancia entre las ubicaciones en kilómetros. Debe ser un valor positivo.
     * @throws IllegalArgumentException si {@code distance} es negativo.
     * @throws NullPointerException     si {@code from} o {@code to} son {@code null}.
     */
    public void addRoute(Location from, Location to, double distance) {
        if (from == null || to == null) {
            throw new NullPointerException("Las ubicaciones de origen y destino no pueden ser nulas.");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa.");
        }

        DefaultWeightedEdge edge = graph.addEdge(from, to);
        if (edge != null) {
            graph.setEdgeWeight(edge, distance);
        }
    }

    /**
     * Obtiene la ruta más corta entre dos ubicaciones utilizando el algoritmo de Dijkstra.
     *
     * @param source La ubicación de origen.
     * @param target La ubicación de destino.
     * @return Una lista de {@code Location} que representa la ruta más corta desde {@code source} hasta {@code target},
     *         o {@code null} si no existe una ruta entre las dos ubicaciones.
     * @throws NullPointerException si {@code source} o {@code target} son {@code null}.
     */
    public List<Location> getShortestPath(Location source, Location target) {
        if (source == null || target == null) {
            throw new NullPointerException("Las ubicaciones de origen y destino no pueden ser nulas.");
        }

        GraphPath<Location, DefaultWeightedEdge> path = dijkstraAlg.getPath(source, target);
        return path != null ? path.getVertexList() : null;
    }

    /**
     * Obtiene la distancia total de la ruta más corta entre dos ubicaciones.
     *
     * @param source La ubicación de origen.
     * @param target La ubicación de destino.
     * @return La distancia total en kilómetros de la ruta más corta, o {@code Double.POSITIVE_INFINITY}
     *         si no existe una ruta entre las dos ubicaciones.
     * @throws NullPointerException si {@code source} o {@code target} son {@code null}.
     */
    public double getShortestPathWeight(Location source, Location target) {
        if (source == null || target == null) {
            throw new NullPointerException("Las ubicaciones de origen y destino no pueden ser nulas.");
        }

        GraphPath<Location, DefaultWeightedEdge> path = dijkstraAlg.getPath(source, target);
        return path != null ? path.getWeight() : Double.POSITIVE_INFINITY;
    }

    /**
     * Obtiene el grafo actual de entregas.
     *
     * @return El objeto {@link SimpleWeightedGraph} que representa el grafo de entregas.
     */
    public SimpleWeightedGraph<Location, DefaultWeightedEdge> getGraph() {
        return graph;
    }
}
