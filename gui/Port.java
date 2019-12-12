package RAT.gui;

import RAT.Server;

import javax.swing.*;
import java.awt.*;

public class Port extends JFrame {  //Problem with display the screenshot

    JLabel jl2 = new JLabel("Port d'écoute:");
    JTextField textPort = new JTextField();
    JButton bOK = new JButton("OK");

    public Port(){
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setLayout(new FlowLayout());
        setSize(200,100);

        textPort.setPreferredSize(new Dimension(100,20));
        add(jl2);
        add(textPort);
        add(bOK);

        bOK.addActionListener((e)->{
            dispose();
            new Server(Integer.valueOf(textPort.getText()));
        });
    }
}
