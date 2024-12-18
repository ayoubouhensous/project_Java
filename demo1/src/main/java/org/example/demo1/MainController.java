package org.example.demo1;

import Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import models.CameraService;
import models.FaceDetection;
import models.FaceEmbedding;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class MainController {
    @FXML
    private ImageView cameraView;

    private CameraService cameraService;

    private float[] referenceEmbeddings;


    public void initialize() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Charger la bibliothèque OpenCV
        cameraService = new CameraService(cameraView);
        cameraService.startCamera();
        loadReferenceImage("C:\\Users\\ayoub\\Desktop\\Nouveau dossier (2)\\face_0.jpg");
    }
    private void loadReferenceImage(String imagePath) {
        Mat referenceImage = Imgcodecs.imread(imagePath); // Charger l'image de référence
        if (referenceImage.empty()) {
            System.err.println("Erreur de chargement de l'image de référence.");
            return;
        }


        if (referenceImage != null) {
            FaceEmbedding faceEmbedding = new FaceEmbedding();
            referenceEmbeddings = faceEmbedding.calculateEmbeddings(referenceImage); // Calculer les embeddings
        } else {
            System.err.println("Aucun visage détecté dans l'image de référence.");
        }
    }

    private static double calculateDistance(float[] embeddings1, float[] embeddings2) {
        double sum = 0.0;
        for (int i = 0; i < embeddings1.length; i++) {
            sum += Math.pow(embeddings1[i] - embeddings2[i], 2);
        }
        return Math.sqrt(sum); // Distance euclidienne
    }


    @FXML
    private void captureAndProcess() {
        Mat capturedImage = cameraService.captureFrame();

        if (capturedImage != null) {

            // Détection et calcul des embeddings
            FaceDetection faceDetection = new FaceDetection();
            Mat detectedFace = faceDetection.detectFace(capturedImage);


            if (detectedFace != null) {
                FaceEmbedding faceEmbedding = new FaceEmbedding();
                float[] embeddings = faceEmbedding.calculateEmbeddings(detectedFace);
                if (referenceEmbeddings != null) {
                    double distance = calculateDistance(referenceEmbeddings, embeddings);
                    System.out.println("Distance entre les embeddings : " + distance);
                } else {
                    System.err.println("Embeddings de référence non disponibles.");
                }



            }
            }
        }

    }




