package view;

import controller.AgendaController;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

import java.util.List;

public class AgendaView {
    private final AgendaController controller;

    // Definición de colores según la paleta proporcionada
    private static final String COLOR_BEIGE = "#F2D0A7";       // Beige
    private static final String COLOR_CREMA = "#F2EDE9";       // Crema claro
    private static final String COLOR_MINT = "#C3D7D8";        // Azul claro
    private static final String COLOR_MARRON = "#8C6E5D";      // Marrón
    private static final String COLOR_ROSA = "#F2CFC2";        // Rosa pastel
    private static final String COLOR_TEXTO = "#3A3A3A";       // Texto oscuro

    public AgendaView(AgendaController controller) {
        this.controller = controller;
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Agenda de Contactos");

        // Título principal con estilo moderno
        Label lblTitulo = new Label("AGENDA DE CONTACTOS");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web(COLOR_MARRON));
        lblTitulo.setPadding(new Insets(15, 0, 15, 0));
        lblTitulo.setAlignment(Pos.CENTER);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                          "-fx-background-radius: 8;" +
                          "-fx-border-radius: 8;");

        // Campos de entrada con estilo moderno
        Label lblDatosContacto = new Label("INFORMACIÓN DE CONTACTO");
        lblDatosContacto.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblDatosContacto.setTextFill(Color.web(COLOR_MARRON));
        lblDatosContacto.setPadding(new Insets(10, 0, 10, 0));
        lblDatosContacto.setAlignment(Pos.CENTER);
        lblDatosContacto.setMaxWidth(Double.MAX_VALUE);

        TextField txtNombre = crearCampoTexto("Nombre");
        TextField txtApellido = crearCampoTexto("Apellido");
        TextField txtTelefono = crearCampoTexto("Teléfono (9 dígitos)");
        TextField txtEmail = crearCampoTexto("Correo electrónico");

        // Lista de contactos con estilo moderno
        ListView<String> listaContactos = new ListView<>();
        listaContactos.setStyle("-fx-control-inner-background: " + COLOR_CREMA + ";" +
                               "-fx-background-color: " + COLOR_CREMA + ";" +
                               "-fx-text-fill: " + COLOR_TEXTO + ";" +
                               "-fx-border-color: " + COLOR_MINT + ";" +
                               "-fx-border-width: 2px;" +
                               "-fx-border-radius: 10px;" +
                               "-fx-background-radius: 10px;" +
                               "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Botones con estilos modernos
        Button btnAgregar = crearBoton("Añadir Contacto", COLOR_MINT);
        Button btnListar = crearBoton("Listar Contactos", COLOR_MINT);
        Button btnEliminar = crearBoton("Eliminar Contacto", COLOR_MARRON);
        Button btnModificar = crearBoton("Modificar Teléfono", COLOR_MINT);
        Button btnEspacios = crearBoton("Espacios Libres", COLOR_ROSA);
        Button btnLlena = crearBoton("¿Agenda Llena?", COLOR_ROSA);

        // Acciones
        btnAgregar.setOnAction(e -> {
            try {
                String mensaje = controller.agregarContacto(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText(),
                        txtEmail.getText()
                );
                mostrarAlerta("Resultado", mensaje);
            } catch (Exception ex) {
                mostrarAlerta("Error", ex.getMessage());
            }
        });

        btnListar.setOnAction(e -> {
            listaContactos.getItems().setAll(controller.listarContactos());
        });

        btnEliminar.setOnAction(e -> {
            String mensaje = controller.eliminarContacto(txtNombre.getText(), txtApellido.getText());
            mostrarAlerta("Resultado", mensaje);
        });

        // Modificado: Botón de modificar teléfono ahora muestra un diálogo personalizado
        btnModificar.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();

            // Primero verificamos si el contacto existe
            if (nombre.isEmpty() || apellido.isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar nombre y apellido para buscar el contacto.");
                return;
            }

            // Intentamos buscar el teléfono actual del contacto
            List<String> contactos = controller.listarContactos();
            String telefonoActual = "";
            boolean encontrado = false;

            for (String contactoInfo : contactos) {
                // Buscar exactamente el nombre y apellido
                if (contactoInfo.toLowerCase().startsWith(nombre.toLowerCase() + " " + apellido.toLowerCase() + ": ")) {
                    // Extraer el teléfono del formato "Nombre Apellido: Teléfono - Email"
                    int inicioTel = contactoInfo.indexOf(": ") + 2;
                    int finTel = contactoInfo.indexOf(" - ");
                    if (inicioTel > 0 && finTel > inicioTel) {
                        telefonoActual = contactoInfo.substring(inicioTel, finTel);
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) {
                mostrarAlerta("Error", "No se encontró el contacto especificado.");
                return;
            }

            // Mostrar diálogo de modificación
            mostrarDialogoModificarTelefono(nombre, apellido, telefonoActual);
        });

        btnEspacios.setOnAction(e -> {
            mostrarAlerta("Espacios libres", "Quedan " + controller.espaciosLibres() + " espacios.");
        });

        btnLlena.setOnAction(e -> {
            boolean llena = controller.agendaLlena();
            mostrarAlerta("Estado de Agenda", llena ? "La agenda está llena" : "Aún hay espacio disponible");
        });

        // Layout mejorado con diseño moderno
        VBox seccionFormulario = new VBox(10);
        seccionFormulario.getChildren().addAll(lblDatosContacto, txtNombre, txtApellido, txtTelefono, txtEmail);
        seccionFormulario.setStyle("-fx-background-color: " + COLOR_BEIGE + ";" +
                                  "-fx-background-radius: 12;" +
                                  "-fx-padding: 20;" +
                                  "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Botones en disposición vertical
        VBox seccionBotones = new VBox(10);
        seccionBotones.getChildren().addAll(
                new Label("ACCIONES"),
                btnAgregar,
                btnListar,
                btnModificar,
                new Separator(),
                btnEliminar,
                new Separator(),
                btnEspacios,
                btnLlena);
        seccionBotones.setStyle("-fx-padding: 15;" +
                               "-fx-background-color: " + COLOR_BEIGE + ";" +
                               "-fx-background-radius: 12;" +
                               "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        // Agregamos estilo al título de la sección de botones
        ((Label)seccionBotones.getChildren().get(0)).setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ((Label)seccionBotones.getChildren().get(0)).setTextFill(Color.web(COLOR_MARRON));
        ((Label)seccionBotones.getChildren().get(0)).setAlignment(Pos.CENTER);
        ((Label)seccionBotones.getChildren().get(0)).setMaxWidth(Double.MAX_VALUE);
        ((Label)seccionBotones.getChildren().get(0)).setPadding(new Insets(0, 0, 5, 0));

        VBox panelIzquierdo = new VBox(20);
        panelIzquierdo.getChildren().addAll(lblTitulo, seccionFormulario, seccionBotones);
        panelIzquierdo.setPadding(new Insets(25));
        panelIzquierdo.setStyle("-fx-background-color: " + COLOR_CREMA + ";");
        panelIzquierdo.setPrefWidth(300);
        panelIzquierdo.setAlignment(Pos.TOP_CENTER);

        Label lblListaContactos = new Label("CONTACTOS GUARDADOS");
        lblListaContactos.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblListaContactos.setTextFill(Color.web(COLOR_MARRON));
        lblListaContactos.setPadding(new Insets(0, 0, 15, 0));
        lblListaContactos.setAlignment(Pos.CENTER);
        lblListaContactos.setMaxWidth(Double.MAX_VALUE);

        VBox panelDerecho = new VBox(15);
        panelDerecho.getChildren().addAll(lblListaContactos, listaContactos);
        panelDerecho.setPadding(new Insets(25));
        panelDerecho.setStyle("-fx-background-color: " + COLOR_CREMA + ";");

        // Añadir una línea decorativa entre paneles
        Separator separador = new Separator();
        separador.setOrientation(Orientation.VERTICAL);
        separador.setStyle("-fx-background-color: " + COLOR_BEIGE + ";");

        HBox contenedorPrincipal = new HBox();
        contenedorPrincipal.getChildren().addAll(panelIzquierdo, separador, panelDerecho);
        HBox.setHgrow(panelDerecho, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(contenedorPrincipal);
        root.setStyle("-fx-background-color: " + COLOR_CREMA + ";");

        Scene scene = new Scene(root, 850, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextField crearCampoTexto(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                          "-fx-text-fill: " + COLOR_TEXTO + ";" +
                          "-fx-prompt-text-fill: derive(" + COLOR_TEXTO + ", 50%);" +
                          "-fx-border-color: transparent;" +
                          "-fx-border-width: 0 0 2 0;" +
                          "-fx-border-style: solid;" +
                          "-fx-border-color: " + COLOR_MINT + ";" +
                          "-fx-background-radius: 5;");
        textField.setPadding(new Insets(12, 15, 12, 15));
        return textField;
    }

    private Button crearBoton(String texto, String colorFondo) {
        Button boton = new Button(texto);
        boton.setStyle("-fx-background-color: " + colorFondo + ";" +
                       "-fx-text-fill: " + COLOR_TEXTO + ";" +
                       "-fx-font-family: 'Arial';" +
                       "-fx-font-weight: bold;" +
                       "-fx-border-color: transparent;" +
                       "-fx-border-radius: 8;" +
                       "-fx-background-radius: 8;");
        boton.setPadding(new Insets(12, 15, 12, 15));
        boton.setMaxWidth(Double.MAX_VALUE);

        // Efectos al pasar el ratón
        boton.setOnMouseEntered(e -> {
            boton.setStyle("-fx-background-color: derive(" + colorFondo + ", -10%);" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-font-family: 'Arial';" +
                           "-fx-font-weight: bold;" +
                           "-fx-border-color: transparent;" +
                           "-fx-border-radius: 8;" +
                           "-fx-background-radius: 8;" +
                           "-fx-cursor: hand;");
            boton.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.2)));
        });

        boton.setOnMouseExited(e -> {
            boton.setStyle("-fx-background-color: " + colorFondo + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-font-family: 'Arial';" +
                           "-fx-font-weight: bold;" +
                           "-fx-border-color: transparent;" +
                           "-fx-border-radius: 8;" +
                           "-fx-background-radius: 8;");
            boton.setEffect(null);
        });

        return boton;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // Estilo personalizado para el diálogo
        DialogPane dialogPane = alerta.getDialogPane();
        dialogPane.setMinHeight(200);
        dialogPane.setMinWidth(400);
        dialogPane.setPrefSize(450, 250);

        // Ajustar el contenedor de texto para que se ajuste correctamente
        Label label = new Label(mensaje);
        label.setWrapText(true);
        label.setMaxWidth(400);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setStyle("-fx-text-fill: " + COLOR_TEXTO + ";" +
                      "-fx-font-size: 14px;" +
                      "-fx-font-family: 'Arial';");

        // Reemplazar el contenido del diálogo con nuestro label personalizado
        dialogPane.setContent(label);

        dialogPane.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                           "-fx-border-color: " + COLOR_BEIGE + ";" +
                           "-fx-border-width: 2px;" +
                           "-fx-border-radius: 10px;" +
                           "-fx-background-radius: 10px;");

        // Cambiar estilo de los botones
        dialogPane.getButtonTypes().forEach(buttonType -> {
            Button button = (Button) dialogPane.lookupButton(buttonType);
            button.setStyle("-fx-background-color: " + COLOR_MINT + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-font-family: 'Arial';" +
                           "-fx-font-weight: bold;" +
                           "-fx-padding: 10 20 10 20;" +
                           "-fx-border-radius: 8;" +
                           "-fx-background-radius: 8;");
        });

        // Configurar para que la ventana sea redimensionable
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.setResizable(true);

        alerta.showAndWait();
    }

    private void mostrarDialogoModificarTelefono(String nombre, String apellido, String telefonoActual) {
        // Crear ventana de diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modificar Teléfono");

        // Título del diálogo
        Label lblTitulo = new Label("MODIFICAR TELÉFONO");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setTextFill(Color.web(COLOR_MARRON));
        lblTitulo.setPadding(new Insets(0, 0, 20, 0));
        lblTitulo.setAlignment(Pos.CENTER);
        lblTitulo.setMaxWidth(Double.MAX_VALUE);

        // Etiquetas con estilo
        Label lblNombre = new Label("Nombre:");
        Label lblApellido = new Label("Apellido:");
        Label lblTelefonoActual = new Label("Teléfono Actual:");
        Label lblNuevoTelefono = new Label("Nuevo Teléfono:");

        // Aplicar estilo a las etiquetas
        lblNombre.setStyle("-fx-text-fill: " + COLOR_TEXTO + "; -fx-font-weight: bold;");
        lblApellido.setStyle("-fx-text-fill: " + COLOR_TEXTO + "; -fx-font-weight: bold;");
        lblTelefonoActual.setStyle("-fx-text-fill: " + COLOR_TEXTO + "; -fx-font-weight: bold;");
        lblNuevoTelefono.setStyle("-fx-text-fill: " + COLOR_TEXTO + "; -fx-font-weight: bold;");

        // Campos de texto con estilos coherentes
        TextField txtNombre = new TextField(nombre);
        txtNombre.setEditable(false);
        txtNombre.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-border-color: transparent;" +
                           "-fx-border-width: 0 0 1 0;" +
                           "-fx-border-style: solid;" +
                           "-fx-background-radius: 5;");

        TextField txtApellido = new TextField(apellido);
        txtApellido.setEditable(false);
        txtApellido.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-border-color: transparent;" +
                           "-fx-border-width: 0 0 1 0;" +
                           "-fx-border-style: solid;" +
                           "-fx-background-radius: 5;");

        TextField txtTelefonoActual = new TextField(telefonoActual);
        txtTelefonoActual.setEditable(false);
        txtTelefonoActual.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-border-color: transparent;" +
                           "-fx-border-width: 0 0 1 0;" +
                           "-fx-border-style: solid;" +
                           "-fx-background-radius: 5;");

        TextField txtNuevoTelefono = new TextField();
        txtNuevoTelefono.setPromptText("Ingrese el nuevo teléfono");
        txtNuevoTelefono.setStyle("-fx-background-color: " + COLOR_CREMA + ";" +
                           "-fx-text-fill: " + COLOR_TEXTO + ";" +
                           "-fx-prompt-text-fill: derive(" + COLOR_TEXTO + ", 50%);" +
                           "-fx-border-color: " + COLOR_MINT + ";" +
                           "-fx-border-width: 0 0 2 0;" +
                           "-fx-border-style: solid;" +
                           "-fx-background-radius: 5;");
        txtNuevoTelefono.setPadding(new Insets(10));
        txtNuevoTelefono.requestFocus(); // Poner el foco en este campo

        // Botones con estilo coherente
        Button btnGuardar = new Button("Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: " + COLOR_MINT + ";" +
                       "-fx-text-fill: " + COLOR_TEXTO + ";" +
                       "-fx-font-family: 'Arial';" +
                       "-fx-font-weight: bold;" +
                       "-fx-border-color: transparent;" +
                       "-fx-border-radius: 8;" +
                       "-fx-background-radius: 8;" +
                       "-fx-padding: 10 20 10 20;");
        btnGuardar.setPrefWidth(150);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: " + COLOR_MARRON + ";" +
                       "-fx-text-fill: white;" +
                       "-fx-font-family: 'Arial';" +
                       "-fx-font-weight: bold;" +
                       "-fx-border-color: transparent;" +
                       "-fx-border-radius: 8;" +
                       "-fx-background-radius: 8;" +
                       "-fx-padding: 10 20 10 20;");
        btnCancelar.setPrefWidth(120);

        // Acciones de los botones
        btnGuardar.setOnAction(e -> {
            String nuevoTelefono = txtNuevoTelefono.getText();
            if (nuevoTelefono.isEmpty()) {
                mostrarAlerta("Error", "El nuevo teléfono no puede estar vacío.");
                return;
            }

            // Lógica para modificar el teléfono en el controlador
            String mensaje = controller.modificarTelefono(nombre, apellido, nuevoTelefono);
            mostrarAlerta("Resultado", mensaje);

            dialogStage.close();
        });

        btnCancelar.setOnAction(e -> dialogStage.close());

        // Layout del diálogo mejorado
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.add(lblTitulo, 0, 0, 2, 1); // Ocupa dos columnas
        grid.add(lblNombre, 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(lblApellido, 0, 2);
        grid.add(txtApellido, 1, 2);
        grid.add(lblTelefonoActual, 0, 3);
        grid.add(txtTelefonoActual, 1, 3);
        grid.add(lblNuevoTelefono, 0, 4);
        grid.add(txtNuevoTelefono, 1, 4);

        // Añadir separador antes de los botones
        Separator separador = new Separator();
        separador.setPadding(new Insets(15, 0, 15, 0));
        grid.add(separador, 0, 5, 2, 1);

        // Añadir botones en un HBox para mejor distribución
        HBox botonesBox = new HBox(20);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.getChildren().addAll(btnGuardar, btnCancelar);
        grid.add(botonesBox, 0, 6, 2, 1);

        // Estilo del contenedor principal
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: " + COLOR_BEIGE + ";" +
                     "-fx-background-radius: 10;");

        // Configurar escena y mostrar
        Scene scene = new Scene(grid, 450, 400);
        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL); // Modal
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}

