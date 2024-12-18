package org.example.demo1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean t =true;
        // Ici, vous pouvez ajouter une vérification d'identifiants
        if (t == true) {
            // Si les informations d'identification sont valides, chargez la page de la caméra
            openDashboard();
        } else {
            // Afficher un message d'erreur ou une notification
            System.out.println("Invalid credentials. Please try again.");
        }
    }
    private boolean isValidCredentials(String username, String password) {
        // Remplacez ceci par votre logique de vérification (ex : vérifier avec une base de données)
        return "admin".equals(username) && "password".equals(password);
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
