package view;

import controller.AgendaController;
import model.Contacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class AgendaView {
    private final AgendaController controller;
    private final ObservableList<Contacto> contactosObs = FXCollections.observableArrayList();

    public AgendaView(AgendaController controller) {
        this.controller = controller;
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Agenda");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(650);

        // Título principal
        Label lblTitulo = new Label("Sistema de Agenda");
        lblTitulo.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #8C6E5D;");
        lblTitulo.setPadding(new Insets(15, 0, 15, 0));

        // Título para ingreso
        Label lblFormulario = new Label("Ingresar Contacto");
        lblFormulario.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #8C6E5D;");

        // Campos de entrada
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        Label lblErrorNombre = new Label("");
        lblErrorNombre.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");
        Label lblErrorApellido = new Label("");
        lblErrorApellido.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono (10 dígitos)");
        Label lblErrorTelefono = new Label("");
        lblErrorTelefono.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Correo electrónico");
        Label lblErrorEmail = new Label("");
        lblErrorEmail.setStyle("-fx-text-fill: red; -fx-font-size: 11px;");

        // Añadir listeners para validaciones en tiempo real
        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                lblErrorNombre.setText("El nombre no puede estar vacío");
            } else if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                lblErrorNombre.setText("El nombre solo puede contener letras");
            } else {
                lblErrorNombre.setText("");
            }
        });

        txtApellido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                lblErrorApellido.setText("El apellido no puede estar vacío");
            } else if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                lblErrorApellido.setText("El apellido solo puede contener letras");
            } else {
                lblErrorApellido.setText("");
            }
        });

        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                lblErrorTelefono.setText("El teléfono no puede estar vacío");
            } else if (!newValue.matches("\\d+")) {
                lblErrorTelefono.setText("El teléfono solo puede contener números");
            } else if (newValue.length() != 10) {
                lblErrorTelefono.setText("El teléfono debe tener exactamente 10 dígitos");
            } else {
                lblErrorTelefono.setText("");
            }
        });

        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                lblErrorEmail.setText("El email no puede estar vacío");
            } else if (!newValue.contains("@") || !newValue.contains(".")) {
                lblErrorEmail.setText("El email debe contener '@' y '.'");
            } else if (!newValue.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                lblErrorEmail.setText("Formato de email incorrecto");
            } else {
                lblErrorEmail.setText("");
            }
        });

        VBox nombreBox = new VBox(3, txtNombre, lblErrorNombre);
        VBox apellidoBox = new VBox(3, txtApellido, lblErrorApellido);
        VBox telefonoBox = new VBox(3, txtTelefono, lblErrorTelefono);
        VBox emailBox = new VBox(3, txtEmail, lblErrorEmail);

        VBox camposBox = new VBox(10, lblFormulario, nombreBox, apellidoBox, telefonoBox, emailBox);
        camposBox.setPadding(new Insets(10, 0, 20, 0));

        // Botones (colores planos)
        Button btnAgregar   = crearBoton("Añadir Contacto", "#F2D0A7");
//        Button btnListar    = crearBoton("Listar Contactos", "#C3D7D8");
        Button btnEliminar  = crearBoton("Eliminar Contacto", "#F2CFC2");
        Button btnModificar = crearBoton("Modificar Teléfono", "#8C6E5D", "#ffffff");
        Button btnEspacios  = crearBoton("Espacios Libres", "#C3D7D8");
        Button btnLlena     = crearBoton("¿Agenda Llena?", "#F2D0A7");

        VBox botonesBox = new VBox(12, btnAgregar, btnEliminar, btnModificar, btnEspacios, btnLlena);
