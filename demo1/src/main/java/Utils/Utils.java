package Utils;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;

import static org.opencv.core.CvType.CV_32F;

public class Utils {


    public static Mat prepareImage(Mat image) {
        Imgproc.resize(image, image, new Size(160, 160), 0, 0, Imgproc.INTER_LINEAR);
        image.convertTo(image, CV_32F, 1.0 / 255.0); // Normalisation entre 0 et 1
        Mat rgbImage = new Mat();
        Imgproc.cvtColor(image, rgbImage, Imgproc.COLOR_BGR2RGB);

        // Aligner le visage

        return rgbImage; // Retourner l'image alignée

    }

    public static float[] matToFloatArray(Mat mat) {
        int size = (int) (mat.total() * mat.channels());
        float[] floatArray = new float[size];
        mat.get(0, 0, floatArray);
        return floatArray;
    }
    public static Mat resizeImage(Mat image, int size) {
        Mat resizedImage = new Mat();
        Imgproc.resize(image, resizedImage, new Size(size, size));
        return resizedImage;
    }
    public static Image matToImage(Mat mat) {
        // Convertir Mat en MatOfByte
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", mat, buffer);

        // Créer une Image à partir de ByteArrayInputStream
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }






}
