# 🚚 Gestión de Rutas - Sistema de Entregas
[![Java](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![JavaFX](https://img.shields.io/badge/JavaFX-17-blue.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Este proyecto implementa un sistema para calcular y gestionar rutas entre ubicaciones utilizando un grafo ponderado. Ofrece una interfaz gráfica interactiva desarrollada en JavaFX, persistencia de datos en formato JSON, y servicios para inicializar, renderizar y calcular rutas.

## 📁 Estructura del Proyecto

La estructura del proyecto está organizada siguiendo los principios de la arquitectura MVC:

```
src/
├── main/
│   ├── java/
│   │   ├── co/edu/uptc/taller/
│   │   │   ├── controller/
│   │   │   │   └── MainController.java  # Controlador principal
│   │   │   ├── model/
│   │   │   │   ├── Location.java        # Clase para representar ubicaciones
│   │   │   │   ├── DeliveryGraph.java   # Lógica del grafo
│   │   │   ├── persistence/
│   │   │   │   └── GraphPersistence.java # Persistencia de rutas
│   │   │   ├── service/
│   │   │   │   ├── GraphInitializer.java # Inicialización de datos
│   │   │   │   ├── GraphRenderer.java    # Renderización del grafo
│   │   │   │   └── RouteService.java     # Cálculo de rutas
│   │   │   ├── view/
│   │   │   │   └── ViewGraph.java       # Clase de vista para grafo
│   ├── resources/
│   │   ├── view/
│   │   │   ├── main_view.fxml           # Archivo FXML para la interfaz gráfica
│   │   │   └── style.css                # Estilo para JavaFX
│   │   ├── persistence/
│   │   │   └── route_history.json       # Archivo de persistencia
```

## ⭐ Características Principales

### 🗺️ Cálculo de rutas más cortas
- Utiliza el algoritmo de Dijkstra para encontrar la ruta más eficiente entre dos ubicaciones.

### 💾 Persistencia de rutas
- Las rutas calculadas se almacenan en un archivo JSON (`route_history.json`) para referencia futura.

### 🖥️ Visualización interactiva
- Un grafo renderizado muestra las ubicaciones y rutas, permitiendo destacar las rutas calculadas.

### 🏗️ Arquitectura modular
- Basado en la arquitectura MVC, separando la lógica de negocios, la presentación y la persistencia.

## 📋 Requisitos Previos

### 🔧 Java Development Kit (JDK)
- Versión 11 o superior
- [Descargar JDK](https://www.oracle.com/java/technologies/downloads/)

### 📚 Bibliotecas externas
- **Jackson**: Para la serialización/deserialización de JSON
- **JGraphT**: Para el manejo de grafos y algoritmos
- **JavaFX**: Para la interfaz gráfica

### 💻 IDE recomendado
IntelliJ IDEA, Eclipse o NetBeans, con soporte para JavaFX

## 🚀 Configuración del Proyecto

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Vargas876/Gestion-de-Rutas---Sistema-de-Entregas.git
cd Gestion-de-Rutas---Sistema-de-Entregas
```

### 2. Configuración de Dependencias

#### Con Maven:
```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.14.0</version>
    </dependency>
    <dependency>
        <groupId>org.jgrapht</groupId>
        <artifactId>jgrapht-core</artifactId>
        <version>1.5.1</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17</version>
    </dependency>
</dependencies>
```



### 3. Configuración de JavaFX
```bash
--module-path /ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
```

## ▶️ Ejecución del Proyecto

### 🖥️ Ejecutar desde un IDE
1. Abre el proyecto en tu IDE preferido
2. Configura el módulo de ejecución con los argumentos de VM para JavaFX
3. Ejecuta la clase `ViewGraph` ubicada en el paquete `view`

### ⌨️ Ejecutar desde la Línea de Comandos

Compila el proyecto:
```bash
javac -cp "ruta/a/javafx-sdk/lib/*:ruta/a/dependencias/*" src/main/java/co/edu/uptc/taller/view/ViewGraph.java
```

Ejecuta la aplicación:
```bash
java --module-path ruta/a/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp bin co.edu.uptc.taller.view.ViewGraph
```

## 📝 Cómo Usar la Aplicación

1. Selecciona una ubicación de origen y una ubicación de destino desde las listas desplegables
2. Haz clic en el botón "Calcular Ruta"
3. Observa:
    - La ruta más corta en el área de texto
    - El grafo resaltado con la ruta seleccionada
    - El costo, distancia y tiempo estimados de la ruta
4. Visualiza el historial de rutas calculadas en el panel derecho

## ℹ️ Notas Importantes

### 🔧 Personalización de Datos
- Para personalizar las ubicaciones y rutas predeterminadas, edita las clases `GraphInitializer` y `DeliveryGraph`

### 🐛 Depuración de Errores
- Si el archivo `route_history.json` no existe, el sistema lo creará automáticamente
- Verifica los logs en caso de errores para más detalles

### 🎨 Estilo y Apariencia
- La apariencia de la aplicación está controlada por el archivo `style.css` ubicado en `src/main/resources/view/`

## 👤 Autor

Juan Esteban Vargas González 

Universidad Pedagógica y Tecnológica de Colombia (UPTC)
