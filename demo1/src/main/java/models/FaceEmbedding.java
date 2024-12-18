package models;

import Utils.Utils;
import org.opencv.core.Mat;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FaceEmbedding  {

    private static final String MODEL_PATH = "C:\\Users\\ayoub\\Desktop\\20180408-102900\\20180408-102900\\20180408-102900.pb"; // Remplacez par le chemin du modèle
    public float[] calculateEmbeddings(Mat face)  {
        Mat imageData = Utils.prepareImage(face);

        float[] preprocessedImage = new float[(int) (imageData.total() * imageData.channels())];
        imageData.get(0, 0, preprocessedImage);

        try (Graph graph = new Graph()) {
            byte[] graphDef = Files.readAllBytes(Paths.get(MODEL_PATH));
            graph.importGraphDef(graphDef);
            try (Session session = new Session(graph)) {
                Tensor<?> inputTensor = Tensor.create(new long[]{1, 160, 160, 3}, FloatBuffer.wrap(preprocessedImage));
                Tensor<?> phaseTrainTensor = Tensor.create(false);
                Tensor<?> outputTensor = session.runner()
                        .feed("input", inputTensor) // Remplacez "input" par le nom de l'entrée de votre modèle
                        .feed("phase_train", phaseTrainTensor)
                        .fetch("embeddings") // Remplacez "embeddings" par le nom de la sortie de votre modèle
                        .run()
                        .get(0);

                float[][] embeddings = new float[1][512];
                outputTensor.copyTo(embeddings);
                return embeddings[0];
            }


        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }







    }
