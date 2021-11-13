import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Handler {

    public void fileWrite(String filepath, String text) throws IOException {
        File file = new File(filepath);
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.flush();
        writer.close();
    }

}
