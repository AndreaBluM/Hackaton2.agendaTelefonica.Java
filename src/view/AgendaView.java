package view;

import controller.AgendaController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AgendaView {
    private final AgendaController controller;

    public AgendaView(AgendaController controller) {
        this.controller = controller;
    }

    public void mostrar(Stage primaryStage) {
        primaryStage.setTitle("Agenda de Contactos");

        // Campos de entrada
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");

        TextField txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono (9 dígitos)");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Correo electrónico");

        ListView<String> listaContactos = new ListView<>();

        // Botones
        Button btnAgregar = new Button("Añadir Contacto");
        Button btnListar = new Button("Listar Contactos");
        Button btnEliminar = new Button("Eliminar Contacto");
        Button btnModificar = new Button("Modificar Teléfono");
        Button btnEspacios = new Button("Espacios Libres");
        Button btnLlena = new Button("¿Agenda Llena?");

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

        btnModificar.setOnAction(e -> {
            String mensaje = controller.modificarTelefono(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText()
            );
            mostrarAlerta("Resultado", mensaje);
        });

        btnEspacios.setOnAction(e -> {
            mostrarAlerta("Espacios libres", "Quedan " + controller.espaciosLibres() + " espacios.");
        });

        btnLlena.setOnAction(e -> {
            boolean llena = controller.agendaLlena();
            mostrarAlerta("Estado de Agenda", llena ? "La agenda está llena" : "Aún hay espacio disponible");
        });

        // Layout
        VBox form = new VBox(10, txtNombre, txtApellido, txtTelefono, txtEmail,
                btnAgregar, btnListar, btnEliminar, btnModificar, btnEspacios, btnLlena);
        form.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setLeft(form);
        root.setCenter(listaContactos);

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
