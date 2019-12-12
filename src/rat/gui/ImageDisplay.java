package RAT.src.rat.gui;

import javax.swing.*;
import java.awt.*;

public class ImageDisplay extends JFrame{

    private JLabel jl;

    public ImageDisplay(String imageName) {
        setLayout(new BorderLayout());
        jl = new JLabel(new ImageIcon(imageName));
        add(jl,BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
