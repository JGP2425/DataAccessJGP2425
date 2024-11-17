package org.jgp2425.unit.finalactivity_v1;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.jgp2425.unit.finalactivity_v1.entities.Sellers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class SellerLoginController {
    @FXML
    private TextField userField;

    @FXML
    private PasswordField pwdField;

    @FXML
    private Label errorLabel;

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
                Sellers seller = session.createQuery("from Sellers where cif= :cif", Sellers.class).setParameter("cif", user).uniqueResult();

                //If seller is founded
                if (seller != null) {
                    //Validate password
                    String MD5pwd = seller.getPassword();
                    boolean validPwd = Utils.validatePassword(pwd, MD5pwd);

                    //If valid enter the application, else error message
                    if (validPwd) {
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