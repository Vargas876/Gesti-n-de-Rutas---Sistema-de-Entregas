package co.edu.uptc.taller.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.edu.uptc.taller.model.DeliveryGraph;
import co.edu.uptc.taller.model.Location;
import co.edu.uptc.taller.persistence.GraphPersistence;
import co.edu.uptc.taller.service.GraphInitializer;
import co.edu.uptc.taller.service.GraphRenderer;
import co.edu.uptc.taller.service.RouteService;
import co.edu.uptc.taller.service.RouteService.RouteResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

/**
 * Controlador principal de la aplicación, encargado de gestionar la interacción entre la vista,
 * el modelo y los servicios relacionados con el cálculo y la visualización de rutas.
 *
 * <p>Este controlador maneja eventos de la interfaz de usuario, actualiza los datos mostrados y
 * utiliza servicios para procesar la lógica del negocio.</p>
 *
 * @author
 * @version 1.0
 */
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @FXML
    private ComboBox<Location> sourceComboBox;

    @FXML
    private ComboBox<Location> targetComboBox;

    @FXML
    private TextArea pathTextArea;

    @FXML
    private TextArea costTextArea;

    @FXML
    private WebView graphView;

    @FXML
    private TextArea distanceTextArea;

    @FXML
    private TextArea timeTextArea;

    @FXML
    private ListView<GraphPersistence.RouteHistory> historyListView;

    private DeliveryGraph deliveryGraph;
    private GraphPersistence persistence;

    private GraphInitializer graphInitializer;
    private RouteService routeService;
    private GraphRenderer graphRenderer;

    /**
     * Método de inicialización que se ejecuta automáticamente al cargar el archivo FXML.
     *
     * <p>Configura los componentes del controlador, inicializa servicios, carga datos predeterminados
     * y renderiza el grafo inicial.</p>
     */
    @FXML
    public void initialize() {
        logger.info("Inicializando MainController.");


        deliveryGraph = new DeliveryGraph();
        persistence = new GraphPersistence();


        graphInitializer = new GraphInitializer(deliveryGraph);
        routeService = new RouteService(deliveryGraph);
        graphRenderer = new GraphRenderer(deliveryGraph);

        // Cargar datos predeterminados si el grafo está vacío
        if (deliveryGraph.getGraph().vertexSet().isEmpty()) {
            logger.warning("El grafo cargado está vacío. Cargando ubicaciones y rutas por defecto.");
            graphInitializer.initializeGraph();
        } else {
            logger.info("Grafo cargado con " + deliveryGraph.getGraph().vertexSet().size() + " ubicaciones.");
        }


        updateLocationComboBoxes();


        loadRouteHistory();


        graphRenderer.renderGraph(graphView, null);
    }

    /**
     * Actualiza las ComboBoxes con las ubicaciones disponibles en el grafo.
     */
    private void updateLocationComboBoxes() {
        logger.info("Actualizando ComboBoxes de ubicaciones.");
        ObservableList<Location> locations = FXCollections.observableArrayList(deliveryGraph.getGraph().vertexSet());
        sourceComboBox.setItems(locations);
        targetComboBox.setItems(locations);

        if (!locations.isEmpty()) {
            sourceComboBox.getSelectionModel().selectFirst();
            targetComboBox.getSelectionModel().selectLast();
            logger.info("ComboBoxes actualizadas con " + locations.size() + " ubicaciones.");
        } else {
            logger.warning("No hay ubicaciones disponibles para poblar las ComboBoxes.");
        }
    }

    /**
     * Carga el historial de rutas desde la persistencia y lo muestra en el ListView.
     */
    private void loadRouteHistory() {
        try {
            logger.info("Cargando historial de rutas.");
            List<GraphPersistence.RouteHistory> history = persistence.loadRouteHistory();
            historyListView.setItems(FXCollections.observableArrayList(history));
            historyListView.setCellFactory(lv -> new ListCell<GraphPersistence.RouteHistory>() {
                @Override
                protected void updateItem(GraphPersistence.RouteHistory item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%s → %s (%.1f km, %.2f h) - %s",
                                item.source, item.target, item.distance, item.time,
                                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.timestamp)));
                    }
                }
            });
            logger.info("Historial de rutas cargado exitosamente con " + history.size() + " entradas.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "No se pudo cargar el historial: " + e.getMessage(), e);
        }
    }

    /**
     * Maneja el evento de cálculo de ruta cuando el usuario hace clic en el botón correspondiente.
     *
     * <p>Valida las ubicaciones seleccionadas, calcula la ruta más corta y actualiza la interfaz de
     * usuario con los resultados. También guarda la ruta calculada en el historial.</p>
     */
    @FXML
    private void handleCalculateRoute() {
        logger.info("Calculando ruta.");
        Location source = sourceComboBox.getSelectionModel().getSelectedItem();
        Location target = targetComboBox.getSelectionModel().getSelectedItem();

        if (source == null || target == null) {
            logger.warning("Origen o destino no seleccionado.");
            pathTextArea.setText("Selecciona ambas ubicaciones.");
            distanceTextArea.setText("");
            costTextArea.setText("");
            timeTextArea.setText("");
            return;
        }

        if (source.equals(target)) {
            logger.warning("Origen y destino son la misma ubicación.");
            pathTextArea.setText("Origen y destino son la misma ubicación.");
            distanceTextArea.setText("0 km");
            costTextArea.setText("COP $0");
            timeTextArea.setText("0 h");
            return;
        }


        RouteResult routeResult = routeService.calculateRoute(source, target);
        List<Location> path = routeResult.getPath();
        double distance = routeResult.getDistance();
        double cost = routeResult.getCost();
        double time = routeResult.getTime();

        if (path != null) {

            StringBuilder pathStr = new StringBuilder();
            for (Location loc : path) {
                pathStr.append(loc.getName()).append(" -> ");
            }
            pathStr.setLength(pathStr.length() - 4);
            pathTextArea.setText(pathStr.toString());


            distanceTextArea.setText(String.format("%.1f km", distance));


            costTextArea.setText(String.format("COP $%.0f", cost));


            timeTextArea.setText(String.format("%.2f h", time));

            graphRenderer.renderGraph(graphView, path);
            logger.info("Ruta calculada: " + pathStr.toString() + " | Distancia: " + distance + " km | Costo: " + cost + " COP | Tiempo: " + time + " h");
        } else {
            logger.warning("No existe una ruta entre las ubicaciones seleccionadas.");
            pathTextArea.setText("No existe una ruta entre las ubicaciones seleccionadas.");
            distanceTextArea.setText("");
            costTextArea.setText("");
            timeTextArea.setText("");
        }


        try {
            GraphPersistence.RouteHistory history =
                    new GraphPersistence.RouteHistory(source, target, path, distance, cost, time);
            persistence.saveRouteHistory(history);
            historyListView.getItems().add(history);
            logger.info("Historial de ruta guardado.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "No se pudo guardar el historial: " + e.getMessage(), e);
        }
    }
}
