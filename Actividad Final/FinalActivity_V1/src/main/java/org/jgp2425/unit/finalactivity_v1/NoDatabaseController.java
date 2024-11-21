package org.jgp2425.unit.finalactivity_v1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NoDatabaseController extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Start the view
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Online Market");
        primaryStage.setScene(scene);
        primaryStage.show();
        showPopUp();
    }

    private void showPopUp() {
        //Create a new stage
        Stage popupStage = new Stage();

        //Configure the pop-up
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("No database found");
        popupStage.setWidth(450);
        popupStage.setHeight(350);

        // Pop-up content
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            popupStage.close();
            Platform.exit();
        });

        StackPane popupRoot = new StackPane(closeBtn);
        Scene popupScene = new Scene(popupRoot);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
