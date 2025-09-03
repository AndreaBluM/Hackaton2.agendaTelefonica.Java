package app;

import controller.AgendaController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.AgendaView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        AgendaController controller = new AgendaController();
        AgendaView view = new AgendaView(controller);
        view.mostrar(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

