package fazua;

import fazua.controller.ProductionController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FazuaApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("productionUI.fxml"));
        ProductionController productionController = new ProductionController();
        loader.setControllerFactory(t -> productionController);

        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                productionController.bindProgressBar();
            }
        });
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                productionController.shutdown();
            }
        });
        stage.setTitle("Fazua evation drive system production line");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
