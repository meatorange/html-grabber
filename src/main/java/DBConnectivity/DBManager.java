package DBConnectivity;

import DBConnectivity.DAO.AppSession;
import DBConnectivity.DAO.UniqueWord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBManager {

    private String configFileName;
    private Session session;

    public DBManager(String configFileName) {
        this.configFileName = configFileName;

        SessionFactory factory = new Configuration()
                .configure(configFileName)
                .addAnnotatedClass(AppSession.class)
                .addAnnotatedClass(UniqueWord.class)
                .buildSessionFactory();

        session = factory.getCurrentSession();
        session.beginTransaction();
    }

    public void saveSession(AppSession session) {
        this.session.save(session);
    }

    public void close() {
        session.getTransaction().commit();
        session.close();
    }

}
