import DBConnectivity.DAO.AppSession;
import DBConnectivity.DAO.UniqueWord;
import DBConnectivity.DBManager;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Main {

    private static final String DEFAULT_CONFIG_FILENAME = "hibernate.cfg.xml";
    private static final String FILE_NAME = "result_";
    private static final String DATE_FORMAT = "HH-mm-ss_dd-MM-yyyy";
    private static final String FILE_FORMAT = ".html";
    private static final String DEFAULT_URL_ADDR = "https://www.simbirsoft.com/";

    private static String generateFileNameByDate(String base, String format) {
        return base + new SimpleDateFormat(DATE_FORMAT)   .format(new Date()) + format;
    }

    public static void main(String[] args) {

        String url = DEFAULT_URL_ADDR;
        String hibernateConfigFileName = DEFAULT_CONFIG_FILENAME;

        if (args != null) {
            if (args.length > 0) {
                url = args[0];
            }
            if (args.length > 1) {
                hibernateConfigFileName = args[1];
            }
        }

        HtmlLineReader htmlLineReader = null;
        FileWriter file = null;
        String filename = generateFileNameByDate(FILE_NAME, FILE_FORMAT);
        TokenProcessor tokens = new TokenProcessor();

        try {

            htmlLineReader = new HtmlLineReader(url);
            file = new FileWriter(filename);

            while (htmlLineReader.nextLine() == HtmlLineReader.HAS_LINE) {
                file.writeLineWithLineSeparator(htmlLineReader.getLine());
                tokens.addLine(htmlLineReader.getLine());
            }

            System.out.println("URL: " + url + "\nOutput file: " + filename + "Result:");

            Map<String, Integer> data = tokens.getData();
            AppSession session = new AppSession(url, filename);
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                UniqueWord word = new UniqueWord(entry.getKey(), entry.getValue());
                session.addUniqueWord(word);
                System.out.print(word + " ");
            }
            System.out.println();

            DBManager manager = null;

            try {
                manager = new DBManager(hibernateConfigFileName);
                manager.saveSession(session);
                System.out.println("Result save in database with session's id=" + session.getId());
            }
            catch (HibernateException e) {
                System.err.println(e.getMessage());
            }
            finally {
                try {
                    if (manager != null) {
                        manager.close();
                    }
                }
                catch (HibernateException e) {
                    System.err.println(e.getMessage());
                }
            }

        }
        catch (MalformedURLException e) {
            System.err.println("Bad url: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {

            try {
                if ((file != null)) {
                    file.close();
                }
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }

            try {
                if (htmlLineReader != null) {
                    htmlLineReader.close();
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
