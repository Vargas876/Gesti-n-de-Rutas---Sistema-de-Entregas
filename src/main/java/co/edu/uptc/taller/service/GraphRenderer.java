package co.edu.uptc.taller.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jgrapht.graph.DefaultWeightedEdge;

import co.edu.uptc.taller.model.DeliveryGraph;
import co.edu.uptc.taller.model.Location;
import javafx.scene.web.WebView;

/**
 * Servicio encargado de renderizar el grafo en un WebView.
 */
public class GraphRenderer {

    private static final Logger logger = Logger.getLogger(GraphRenderer.class.getName());
    private static final double AVERAGE_SPEED_KMH = 60.0; // Debe coincidir con RouteService

    private DeliveryGraph deliveryGraph;

    /**
     * Constructor que recibe una instancia de DeliveryGraph.
     *
     * @param deliveryGraph La instancia del grafo de entregas.
     */
    public GraphRenderer(DeliveryGraph deliveryGraph) {
        this.deliveryGraph = deliveryGraph;
    }
    /**
     * Renderiza el grafo en el WebView proporcionado.
     *
     * @param webView WebView donde se renderizará el grafo.
     * @param path    Ruta a resaltar en el grafo (puede ser null).
     */
    public void renderGraph(WebView webView, List<Location> path) {
        logger.info("Renderizando el grafo. Ruta proporcionada: " + (path != null ? "Sí" : "No"));

        StringBuilder html = new StringBuilder();
        html.append("<html><head>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 0; }")
                .append(".node { fill: #4a90e2; stroke: white; stroke-width: 2px; cursor: pointer; }")
                .append(".edge { stroke: #999; stroke-width: 2px; }")
                .append(".highlighted { stroke: #e74c3c; stroke-width: 4px; }")
                .append(".label { font-size: 14px; font-weight: bold; fill: #2c3e50; }")
                .append(".distance-label { font-size: 12px; fill: #666; }")
                .append(".tooltip { ")
                .append("    position: absolute;")
                .append("    background-color: rgba(0,0,0,0.8);")
                .append("    color: white;")
                .append("    padding: 5px 10px;")
                .append("    border-radius: 5px;")
                .append("    font-size: 12px;")
                .append("    pointer-events: none;")
                .append("}")
                .append("</style>")
                // Scripts para zoom, pan y tooltips
                .append("<script>")
                .append("var svg = null;")
                .append("var tooltip = null;")
                .append("var viewBox = {x: 0, y: 0, width: 800, height: 600};")
                .append("function init(evt) {")
                .append("    svg = evt.target;")
                .append("    svg.addEventListener('wheel', zoom);")
                .append("    var isPanning = false;")
                .append("    var start = {x: 0, y: 0};")
                .append("    var view = viewBox;")
                .append("    svg.addEventListener('mousedown', function(e) { isPanning = true; start.x = e.clientX; start.y = e.clientY; });")
                .append("    svg.addEventListener('mousemove', function(e) {")
                .append("        if (isPanning) {")
                .append("            var dx = (e.clientX - start.x) * view.width / svg.clientWidth;")
                .append("            var dy = (e.clientY - start.y) * view.height / svg.clientHeight;")
                .append("            viewBox.x -= dx;")
                .append("            viewBox.y -= dy;")
                .append("            updateViewBox();")
                .append("            start.x = e.clientX;")
                .append("            start.y = e.clientY;")
                .append("        } else {")
                .append("            var elem = document.elementFromPoint(e.clientX, e.clientY);")
                .append("            if (elem && elem.tagName === 'circle') {")
                .append("                var name = elem.getAttribute('data-name');")
                .append("                showTooltip(e.clientX, e.clientY, 'Ubicación: ' + name);")
                .append("            } else if (elem && elem.tagName === 'line') {")
                .append("                var distance = elem.getAttribute('data-distance');")
                .append("                var cost = elem.getAttribute('data-cost');")
                .append("                var time = elem.getAttribute('data-time');")
                .append("                showTooltip(e.clientX, e.clientY, 'Distancia: ' + distance + ' km<br/>Costo: COP $' + cost + '<br/>Tiempo: ' + time + ' h');")
                .append("            } else {")
                .append("                hideTooltip();")
                .append("            }")
                .append("        }")
                .append("    });")
                .append("    svg.addEventListener('mouseup', function(e) { isPanning = false; });")
                .append("    svg.addEventListener('mouseleave', function(e) { isPanning = false; hideTooltip(); });")
                .append("    // Crear tooltip")
                .append("    tooltip = document.createElement('div');")
                .append("    tooltip.className = 'tooltip';")
                .append("    document.body.appendChild(tooltip);")
                .append("}")
                .append("function zoom(e) {")
                .append("    e.preventDefault();")
                .append("    var scale = 1.1;")
                .append("    if (e.deltaY < 0) {")
                .append("        viewBox.width /= scale;")
                .append("        viewBox.height /= scale;")
                .append("    } else {")
                .append("        viewBox.width *= scale;")
                .append("        viewBox.height *= scale;")
                .append("    }")
                .append("    updateViewBox();")
                .append("}")
                .append("function updateViewBox() {")
                .append("    svg.setAttribute('viewBox', viewBox.x + ' ' + viewBox.y + ' ' + viewBox.width + ' ' + viewBox.height);")
                .append("}")
                .append("function showTooltip(x, y, content) {")
                .append("    tooltip.innerHTML = content;")
                .append("    tooltip.style.left = (x + 10) + 'px';")
                .append("    tooltip.style.top = (y + 10) + 'px';")
                .append("    tooltip.style.display = 'block';")
                .append("}")
                .append("function hideTooltip() {")
                .append("    tooltip.style.display = 'none';")
                .append("}")
                .append("</script>")
                .append("</head><body onload='init(event)'>")
                .append("<svg width='800' height='600' viewBox='0 0 800 600'>");

        // Obtener ubicaciones del grafo
        List<Location> locations = new ArrayList<>(deliveryGraph.getGraph().vertexSet());
        int size = locations.size();
        if (size == 0) {
            logger.warning("No se encontraron ubicaciones en el grafo para renderizar.");
        }
        int centerX = 400;
        int centerY = 300;
        int radius = 250;
        double angleStep = 2 * Math.PI / (size > 0 ? size : 1);


        double[][] positionsArr = new double[size][2];
        for (int i = 0; i < size; i++) {
            positionsArr[i][0] = centerX + radius * Math.cos(i * angleStep);
            positionsArr[i][1] = centerY + radius * Math.sin(i * angleStep);
        }


        for (DefaultWeightedEdge edge : deliveryGraph.getGraph().edgeSet()) {
            Location source = deliveryGraph.getGraph().getEdgeSource(edge);
            Location target = deliveryGraph.getGraph().getEdgeTarget(edge);
            int sourceIndex = locations.indexOf(source);
            int targetIndex = locations.indexOf(target);

            double x1 = positionsArr[sourceIndex][0];
            double y1 = positionsArr[sourceIndex][1];
            double x2 = positionsArr[targetIndex][0];
            double y2 = positionsArr[targetIndex][1];

            String className = "edge";
            if (path != null && isEdgeInPath(path, source, target)) {
                className += " highlighted";
            }

            double distance = deliveryGraph.getGraph().getEdgeWeight(edge);
            double cost = distance * 1500;
            double time = distance / AVERAGE_SPEED_KMH; // Cálculo del tiempo


            html.append("<line x1='").append(x1).append("' y1='").append(y1)
                    .append("' x2='").append(x2).append("' y2='").append(y2)
                    .append("' class='").append(className).append("' ")
                    .append("data-distance='").append(distance).append("' ")
                    .append("data-cost='").append(cost).append("' ")
                    .append("data-time='").append(String.format("%.2f", time)).append("' />");


            double midX = (x1 + x2) / 2;
            double midY = (y1 + y2) / 2;
            html.append("<text x='").append(midX + 5).append("' y='").append(midY - 5)
                    .append("' class='distance-label'>")
                    .append(String.format("%.1f km", distance))
                    .append("</text>");


        }


        for (int i = 0; i < locations.size(); i++) {
            double x = positionsArr[i][0];
            double y = positionsArr[i][1];
            String nodeName = locations.get(i).getName();
            html.append("<circle cx='").append(x).append("' cy='").append(y)
                    .append("' r='15' class='node' data-name='").append(nodeName).append("' />")
                    .append("<text x='").append(x - 10).append("' y='").append(y + 5)
                    .append("' class='label'>").append(nodeName).append("</text>");
        }

        html.append("</svg></body></html>");


        webView.getEngine().loadContent(html.toString());
        logger.info("Grafo renderizado exitosamente en WebView.");
    }

    /**
     * Verifica si una arista entre dos ubicaciones forma parte de la ruta más corta.
     *
     * @param path    La lista de ubicaciones que forman la ruta más corta.
     * @param source  La ubicación de origen de la arista.
     * @param target  La ubicación de destino de la arista.
     * @return        true si la arista está en la ruta, false en caso contrario.
     */
    private boolean isEdgeInPath(List<Location> path, Location source, Location target) {
        for (int i = 0; i < path.size() - 1; i++) {
            Location current = path.get(i);
            Location next = path.get(i + 1);
            if ((current.equals(source) && next.equals(target)) ||
                    (current.equals(target) && next.equals(source))) {
                return true;
            }
        }
        return false;
    }
}
