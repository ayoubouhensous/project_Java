package org.example.demo1;

import Database.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;


    @FXML
    private Button cancelButton;

    private DatabaseService db = new DatabaseService();


    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Vérification des identifiants
        if (db.isValidCredentials(username, password)) {
            // Si les informations d'identification sont valides, chargez la page de la caméra
            openDashboard();
        } else {
            // Afficher un message d'erreur dans une alerte
            showErrorAlert("Invalid credentials. Please try again.");
        }
    }


    private void showErrorAlert(String message) {
        // Création d'une alerte d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void outLogin() {
        // Masquer la fenêtre actuelle (c'est-à-dire la cacher sans la fermer)
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.hide();  // Masquer la fenêtre
    }












    private void loadCameraPage() {
        try {
            // Chargez la vue de caméra
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainController.fxml"));
            VBox cameraView = loader.load();

            // Montrez la nouvelle vue
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(cameraView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openDashboard() {
        try {
            // Charger la vue de la Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Parent root = loader.load();

            // Passer les informations de tentatives à la Dashboard
            DashboardController dashboardController = loader.getController();

            DatabaseService databaseService = new DatabaseService();
            dashboardController.setDatabaseService(databaseService);
            dashboardController.initializeDashboardData();

            // Ouvrir la nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre de connexion
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
