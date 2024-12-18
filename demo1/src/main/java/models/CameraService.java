package models;

import Utils.Utils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class CameraService {

    private VideoCapture videoCapture;
    private ImageView cameraView;


    public CameraService(ImageView cameraView) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Charger la bibliothèque OpenCV

        this.cameraView = cameraView;
        this.videoCapture = new VideoCapture(0); // 0 pour la caméra par défaut
    }
public void afficher(){
    System.out.println("aa");
}
    public void startCamera() {
        Thread cameraThread = new Thread(() -> {
            Mat frame = new Mat();
            while (videoCapture.isOpened()) {
                if (videoCapture.read(frame)) {
                    Image image = Utils.matToImage(frame);
                    Platform.runLater(() -> cameraView.setImage(image));
                }
            }
        });
        cameraThread.setDaemon(true);
        cameraThread.start();
    }
    public Mat captureFrame() {
        Mat frame = new Mat();
        if (videoCapture.isOpened()) {
            videoCapture.read(frame);
        }
        return frame;
    }

    public void stopCamera() {
        if (videoCapture.isOpened()) {
            videoCapture.release();
        }
    }


}
