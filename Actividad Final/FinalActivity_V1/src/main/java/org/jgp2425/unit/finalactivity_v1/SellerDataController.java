package org.jgp2425.unit.finalactivity_v1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jgp2425.unit.finalactivity_v1.entities.Sellers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellerDataController {
    @FXML
    private TextField cifField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField bnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField pwdField;

    @FXML
    private TextField confirmPwdField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label sucessfulLabel;

    //Function to set all the parameters of the seller in the fields
    public void setParametersSeller(Sellers seller) {
        cifField.setText(seller.getCif());
        nameField.setText(seller.getName());
        bnameField.setText(seller.getBusinessName());
        phoneField.setText(seller.getPhone());
        emailField.setText(seller.getEmail());
        pwdField.setText(seller.getPlainPassword());
    }

    //Function to validate empty fields and lengths of the characters.
    public boolean validateAllFields() {
        //Retrieve all the variables needed
        String name = nameField.getText();
        String bname = bnameField.getText();
        String phone = phoneField.getText();
        String pwd = pwdField.getText();
        String confirmPwd = confirmPwdField.getText();
        String email = emailField.getText();
        boolean isOk = true;

        //Regex for email and phone validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        String phoneRegex = "^\\d{3}-\\d{3}-\\d{3}$";

        //Empty validations and length validations
        if (name.isEmpty() || bname.isEmpty() || phone.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("All necessary fields need to be filled");
            isOk = false;
        }
        else if (name.length() > 100) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Name cannot be more than 100 characters.");
            isOk = false;
        }
        else if (bname.length() > 100) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Business name cannot be more than 100 characters.");
            isOk = false;
        }
        else if (phone.length() > 15) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Phone cannot be more than 15 characters or have a invalid format.");
            isOk = false;
        }
        else if (!Pattern.matches(phoneRegex, phone)) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Phone have an invalid format, the valid format is XXX-XXX-XXX.");
            isOk = false;
        }
        else if (email.length() > 90) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Email cannot be more than 90 characters.");
            isOk = false;
        }
        else if (!Pattern.matches(emailRegex, email)) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Email have a invalid format.");
            isOk = false;
        }
        else if (pwd.length() > 50) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Password cannot be more than 50 characters.");
            isOk = false;
        }
        else if (confirmPwd.length() > 50) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Password cannot be more than 50 characters.");
            isOk = false;
        }
        else if (!confirmPwd.equals(pwd)) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Passwords needs to be identical.");
            isOk = false;
        }

        return isOk;
    }

    public void updateSeller() {
        //Retrieve all the variables to update the seller
        String name = nameField.getText();
        String bname = bnameField.getText();
        String phone = phoneField.getText();
        String pwd = pwdField.getText();
        String email = emailField.getText();
        SessionFactory sessionFactory;
        Session session = null;

        //Start the update
        if (validateAllFields()) {
            try {
                //Open the session
                sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Sellers.class)
                        .buildSessionFactory();
                session = sessionFactory.openSession();

                //Retrieve the seller provided by the user
                Sellers seller = new Sellers().getSellerByCif(session, cifField.getText());

                if (seller != null) {
                    //Generate MD5 Password to the new password
                    String newMD5pwd = "";
                    if (!seller.getPlainPassword().equals(pwd)) {
                        newMD5pwd = Utils.MD5Converter(pwd).toUpperCase();
                    }

                    //Start the transaction
                    session.beginTransaction();

                    //Setting all the properties
                    if (!seller.getName().equals(name))
                        seller.setName(name);

                    if (!seller.getBusinessName().equals(bname))
                        seller.setBusinessName(bname);

                    if (!seller.getPhone().equals(phone))
                        seller.setPhone(phone);

                    if (!seller.getPlainPassword().equals(pwd))
                        seller.setPlainPassword(pwd);

                    if (seller.getEmail() != null) {
                        if (!seller.getEmail().equals(email))
                            seller.setEmail(email);
                    }
                    else
                        seller.setEmail(email);

                    if (!newMD5pwd.isEmpty())
                        seller.setPassword(newMD5pwd);

                    //Commit the transaction
                    session.getTransaction().commit();

                    //Feedback to the user.
                    errorLabel.setVisible(false);
                    sucessfulLabel.setVisible(true);
                }
            }
            catch (Exception e)
            {
                if (session != null)
                    session.getTransaction().rollback();
                throw e;
            }
        }
    }
}