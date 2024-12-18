package models;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    private static final String FACE_CASCADE_PATH = "C:\\Users\\ayoub\\Desktop\\Nouveau dossier (5)\\haarcascade_frontalface_default.xml";

    private CascadeClassifier faceCascade;

    public FaceDetection() {
        faceCascade = new CascadeClassifier(FACE_CASCADE_PATH);
    }

    public Mat detectFace(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        MatOfRect faceDetections = new MatOfRect();

        faceCascade.detectMultiScale(grayImage, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            Mat face = new Mat(image, rect);

            return new Mat(image, rect); // Retourner la première région de visage détectée
        }
        return null;
    }

    }
