package org.jgp2425.unit.finalactivity_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SellerLogin-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Online Market");
        stage.setScene(scene);
        stage.show();

        if (!Utils.isDatabaseAvailable()) {
//            showPopUp(stage);

        }

    }

//    private void showPopUp(Stage ownerStage) {
//        // Crear el escenario del pop-up
//        Stage popupStage = new Stage();
//        popupStage.initModality(Modality.APPLICATION_MODAL); // Bloquear interacción con otras ventanas
//        popupStage.initOwner(ownerStage); // Asociar el pop-up a la ventana principal
//        popupStage.setTitle("Error de Conexión");
//        popupStage.setWidth(400);
//        popupStage.setHeight(200);
//
//        // Contenido del pop-up
//        Label mensaje = new Label("No se pudo conectar a la base de datos. La aplicación se cerrará.");
//        Button cerrar = new Button("Cerrar Aplicación");
//        cerrar.setOnAction(e -> {
//            popupStage.close();
//            Platform.exit(); // Cerrar toda la aplicación
//        });
//
//        // Diseño del pop-up
//        VBox popupRoot = new VBox(10, mensaje, cerrar);
//        popupRoot.setAlignment(Pos.CENTER);
//        Scene popupScene = new Scene(popupRoot);
//
//        popupStage.setScene(popupScene);
//        popupStage.showAndWait(); // Mostrar el pop-up y esperar a que se cierre

    public static void main(String[] args) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("org.hibernate");
        logger.setLevel(Level.SEVERE);
        launch();
    }
}