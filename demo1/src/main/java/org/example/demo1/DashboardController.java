package org.example.demo1;

import Database.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DashboardController {

    @FXML
    private Button addUserButton;

    @FXML
    private Label totaluser;

    @FXML
    private Label totalattemp;

    @FXML
    private Label echec;

    @FXML
    private Label succes;

    @FXML
    private Button logoutButton;

    private DatabaseService databaseService;
    private String imagePath;

    @FXML
    public void initialize() {
        initializeDashboardData();
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    public void initializeDashboardData() {
        if (databaseService != null) {
            try {
                int userCount = databaseService.countUsers();
                totaluser.setText(String.valueOf(userCount));

                int AtteCount = databaseService.countAttemps();
                totalattemp.setText(String.valueOf(AtteCount));

                int AccesLog = databaseService.countStatusLogs("acces");
                succes.setText(String.valueOf(AccesLog));

                int EchecLog = databaseService.countStatusLogs("echec");
                echec.setText(String.valueOf(EchecLog));

            } catch (Exception e) {
                e.printStackTrace();
                totaluser.setText("Erreur");
            }
        }
    }






    public DashboardController() {
        this.databaseService = new DatabaseService(); // Instanciez votre service
    }


    @FXML
    private void handleAddUserButtonAction() {
        showAddUserDialog();
    }


    private void showAddUserDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(DashboardController.class.getResource("AddUserDialog.fxml"));
            AnchorPane dialogPane = loader.load();

            // Passer le DatabaseService au contrôleur
            AddUserDialogController controller = loader.getController();
            controller.setDatabaseService(databaseService);
            controller.setDashboardController(this); // Passer la référence du tableau de bord


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter un Utilisateur");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(addUserButton.getScene().getWindow());
            dialogStage.setScene(new Scene(dialogPane));
            dialogStage.showAndWait();
        } catch (IOException e) {
            showAlert("Erreur", "Échec de l'ouverture de la fenêtre d'ajout !");
            e.printStackTrace();
        }
    }







    @FXML
    private void handleLogout() {
        try {
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            Parent root = loader.load();

            // Ouvrir la nouvelle scène
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // Assurez-vous que `logoutButton` est déclaré
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
