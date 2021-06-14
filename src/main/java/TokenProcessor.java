import java.util.*;

public class TokenProcessor {

    private static final String DEFAULT_DELIMITERS = " ,.!?\"';:[]{}()<>\n\r\t1234567890@#^&*~\\/+=|";

    private String delimiters;
    Map<String, Integer> data = new HashMap<>();

    public TokenProcessor() {
        delimiters = DEFAULT_DELIMITERS;
    }

    public TokenProcessor(String delimiters) {
        this.delimiters = delimiters;
    }


    public void addLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, delimiters);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();

            if (data.containsKey(token)) {
                data.replace(token, data.get(token) + 1);
            }
            else {
                data.put(token, 1);
            }
        }
    }

    public Map<String, Integer> getData() {
        return data;
    }

}
