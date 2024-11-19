package co.edu.uptc.taller.service;

import java.util.logging.Logger;

import co.edu.uptc.taller.model.DeliveryGraph;
import co.edu.uptc.taller.model.Location;

/**
 * Clase encargada de inicializar el grafo con ubicaciones y rutas predeterminadas.
 */
public class GraphInitializer {

    private static final Logger logger = Logger.getLogger(GraphInitializer.class.getName());

    private DeliveryGraph deliveryGraph;

    /**
     * Constructor que recibe una instancia de DeliveryGraph.
     *
     * @param deliveryGraph La instancia del grafo de entregas.
     */
    public GraphInitializer(DeliveryGraph deliveryGraph) {
        this.deliveryGraph = deliveryGraph;
    }
    /**
     * Método para inicializar el grafo con ubicaciones y rutas optimizadas.
     * Incluye rutas adicionales entre ciudades cercanas para mejor conectividad local.
     */
    public void initializeGraph() {
        logger.info("Inicializando el grafo con ubicaciones y rutas optimizadas.");

        // Crear ubicaciones principales de Boyacá
        Location tunja = new Location("Tunja");
        Location duitama = new Location("Duitama");
        Location sogamoso = new Location("Sogamoso");
        Location paipa = new Location("Paipa");
        Location samacá = new Location("Samacá");
        Location villaLeyva = new Location("Villa de Leyva");
        Location motavita = new Location("Motavita");
        Location ramiriquí = new Location("Ramiriquí");
        Location garagoa = new Location("Garagoa");
        Location miraflores = new Location("Miraflores");
        Location nobsa = new Location("Nobsa");
        Location topaga = new Location("Tópaga");
        Location mongui = new Location("Monguí");
        Location chiquinquirá = new Location("Chiquinquirá");
        Location moniquira = new Location("Moniquirá");
        Location tibasosa = new Location("Tibasosa");
        Location sotaquirá = new Location("Sotaquirá");
        Location tuta = new Location("Tuta");
        Location siachoque = new Location("Siachoque");
        Location toca = new Location("Toca");
        Location soracá = new Location("Soracá");
        Location combita = new Location("Cómbita");
        Location oicatá = new Location("Oicatá");
        Location firavitoba = new Location("Firavitoba");
        Location iza = new Location("Iza");


        // Añadir todas las ubicaciones al grafo
        deliveryGraph.addLocation(tunja);
        deliveryGraph.addLocation(duitama);
        deliveryGraph.addLocation(sogamoso);
        deliveryGraph.addLocation(paipa);
        deliveryGraph.addLocation(samacá);
        deliveryGraph.addLocation(villaLeyva);
        deliveryGraph.addLocation(motavita);
        deliveryGraph.addLocation(ramiriquí);
        deliveryGraph.addLocation(garagoa);
        deliveryGraph.addLocation(miraflores);
        deliveryGraph.addLocation(nobsa);
        deliveryGraph.addLocation(topaga);
        deliveryGraph.addLocation(mongui);
        deliveryGraph.addLocation(chiquinquirá);
        deliveryGraph.addLocation(moniquira);
        deliveryGraph.addLocation(tibasosa);
        deliveryGraph.addLocation(sotaquirá);
        deliveryGraph.addLocation(tuta);
        deliveryGraph.addLocation(siachoque);
        deliveryGraph.addLocation(toca);
        deliveryGraph.addLocation(soracá);
        deliveryGraph.addLocation(combita);
        deliveryGraph.addLocation(oicatá);
        deliveryGraph.addLocation(firavitoba);
        deliveryGraph.addLocation(iza);


        logger.fine("Ubicaciones principales y secundarias añadidas al grafo.");

        // Rutas desde Tunja (capital) - Conexiones locales
        addBidirectionalRoute(tunja, motavita, 4.7);
        addBidirectionalRoute(tunja, oicatá, 11.9);
        addBidirectionalRoute(tunja, combita, 15);
        addBidirectionalRoute(tunja, soracá, 8.5);
        addBidirectionalRoute(tunja, siachoque, 19.4);
        addBidirectionalRoute(tunja, toca, 30.9);
        addBidirectionalRoute(tunja, samacá, 30.2);
        addBidirectionalRoute(tunja, ramiriquí, 30.3);
        addBidirectionalRoute(tunja, villaLeyva, 38.1);
        addBidirectionalRoute(tunja, paipa, 41.5);
        addBidirectionalRoute(tunja, duitama, 54.5);


        // Corredor industrial Sogamoso-Duitama y alrededores
        addBidirectionalRoute(duitama, paipa, 13.2);
        addBidirectionalRoute(duitama, nobsa, 17.7);
        addBidirectionalRoute(duitama, tibasosa, 11.7);
        addBidirectionalRoute(duitama, sotaquirá, 31.9);
        addBidirectionalRoute(duitama, tuta, 32.4);


        // Conexiones Sogamoso y alrededores
        addBidirectionalRoute(sogamoso, nobsa, 8);
        addBidirectionalRoute(sogamoso, topaga, 18.5);
        addBidirectionalRoute(sogamoso, mongui, 19.7);
        addBidirectionalRoute(sogamoso, firavitoba, 10.4);
        addBidirectionalRoute(sogamoso, iza, 14.7);
        addBidirectionalRoute(sogamoso, tibasosa, 9.3);

        // Conexiones zona oeste
        addBidirectionalRoute(villaLeyva, samacá, 24.6);
        addBidirectionalRoute(villaLeyva, moniquira, 39.8);
        addBidirectionalRoute(villaLeyva, chiquinquirá, 45.5);

        // Conexiones zona sureste
        addBidirectionalRoute(ramiriquí, garagoa, 56.4);
        addBidirectionalRoute(garagoa, miraflores, 59.2);
        addBidirectionalRoute(ramiriquí, siachoque, 18.9);

        // Conexiones adicionales locales
        addBidirectionalRoute(mongui, topaga, 6.4);
        addBidirectionalRoute(nobsa, topaga, 19.1);
        addBidirectionalRoute(motavita, samacá, 32.9);
        addBidirectionalRoute(paipa, tuta, 19.7);
        addBidirectionalRoute(paipa, sotaquirá, 19.3);
        addBidirectionalRoute(tibasosa, firavitoba, 19.1);
        addBidirectionalRoute(firavitoba, iza, 7);
        addBidirectionalRoute(tuta, combita, 17.7);
        addBidirectionalRoute(combita, motavita, 15.3);
        addBidirectionalRoute(oicatá, combita, 8.7);
        addBidirectionalRoute(toca, siachoque, 15.5);
        addBidirectionalRoute(soracá, siachoque, 13.8);

        logger.info("Rutas optimizadas y conexiones locales añadidas al grafo.");
    }

    /**
     * Método auxiliar para añadir rutas bidireccionales.
     * Garantiza consistencia en las distancias en ambas direcciones.
     */
    private void addBidirectionalRoute(Location origin, Location destination, double distance) {
        addRouteWithCost(origin, destination, distance);
        addRouteWithCost(destination, origin, distance);
        logger.fine(String.format("Ruta bidireccional añadida entre %s y %s (%.1f km)",
                origin.getName(), destination.getName(), distance));
    }
    /**
     * Método para añadir rutas con distancia y costo al grafo.
     *
     * @param from     Ubicación de origen.
     * @param to       Ubicación de destino.
     * @param distance Distancia entre las ubicaciones en kilómetros.
     */
    private void addRouteWithCost(Location from, Location to, double distance) {
        double cost = distance * 1500; // Costo por kilómetro
        deliveryGraph.addRoute(from, to, distance);
        logger.fine("Ruta añadida: " + from.getName() + " -> " + to.getName() + " | Distancia: " + distance + " km | Costo: " + cost + " COP");
    }
}
