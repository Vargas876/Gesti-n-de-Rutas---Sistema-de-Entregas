package co.edu.uptc.taller.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import co.edu.uptc.taller.model.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de la persistencia de datos relacionados con rutas y ubicaciones.
 *
 * <p>Utiliza la biblioteca Jackson para serializar y deserializar datos en formato JSON,
 * almacenándolos en un archivo dentro de un directorio específico.</p>
 *
 * @author
 * @version 1.0
 */
public class GraphPersistence {

    /**
     * Ruta base donde se almacenarán los archivos de persistencia.
     */
    private static final String BASE_PATH = "src/main/resources/persistence";

    /**
     * Archivo donde se guarda el historial de rutas en formato JSON.
     */
    private static final String HISTORY_FILE = BASE_PATH + "/route_history.json";

    /**
     * Objeto para manejar operaciones de serialización/deserialización con Jackson.
     */
    private ObjectMapper mapper;

    /**
     * Logger para registrar mensajes relacionados con la persistencia.
     */
    private static final Logger logger = Logger.getLogger(GraphPersistence.class.getName());

    /**
     * Constructor de la clase {@code GraphPersistence}.
     *
     * <p>Inicializa el objeto Jackson y asegura que las rutas y archivos necesarios existan.
     * Si no existen, se crean automáticamente.</p>
     */
    public GraphPersistence() {
        this.mapper = new ObjectMapper();

        createDirectoryIfNotExists();
        createFileIfNotExists(HISTORY_FILE);
    }

    /**
     * Crea el directorio base si no existe.
     */
    private void createDirectoryIfNotExists() {
        try {
            Path dirPath = Paths.get(BASE_PATH);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                logger.info("Estructura de directorios creada: " + BASE_PATH);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creando la estructura de directorios: " + BASE_PATH, e);
        }
    }

    /**
     * Crea un archivo vacío si no existe.
     *
     * @param fileName Nombre del archivo a verificar o crear.
     */
    private void createFileIfNotExists(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();

                if (fileName.equals(HISTORY_FILE)) {
                    mapper.writeValue(file, new ArrayList<>());
                    logger.info("Archivo de historial vacío creado: " + fileName);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error creando el archivo: " + fileName, e);
            }
        }
    }

    /**
     * Clase estática que representa el historial de rutas calculadas.
     */
    public static class RouteHistory {

        /**
         * Ubicación de origen.
         */
        public String source;

        /**
         * Ubicación de destino.
         */
        public String target;

        /**
         * Lista de nombres de ubicaciones que conforman el camino.
         */
        public List<String> path;

        /**
         * Distancia total de la ruta en kilómetros.
         */
        public double distance;

        /**
         * Costo total de la ruta en la unidad monetaria definida.
         */
        public double cost;

        /**
         * Tiempo estimado de viaje en horas.
         */
        public double time;

        /**
         * Fecha y hora en que se calculó la ruta.
         */
        public Date timestamp;

        /**
         * Constructor vacío necesario para la deserialización.
         */
        public RouteHistory() {}

        /**
         * Constructor que inicializa un historial de ruta con los detalles proporcionados.
         *
         * @param source Ubicación de origen.
         * @param target Ubicación de destino.
         * @param path Lista de ubicaciones que conforman el camino.
         * @param distance Distancia total en kilómetros.
         * @param cost Costo total en la unidad monetaria definida.
         * @param time Tiempo estimado de viaje en horas.
         */
        public RouteHistory(Location source, Location target, List<Location> path,
                            double distance, double cost, double time) {
            this.source = source.getName();
            this.target = target.getName();
            this.path = path.stream()
                    .map(Location::getName)
                    .collect(java.util.stream.Collectors.toList());
            this.distance = distance;
            this.cost = cost;
            this.time = time;
            this.timestamp = new Date();
        }
    }

    /**
     * Guarda un nuevo historial de ruta en el archivo JSON.
     *
     * @param history Instancia de {@link RouteHistory} que contiene los detalles de la ruta.
     * @throws IOException Si ocurre un error al guardar los datos en el archivo.
     */
    public void saveRouteHistory(RouteHistory history) throws IOException {
        List<RouteHistory> histories = loadRouteHistory();
        histories.add(history);
        mapper.writeValue(new File(HISTORY_FILE), histories);
        logger.info("Historial de ruta guardado en " + HISTORY_FILE);
    }

    /**
     * Carga el historial de rutas desde el archivo JSON.
     *
     * @return Una lista de instancias {@link RouteHistory} que representan el historial de rutas.
     *         Si el archivo no existe, se devuelve una lista vacía.
     * @throws IOException Si ocurre un error al leer los datos del archivo.
     */
    public List<RouteHistory> loadRouteHistory() throws IOException {
        if (!new File(HISTORY_FILE).exists()) {
            logger.warning("El archivo de historial no existe. Devolviendo lista de historial vacía.");
            return new ArrayList<>();
        }
        List<RouteHistory> histories = mapper.readValue(new File(HISTORY_FILE),
                new TypeReference<List<RouteHistory>>() {});
        logger.info("Historial de rutas cargado con " + histories.size() + " entradas.");
        return histories;
    }
}
