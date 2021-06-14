import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriter {

    private final BufferedWriter out;

    public FileWriter(String filename) throws IOException {
        out = new BufferedWriter(
                new java.io.FileWriter(filename)
        );
    }

    public void writeLine(String line) throws IOException {
        out.write(line);
    }

    public void writeLineWithLineSeparator(String line) throws IOException {
        out.write(line + System.lineSeparator());
    }

    public void close() throws IOException {
        out.close();
    }

}
