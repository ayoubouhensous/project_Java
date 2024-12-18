package org.example.demo1;

import Database.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private DatabaseService databaseService;
    private String imagePath; // pour stocker le chemin de l'image (déjà déclaré)




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
    private void addUserToDatabase(String firstname,String lastname, String imagePath, String status) {
        if (firstname.isEmpty() || imagePath == null) {
            showAlert("Error", "Name and image must be provided!");
            return;
        }

        try {
            byte[] imageData = Files.readAllBytes(new File(imagePath).toPath());
            User user = new User(null,firstname,lastname ,imageData, status);
            databaseService.addUser(user);
            showAlert("Success", "User added successfully!");
        } catch (IOException e) {
            showAlert("Error", "Failed to upload image!");
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