//        VBox botonesBox = new VBox(12, btnAgregar, btnListar, btnEliminar, btnModificar, btnEspacios, btnLlena);

        // Tabla de contactos
        Label lblTabla = new Label("Lista de Contactos");
        lblTabla.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #8C6E5D;");

        TableView<Contacto> tablaContactos = new TableView<>(contactosObs);
        tablaContactos.setPrefHeight(400);
        tablaContactos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaContactos.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );

        TableColumn<Contacto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(Objects.toString(data.getValue().getNombre(), "")));

        TableColumn<Contacto, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(data -> new SimpleStringProperty(Objects.toString(data.getValue().getApellido(), "")));

        TableColumn<Contacto, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(data -> new SimpleStringProperty(Objects.toString(data.getValue().getTelefono(), "")));

        TableColumn<Contacto, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(Objects.toString(data.getValue().getEmail(), "")));

        tablaContactos.getColumns().addAll(colNombre, colApellido, colTelefono, colEmail);

        VBox tablaBox = new VBox(10, lblTabla, tablaContactos);
        tablaBox.setPadding(new Insets(20));

        // Acciones
        btnAgregar.setOnAction(e -> {
            // Validar todos los campos antes de enviar
            boolean hayErrores = false;
            StringBuilder errores = new StringBuilder("Por favor corrige los siguientes errores:\n");

            // Validar nombre
            if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
                errores.append("- El nombre no puede estar vacío\n");
                hayErrores = true;
            } else if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                errores.append("- El nombre solo puede contener letras\n");
                hayErrores = true;
            }

            // Validar apellido
            if (txtApellido.getText() == null || txtApellido.getText().trim().isEmpty()) {
                errores.append("- El apellido no puede estar vacío\n");
                hayErrores = true;
            } else if (!txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+")) {
                errores.append("- El apellido solo puede contener letras\n");
                hayErrores = true;
            }

            // Validar teléfono
            if (txtTelefono.getText() == null || txtTelefono.getText().trim().isEmpty()) {
                errores.append("- El teléfono no puede estar vacío\n");
                hayErrores = true;
            } else if (!txtTelefono.getText().matches("\\d+")) {
                errores.append("- El teléfono solo puede contener números\n");
                hayErrores = true;
            } else if (txtTelefono.getText().length() != 10) {
                errores.append("- El teléfono debe tener exactamente 10 dígitos\n");
                hayErrores = true;
            }

            // Validar email
            if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
                errores.append("- El email no puede estar vacío\n");
                hayErrores = true;
            } else if (!txtEmail.getText().matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                errores.append("- El email no tiene un formato válido\n");
                hayErrores = true;
            }

            // Si hay errores, mostrar alerta y no continuar
            if (hayErrores) {
                mostrarAlertaStyled("Error de Validación", errores.toString());
                return;
            }

            // Si no hay errores, intentar agregar el contacto
            try {
                String mensaje = controller.agregarContacto(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText(),
                        txtEmail.getText()
                );
                mostrarAlertaStyled("Resultado", mensaje);
                txtNombre.clear(); txtApellido.clear(); txtTelefono.clear(); txtEmail.clear();
                contactosObs.setAll(controller.getContactos());
            } catch (Exception ex) {
                mostrarAlertaStyled("Error", "Ha ocurrido un error: " + ex.getMessage());
            }
        });

//        btnListar.setOnAction(e -> contactosObs.setAll(controller.getContactos()));

        btnEliminar.setOnAction(e -> {
            String mensaje = controller.eliminarContacto(txtNombre.getText(), txtApellido.getText());
            mostrarAlertaStyled("Resultado", mensaje);
            contactosObs.setAll(controller.getContactos());
        });

        btnModificar.setOnAction(e -> {
            String mensaje = controller.modificarTelefono(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            );
            mostrarAlertaStyled("Resultado", mensaje);
            contactosObs.setAll(controller.getContactos());
        });

        btnEspacios.setOnAction(e -> mostrarAlertaStyled("Espacios libres", "Quedan " + controller.espaciosLibres() + " espacios."));

        btnLlena.setOnAction(e -> {
            boolean llena = controller.agendaLlena();
            mostrarAlertaStyled("Estado de Agenda", llena ? "La agenda está llena" : "Aún hay espacio disponible");
        });

        // Panel izquierdo: campos + separador + botones
        VBox form = new VBox(20, camposBox, new Separator(), botonesBox);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.TOP_LEFT);
        form.setStyle("-fx-background-color: #F2EDE9; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);");

        BorderPane root = new BorderPane();
        root.setTop(lblTitulo);
        BorderPane.setAlignment(lblTitulo, Pos.CENTER);
        root.setLeft(form);
        root.setCenter(tablaBox);
        BorderPane.setMargin(form, new Insets(20));

        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #F2EDE9, #C3D7D8);");

        Scene scene = new Scene(root);
        // Cargar CSS (debe existir en resources/styles/style.css)
        //try {
         //   String css = Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm();
           // scene.getStylesheets().add(css);
        //} catch (Exception ex) {
            // si no encuentra CSS no es crítico; seguimos sin él
         //   System.err.println("No se encontró style.css en /styles/style.css");
        //}
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inicializar tabla
        contactosObs.setAll(controller.getContactos());
    }

    private Button crearBoton(String texto, String colorFondo) {
        return crearBoton(texto, colorFondo, "#333333"); // texto gris oscuro
    }

    private Button crearBoton(String texto, String colorFondo, String colorTexto) {
        Button btn = new Button(texto);
        btn.setStyle(
                "-fx-background-radius: 10; " +
                        "-fx-background-color: " + colorFondo + "; " +
                        "-fx-text-fill: " + colorTexto + "; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10 20;"
        );
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    // Ventanas emergentes estilizadas: aplica el CSS del proyecto al DialogPane
    private void mostrarAlertaStyled(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        // aplicar css si está disponible en classpath
        try {
            String css = Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm();
            DialogPane dp = alerta.getDialogPane();
            dp.getStylesheets().add(css);
            dp.getStyleClass().add("custom-alert");
        } catch (Exception ex) {
            // no crítico
        }

        alerta.showAndWait();
    }
}
