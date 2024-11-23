package org.jgp2425.unit.finalactivity_v1;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jgp2425.unit.finalactivity_v1.entities.Product;
import org.jgp2425.unit.finalactivity_v1.entities.Seller;
import org.jgp2425.unit.finalactivity_v1.entities.SellerProduct;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddOfferController {
    private Seller _seller;

    @FXML
    private ComboBox<Product> productCmb;

    @FXML
    private DatePicker fromDtt;

    @FXML
    private DatePicker toDtt;

    @FXML
    private TextField discountField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label sucessfulLabel;

    @FXML
    private AnchorPane mainContent;

    //Function to set the seller and all the fields of the view
    public void setSellerAndFields(Seller seller) {
        //Retrieve the products of the seller
        ObservableList<Product> products = FXCollections.observableArrayList(seller.getSellerProducts());

        //Remove all the items of the combo box
        productCmb.getItems().removeAll();

        //Add the products in the combo box
        productCmb.setItems(products);

        //Show only the name of the product
        productCmb.setConverter(new StringConverter<>() {
            public String toString(Product product) {
                return product != null ? product.getProductName() : "";
            }

            public Product fromString(String string) {
                return productCmb.getItems().stream()
                        .filter(product -> product.getProductName().equals(string))
                        .findFirst()
                        .orElse(null);
            }

        });

        //Set the promptText for the fields
        fromDtt.setPromptText("Pick a date for the offer");
        toDtt.setPromptText("Pick a date for the offer");
        discountField.setPromptText("Type a discount for the offer");
        productCmb.setPromptText("Pick a product for the offer");

        //Set the seller
        _seller = seller;
    }

    public boolean validateFields() {
        boolean isOk = true;

        //Validations
        if (productCmb.getValue() == null) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Product cannot be empty.");
            isOk = false;
        }
        else if (checkDates()) {
            //If the dates is ok check the discount
            isOk = checkDiscount();
        }
        else {
            isOk = false;
        }

        return isOk;
    }

    public boolean checkDates() {
        boolean correctDate = true;
        LocalDate fromDate = fromDtt.getValue();
        LocalDate toDate = toDtt.getValue();


        //Validations
        if (fromDate == null || toDate == null) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Any date cannot be empty.");
            correctDate = false;
        }
        else if (fromDate.isAfter(toDate)) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("From date cannot be after To Date");
            correctDate = false;
        }
        else if (fromDate.isBefore(LocalDate.now()) || toDate.isBefore(LocalDate.now())) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Dates cannot be before than the current day");
            correctDate = false;
        }
        //Validation with the procedure of the BBDD
        else  {
            Product selectedProduct = productCmb.getValue();
            int productID = selectedProduct.getProductId();

            try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/onlinemarket", "postgres", "1234");
                 CallableStatement callableStatement = connection.prepareCall("{? = call is_product_discounted_in_period(?, ?, ?)}")) {

                //Set the parameters
                callableStatement.registerOutParameter(1, Types.BOOLEAN);
                callableStatement.setInt(2, productID);
                callableStatement.setDate(3, java.sql.Date.valueOf(fromDate));
                callableStatement.setDate(4, java.sql.Date.valueOf(toDate));

                //Execute the procedure
                callableStatement.execute();

                //Retrieve the return parameter
                boolean productExist = callableStatement.getBoolean(1);

                //If the product already have an offer
                if (productExist) {
                    errorLabel.setVisible(true);
                    sucessfulLabel.setVisible(false);
                    errorLabel.setText("This product already has a discount in the selected period.");
                    correctDate = false;
                }

            } catch (SQLException e) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("Error while validating the discount period.");
                correctDate = false;
            }
        }

        return  correctDate;
    }

    public boolean checkDiscount() {
        //Create the variables to work
        boolean correctDiscount = true;
        String discount = discountField.getText();

        //Retrieve the dates from the datePickers
        LocalDate fromDate = fromDtt.getValue();
        LocalDate toDate = toDtt.getValue();

        //Calculate the days between the dates
        long differenceDays = ChronoUnit.DAYS.between(fromDate,toDate);

        //Regex to check that the text is a number
        String regexIsNumber = "^\\d{1,2}%$";

        //Format validations
        if (discount.isEmpty()) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Discount cannot be empty.");
            correctDiscount = false;
        }
        else if (!Pattern.matches(regexIsNumber, discount)) {
            errorLabel.setVisible(true);
            sucessfulLabel.setVisible(false);
            errorLabel.setText("Discount must to be a two-digit number with a % at the end (Ex: 10%)");
            correctDiscount = false;
        }
        //Discount value validations
        else {
            int discountPercent = Integer.parseInt(discount.replace("%", ""));
            if (differenceDays > 30) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("More than 30 day offers are not allowed.");
                correctDiscount = false;
            }
            if (differenceDays <= 30 && differenceDays > 15 && discountPercent > 10) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("For a 30 day period the maximum discount is 10%");
                correctDiscount = false;
            }
            else if (differenceDays <= 15 && differenceDays > 7 && discountPercent > 15) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("For a 15 day period the maximum discount is 15%");
                correctDiscount = false;
            }
            else if (differenceDays <= 7 && differenceDays > 3 && discountPercent > 20) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("For a 7 day period the maximum discount is 20%");
                correctDiscount = false;
            }
            else if (differenceDays <= 3 && differenceDays > 1 && discountPercent > 30) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("For a 3 day period the maximum discount is 30%");
                correctDiscount = false;
            }
            else if (discountPercent > 50) {
                errorLabel.setVisible(true);
                sucessfulLabel.setVisible(false);
                errorLabel.setText("For a 1 day period the maximum discount is 50%");
                correctDiscount = false;
            }
        }

        return correctDiscount;
    }

    public void addOffer(ActionEvent actionEvent) {
        if (validateFields()) {
            //Retrieve all the variables to insert the offer
            Product selectedProduct = (Product) productCmb.getValue();
            int productID = selectedProduct.getProductId();
            LocalDate fromDate = fromDtt.getValue();
            LocalDate toDate = toDtt.getValue();
            Double discount = Double.parseDouble(discountField.getText().replace("%",""));

            //Create BBDD variables
            SessionFactory sessionFactory;
            Session session = null;

            try {
                //Open the session
                sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Seller.class)
                        .buildSessionFactory();
                session = sessionFactory.openSession();

                //Create the offer
                SellerProduct offer = new SellerProduct().getSellerProduct(session, _seller.getSellerId(), productID);

                if (offer != null) {
                    //Start the transaction
                    session.beginTransaction();

                    //Set the offer parameters
                    offer.setOfferPrice(offer.getPrice() * (discount/100));
                    offer.setOfferStartDate(fromDate);
                    offer.setOfferEndDate(toDate);

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

            errorLabel.setVisible(false);
            sucessfulLabel.setVisible(true);
        }
    }

    //Method to change views
    @FXML
    private void changeView(MouseEvent event) throws IOException {
        Object source = event.getSource();
        if (source instanceof ImageView) {
            String id = ((ImageView) source).getId();

            //See what view to load by ID
            try {
                FXMLLoader loader = null;
                switch (id) {
                    case "sellerDataImg":
                        loader = new FXMLLoader(getClass().getResource("SellerData-View.fxml"));
                        break;
                    case "addOfferImg":
                        loader = new FXMLLoader(getClass().getResource("AddOffer-View.fxml"));
                        break;
                    case "exitImg":
                        showExitConfirmation();
                        break;
                }

                //Load the view
                if (loader != null) {
                    AnchorPane newView = loader.load();
                    if (id.equals("addOfferImg")) {
                        AddOfferController addOfferController = loader.getController();
                        addOfferController.setSellerAndFields(_seller);
                    }
                    if (id.equals("sellerDataImg")) {
                        SellerDataController sellerDataController = loader.getController();
                        sellerDataController.setParametersSeller(_seller);
                    }

                    mainContent.getChildren().setAll(newView);
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private void showExitConfirmation() {
        //Creation of the alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit confirmation");
        alert.setHeaderText("Are you sure you want to leave?");
        alert.setContentText("If you exit, you will lose any unsaved changes.");

        //Dialogue options
        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        //If the user press yes
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonYes) {
            Platform.exit();
        }
    }
}
