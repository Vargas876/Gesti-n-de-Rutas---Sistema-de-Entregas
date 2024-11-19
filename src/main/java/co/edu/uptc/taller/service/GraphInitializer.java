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
     * Método para inicializar el grafo con ubicaciones y rutas predeterminadas.
     */
    public void initializeGraph() {
        logger.info("Inicializando el grafo con ubicaciones y rutas predeterminadas.");

        // Crear ubicaciones reales de Boyacá
        Location tunja = new Location("Tunja");
        Location duitama = new Location("Duitama");
        Location sogamoso = new Location("Sogamoso");
        Location chiquinquirá = new Location("Chiquinquirá");
        Location ramiriquí = new Location("Ramiriquí");
        Location zipaquirá = new Location("Zipaquirá");
        Location miraflores = new Location("Miraflores");
        Location paipa = new Location("Paipa");
        Location samacá = new Location("Samacá");
        Location moniquira = new Location("Moniquirá");
        Location barbosa = new Location("Barbosa");
        Location topaga = new Location("Tópaga");
        Location nobsa = new Location("Nobsa");
        Location villaLeyva = new Location("Villa de Leyva");
        Location motavita = new Location("Motavita");
        Location garagoa = new Location("Garagoa");
        Location socota = new Location("Socotá");
        Location chiscas = new Location("Chiscas");
        Location mongui = new Location("Monguí");

        // Añadir ubicaciones al grafo
        deliveryGraph.addLocation(tunja);
        deliveryGraph.addLocation(duitama);
        deliveryGraph.addLocation(sogamoso);
        deliveryGraph.addLocation(chiquinquirá);
        deliveryGraph.addLocation(ramiriquí);
        deliveryGraph.addLocation(zipaquirá);
        deliveryGraph.addLocation(miraflores);
        deliveryGraph.addLocation(paipa);
        deliveryGraph.addLocation(samacá);
        deliveryGraph.addLocation(moniquira);
        deliveryGraph.addLocation(barbosa);
        deliveryGraph.addLocation(topaga);
        deliveryGraph.addLocation(nobsa);
        deliveryGraph.addLocation(villaLeyva);
        deliveryGraph.addLocation(motavita);
        deliveryGraph.addLocation(garagoa);
        deliveryGraph.addLocation(socota);
        deliveryGraph.addLocation(chiscas);
        deliveryGraph.addLocation(mongui);

        logger.fine("Ubicaciones predeterminadas añadidas al grafo.");

        // Rutas actualizadas con distancias reales (en kilómetros)
        addRouteWithCost(tunja, duitama, 54.5); // Tunja a Duitama
        addRouteWithCost(tunja, sogamoso, 70.6); // Tunja a Sogamoso
        addRouteWithCost(tunja, chiquinquirá, 77.2); // Tunja a Chiquinquirá
        addRouteWithCost(duitama, miraflores, 149.2); // Duitama a Miraflores
        addRouteWithCost(duitama, sogamoso, 18); // Nueva ruta Duitama a Sogamoso
        addRouteWithCost(sogamoso, ramiriquí, 98.8); // Sogamoso a Ramiriquí
        addRouteWithCost(chiquinquirá, zipaquirá, 94.5); // Chiquinquirá a Zipaquirá
        addRouteWithCost(miraflores, paipa, 134.8); // Miraflores a Paipa
        addRouteWithCost(ramiriquí, samacá, 51.6); // Ramiriquí a Samacá
        addRouteWithCost(paipa, samacá, 70); // Paipa a Samacá
        addRouteWithCost(zipaquirá, samacá, 120.6); // Zipaquirá a Samacá VER
        addRouteWithCost(tunja, moniquira, 60.6); // Tunja a Moniquirá
        addRouteWithCost(moniquira, barbosa, 10.1); // Moniquirá a Barbosa
        addRouteWithCost(barbosa, topaga, 140.3); // Barbosa a Tópaga
        addRouteWithCost(topaga, nobsa, 17.4); // Tópaga a Nobsa
        addRouteWithCost(nobsa, villaLeyva, 105.5); // Nobsa a Villa de Leyva
        addRouteWithCost(villaLeyva, motavita, 41.8); // Villa de Leyva a Motavita
        addRouteWithCost(motavita, garagoa, 82.4); // Motavita a Garagoa
        addRouteWithCost(garagoa, socota, 206.4); // Garagoa a Socotá
        addRouteWithCost(socota, chiscas, 129.5); // Socotá a Chiscas
        addRouteWithCost(chiscas, mongui, 229.1); // Chiscas a Monguí
        addRouteWithCost(mongui, tunja, 83); // Monguí a Tunja
        addRouteWithCost(sogamoso, garagoa, 146.2); // Sogamoso a Garagoa
        addRouteWithCost(zipaquirá, nobsa, 187.2); // Zipaquirá a Nobsa
        addRouteWithCost(paipa, topaga, 42.2); // Paipa a Tópaga
        addRouteWithCost(paipa, duitama, 15.0);  // Distancia real Paipa-Duitama
        addRouteWithCost(topaga, sogamoso, 12.0); // Distancia real Tópaga-Sogamoso
        addRouteWithCost(miraflores, villaLeyva, 133.2); // Miraflores a Villa de Leyva

        addRouteWithCost(tunja, motavita, 8.5);  // Tunja - Motavita (muy cercanos)
        addRouteWithCost(tunja, samacá, 32.0);   // Tunja - Samacá
        addRouteWithCost(tunja, villaLeyva, 45.0); // Tunja - Villa de Leyva
        addRouteWithCost(tunja, ramiriquí, 27.3); // Tunja - Ramiriquí
        addRouteWithCost(duitama, nobsa, 7.2);    // Duitama - Nobsa (muy cercanos)
        addRouteWithCost(duitama, paipa, 15.0);   // Duitama - Paipa
        addRouteWithCost(sogamoso, mongui, 19.2); // Sogamoso - Monguí
        addRouteWithCost(sogamoso, nobsa, 9.8);   // Sogamoso - Nobsa
        addRouteWithCost(sogamoso, topaga, 12.0); // Sogamoso - Tópaga
        addRouteWithCost(chiquinquirá, villaLeyva, 37.8); // Chiquinquirá - Villa de Leyva
        addRouteWithCost(villaLeyva, samacá, 30.5); // Villa de Leyva - Samacá
        addRouteWithCost(nobsa, topaga, 17.4);    // Nobsa - Tópaga
        addRouteWithCost(ramiriquí, garagoa, 45.6); // Ramiriquí - Garagoa
        addRouteWithCost(garagoa, miraflores, 25.3); // Garagoa - Miraflores
        addRouteWithCost(moniquira, villaLeyva, 42.7); // Moniquirá - Villa de Leyva
        addRouteWithCost(socota, mongui, 89.4);   // Socotá - Monguí
        addRouteWithCost(paipa, villaLeyva, 58.3); // Paipa - Villa de Leyva
        addRouteWithCost(barbosa, moniquira, 10.1); // Barbosa - Moniquirá
        addRouteWithCost(motavita, samacá, 25.8);  // Motavita - Samacá
        addRouteWithCost(mongui, topaga, 15.6);    // Monguí - Tópaga

        logger.info("Rutas predeterminadas añadidas al grafo.");
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
