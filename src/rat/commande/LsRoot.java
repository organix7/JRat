package rat.commande;

import java.io.File;

public class LsRoot {

    static public String  getDisk(){
        StringBuilder sb = new StringBuilder();

        for (File file : File.listRoots()) {
            sb.append(file.toString());
        }
        return sb.toString();
    }
}
