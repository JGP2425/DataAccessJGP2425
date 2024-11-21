package org.jgp2425.unit.finalactivity_v1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jgp2425.unit.finalactivity_v1.entities.Sellers;

import java.io.IOException;

public class SellerLoginController extends Application {
    @FXML
    private TextField userField;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Label errorLabel;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SellerLogin-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Online Market");
        stage.setScene(scene);
        stage.show();

        //Check if the database is available
        if (!Utils.isDatabaseAvailable()) {
            showPopUp(stage);
        }
    }

    private void showPopUp(Stage ownerStage) {
        //Create scene for the pop-up
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(ownerStage);
        popupStage.setTitle("Database not found");
        popupStage.setWidth(300);
        popupStage.setHeight(200);

        //Pop-up Content
        Label message = new Label("Database not found. The application will close.");
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> {
            popupStage.close();
            Platform.exit();
        });

        //Close the pop-up if the user close the window
        popupStage.setOnCloseRequest(event -> Platform.exit());

        // Pop-up center content
        VBox popupRoot = new VBox(10, message, closeBtn);
        popupRoot.setAlignment(Pos.CENTER);
        Scene popupScene = new Scene(popupRoot);

        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    @FXML
    public void loginBtnOnClick() {
        //Open the session
        SessionFactory sessionFactory;
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Sellers.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Retrieve all the variables provided by the user
        String user = userField.getText();
        String pwd = pwdField.getText();

        //Check if the user and the password is not empty
        if (!user.isEmpty() && !pwd.isEmpty()) {
            try {
                //Retrieve the seller provided by the user
                Sellers seller = new Sellers().getSellerByCif(session,user);

                //If seller is founded
                if (seller != null) {
                    //Validate password
                    String MD5pwd = seller.getPassword();
                    boolean validPwd = Utils.validatePassword(pwd, MD5pwd);

                    //If valid enter the application, else error message
                    if (seller.validateSeller(pwd)) {
                        //Load the other windows with the seller data
                        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("SellerData-View.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                        Stage stage = (Stage) pwdField.getScene().getWindow();
                        SellerDataController sellerDataController = fxmlLoader.getController();
                        sellerDataController.setParametersSeller(seller);
                        stage.setTitle("Online Market");
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Incorrect password. Try again.");
                    }
                }
                else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("User has not been founded.");
                }
            }
            catch (javax.persistence.NoResultException e)
            {
                errorLabel.setVisible(true);
                errorLabel.setText("User has not been founded.");
                throw e;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if (user.isEmpty() && pwd.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("User CIF and password cannot be empty.");
        }
        else if (user.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("User CIF cannot be empty.");
        }
        else if (pwd.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Password cannot be empty.");
        }
    }
}