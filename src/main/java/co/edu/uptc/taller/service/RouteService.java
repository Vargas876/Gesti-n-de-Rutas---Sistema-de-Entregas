package co.edu.uptc.taller.service;

import java.util.List;
import java.util.logging.Logger;

import co.edu.uptc.taller.model.DeliveryGraph;
import co.edu.uptc.taller.model.Location;

/**
 * Servicio encargado de calcular rutas, costos y tiempos.
 */
public class RouteService {

    private static final Logger logger = Logger.getLogger(RouteService.class.getName());
    private static final double AVERAGE_SPEED_KMH = 60.0; // Velocidad promedio en km/h
    private static final double COST_PER_KM = 1500.0; // Costo por kilómetro

    private DeliveryGraph deliveryGraph;

    /**
     * Constructor que recibe una instancia de DeliveryGraph.
     *
     * @param deliveryGraph La instancia del grafo de entregas.
     */
    public RouteService(DeliveryGraph deliveryGraph) {
        this.deliveryGraph = deliveryGraph;
    }

    /**
     * Calcula la ruta más corta entre dos ubicaciones.
     *
     * @param source Ubicación de origen.
     * @param target Ubicación de destino.
     * @return Objeto RouteResult con la ruta, distancia, costo y tiempo.
     */
    public RouteResult calculateRoute(Location source, Location target) {
        logger.info("Calculando ruta de " + source.getName() + " a " + target.getName());

        if (source.equals(target)) {
            logger.warning("Origen y destino son la misma ubicación.");
            return new RouteResult(null, 0.0, 0.0, 0.0);
        }

        List<Location> path = deliveryGraph.getShortestPath(source, target);
        double distance = deliveryGraph.getShortestPathWeight(source, target);
        double cost = distance * COST_PER_KM;
        double time = distance / AVERAGE_SPEED_KMH;

        if (path != null) {
            logger.info("Ruta calculada: " + formatPath(path) + " | Distancia: " + distance + " km | Costo: " + cost + " COP | Tiempo: " + time + " h");
            return new RouteResult(path, distance, cost, time);
        } else {
            logger.warning("No se encontró una ruta entre " + source.getName() + " y " + target.getName());
            return new RouteResult(null, distance, cost, time);
        }
    }

    /**
     * Formatea la ruta para su registro.
     *
     * @param path Lista de ubicaciones que forman la ruta.
     * @return String formateado de la ruta.
     */
    private String formatPath(List<Location> path) {
        StringBuilder sb = new StringBuilder();
        for (Location loc : path) {
            sb.append(loc.getName()).append(" -> ");
        }
        if (sb.length() >= 4) {
            sb.setLength(sb.length() - 4); // Eliminar la última flecha
        }
        return sb.toString();
    }

    /**
     * Clase que encapsula el resultado del cálculo de la ruta.
     */
    public static class RouteResult {
        private List<Location> path;
        private double distance;
        private double cost;
        private double time;

        /**
         * Constructor de RouteResult.
         *
         * @param path     Lista de ubicaciones que forman la ruta.
         * @param distance Distancia total de la ruta en kilómetros.
         * @param cost     Costo total de la ruta en COP.
         * @param time     Tiempo estimado de la ruta en horas.
         */
        public RouteResult(List<Location> path, double distance, double cost, double time) {
            this.path = path;
            this.distance = distance;
            this.cost = cost;
            this.time = time;
        }

        // Getters

        public List<Location> getPath() {
            return path;
        }

        public double getDistance() {
            return distance;
        }

        public double getCost() {
            return cost;
        }

        public double getTime() {
            return time;
        }
    }
}
