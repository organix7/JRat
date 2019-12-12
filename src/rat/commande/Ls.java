package rat.commande;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Ls {

    static public String list(Path currentPath) {
        StringBuilder sb = new StringBuilder();

        try {
            Files.list(currentPath).forEach(p -> sb.append(p.getFileName().toString()).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
