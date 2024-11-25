package org.jgp2425.unit.finalactivity_v1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jgp2425.unit.finalactivity_v1.entities.Seller;

import java.io.*;

public class SellerLoginController{
    @FXML
    private TextField userField;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Label errorLabel;

    @FXML
    private CheckBox rememberChk;

    private static String cifFilePath = "C:\\examenJGP2425\\Actividad Final\\FinalActivity_V1\\src\\main\\resources\\cif.txt";

    public void setUserField() {
        //Check if the user have remembered the last login
        String cif = "";
        File file = new File(cifFilePath);
        if (file.exists()) {
            //Read the last cif logged
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(cifFilePath));
                cif = bufferedReader.readLine();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        //If the cif is fulfilled autocomplete the cif field
        if (!cif.isEmpty() && userField != null)
            userField.setText(cif);
    }

    public void showPopUp(Stage ownerStage) {
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
                .addAnnotatedClass(Seller.class)
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        //Retrieve all the variables provided by the user
        String user = userField.getText();
        String pwd = pwdField.getText();

        //Check if the user and the password is not empty
        if (!user.isEmpty() && !pwd.isEmpty()) {
            try {
                //Retrieve the seller provided by the user
                Seller seller = new Seller().getSellerByCif(session,user);

                //If seller is founded
                if (seller != null) {
                    //Validate password
                    String MD5pwd = seller.getPassword();
                    boolean validPwd = Utils.validatePassword(pwd, MD5pwd);

                    //If valid enter the application, else error message
                    if (seller.validateSeller(pwd)) {
                        //If the user want to be remembered save the cif in a txt file
                        if (rememberChk.isSelected())
                        {
                            PrintWriter printWriter = null;
                            try {
                                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(cifFilePath, false)));
                                printWriter.println(seller.getCif());
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                if (printWriter != null)
                                    printWriter.close();
                            }
                        }
                        //Delete the file text
                        else {
                            File cifFile = new File(cifFilePath);
                            if (cifFile.exists())
                                cifFile.delete();
                        }

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