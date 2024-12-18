package org.example.demo1;

import Database.DatabaseService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AddUserDialogController {

    @FXML
    private RadioButton authorizedRadi;

    @FXML
    private TextField firstnameid;
    @FXML
    private TextField lastnameid;
    @FXML
    private Button imageid;


    private String imagePath;



    private DatabaseService databaseService;

    public AddUserDialogController() {
    }
    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    @FXML
    private void chooseImage() {
        // Ouvrir un FileChooser pour choisir une image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
    }
    @FXML
    private void addUser() {

        String firstname = firstnameid.getText();

        byte[] embedding = loadImageAsBytes(imagePath);


        System.out.println("Utilisateur ajouté : " + firstname );

        closeDialog();

    }
    @FXML
    private void cancel() {
        closeDialog();
    }

    private void closeDialog() {
        // Fermez la fenêtre actuelle
        ((Stage) firstnameid.getScene().getWindow()).close();
    }

    private byte[] loadImageAsBytes(String imagePath) {
        // Implémentez cette méthode pour charger l'image et la convertir en tableau de bytes
        return new byte[0]; // Placeholder, à implémenter selon vos besoins
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }








}
