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

        //Retrieve login controller
        SellerLoginController loginController = fxmlLoader.getController();

        //Check if the database is available
        if (!Utils.isDatabaseAvailable())
            loginController.showPopUp(stage);

        //Set the cif is the user used the remember me checkbox
        loginController.setUserField();

        //Show the login view
        stage.setTitle("Online Market");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("org.hibernate");
        logger.setLevel(Level.SEVERE);
        launch();
    }
}