package org.example.demo1;

import Database.DatabaseService;
import Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.User;
import models.FaceDetection;
import models.FaceEmbedding;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;


public class AddUserDialogController {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @FXML
    private TextField firstnameid;
    @FXML
    private TextField lastnameid;
    @FXML
    private Button imageid;
    private String imagePath;
    FaceEmbedding faceEmbedding = new FaceEmbedding();
    FaceDetection faceDetection = new FaceDetection();

    DashboardController dashboardController = new DashboardController();


    private DatabaseService databaseService;

    public AddUserDialogController() {
    }
    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
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
        String lastname = lastnameid.getText();
        Mat img = faceDetection.detectFace(Imgcodecs.imread(imagePath));
        float[] embedding = faceEmbedding.calculateEmbeddings(img);
        byte[] embeddingbyte = Utils.convertFloatArrayToByteArray(embedding);
        databaseService.addUser(new User(firstname,lastname,embeddingbyte));
        System.out.println("Utilisateur ajouté : " + firstname +" "+ lastname);

        if (dashboardController != null) {
            dashboardController.initializeDashboardData();
        }
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