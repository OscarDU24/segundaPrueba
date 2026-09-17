# Proyecto unificado — Registro y Consulta de Solicitudes (JavaFX)

Este proyecto fusiona en una sola aplicación los tres aportes que existían
por separado en las ramas del repositorio (`main`, `DiegoG`, `Oscar`):

- **Base (login / menú / configuración / registro de usuario)** — rama `main`.
- **Registro de cliente** (Ventana 3) — rama `DiegoG`.
- **Consulta de clientes y detalle** (Ventana 4) — rama `Oscar`.

Todo quedó en un único paquete `org.example.test`, con un modelo `Cliente`
y una lista compartida (`Util/DatosCompartidos`) para que el registro y la
consulta trabajen sobre los mismos datos en memoria.

## Cómo ejecutar

```
cd SegundaEvaluacion/Evaluación2
mvn clean javafx:run
```

Clase principal: `org.example.test.HelloApplication` (ya configurada en el `pom.xml`).

## Mapeo con los requerimientos del caso práctico

| Requerimiento | Dónde está implementado |
|---|---|
| Ventana 1 – Login con validación y Alert | `login.fxml` / `loginController` (campos vacíos, usuario no encontrado, contraseña incorrecta) |
| Botón Salir con confirmación (Alert) | `loginController.cerrarVentana` |
| Ventana 2 – MenuBar, ToolBar, botones | `home.fxml` (Menú Archivo/Herramientas/Ayuda + ToolBar) |
| ContextMenu | Clic derecho sobre la etiqueta de bienvenida en `home.fxml` |
| Ventana 3 – Registro de cliente completo | `registroClientes.fxml` / `registroClienteControllers` |
| RadioButton + ToggleGroup (Tipo de solicitud) | `tgTipoSolicitud`, `rbNueva/rbModificacion/rbCancelacion` |
| FileChooser + ImageView | `seleccionarFoto()` en `registroClienteControllers` |
| Ventana 4 – TableView de clientes | `consultas.fxml` / `consultasController` |
| MouseEvent (doble clic → detalle) | `onTableItemClicked` en `consultasController` |
| Navegación y paso de datos entre ventanas | Login → Home → Registro/Consulta → Detalle, todo sobre `DatosCompartidos.clientes` |
| ActionEvent | Iniciar sesión, Guardar, Limpiar, abrir ventanas, cerrar sesión, menú, etc. |
| KeyEvent | ENTER en login (inicia sesión), ESC en Home (confirma salida), restricción de caracteres en Nombre/Apellido |
| Alert de información / advertencia / confirmación | Presentes en login, registro de cliente y ventana principal |
| Dialog (distinto de Alert) | "Acerca de" (Dialog personalizado) y "Filtrar por ciudad" (ChoiceDialog) en Home/Consultas |
| DirectoryChooser | "Elegir carpeta de respaldo" en el menú Herramientas de Home |
| Separación vistas/controladores, FXML, paquetes organizados | Estructura `Controllers/ Models/ Services/ Util/` |

## Pendiente para el equipo

- Completar el documento breve de integrantes y función de cada uno (entregable #10).
- Verificar en IntelliJ + Scene Builder que el proyecto compila y corre sin errores antes de la defensa (`mvn clean javafx:run`).
- Opcional: agregar íconos/imágenes propias en `src/main/resources/org/example/test/images/`.
