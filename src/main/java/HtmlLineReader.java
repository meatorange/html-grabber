import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HtmlLineReader {

    private String urlAddr;

    private final BufferedReader in;

    private String currentLine;

    public static final int
            OUT_OF_LINES = -1,
            HAS_LINE = 1;


    public HtmlLineReader(String url) throws IOException {
        urlAddr = url;

        URL u = new URL(url);

        try {
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            //connection.setRequestProperty();

            in = new BufferedReader( // goto .nio
                    new InputStreamReader(
                            connection.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            );
        }
        catch (IOException e) {
            throw new IOException("Connection lost: " + urlAddr, e);
        }
    }

    public int nextLine() throws IOException {
        try {
            if ((currentLine = in.readLine()) != null) {
                return HAS_LINE;
            }

            return OUT_OF_LINES;
        }
        catch (IOException e) {
            throw new IOException("ReadError: connection lost: " + urlAddr, e);
        }
    }

    public String getLine() {
        return currentLine;
    }

    public String getUrlAddr() {
        return urlAddr;
    }

    public void close() throws Exception {
        in.close();
    }

    @Override
    public String toString() {
        return currentLine;
    }

}
