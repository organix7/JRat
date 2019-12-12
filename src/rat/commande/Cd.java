package rat.commande;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Cd {
    static public String  changeDirectory(Path currentPath, String directory){
        String dirChange;

        if(directory.matches("^..$"))
            return currentPath.getParent().toString();
        else if(directory.matches("^/$"))
            return currentPath.getRoot().toString();
        if(currentPath.toString().equals(currentPath.getRoot().toString()))
            dirChange = currentPath.toString()+directory;
        else
            dirChange = currentPath.toString()+"\\"+directory;
        if(Files.isDirectory(new File(dirChange).toPath())){
            return dirChange;
        }
        return "bad argument";
    }
}
