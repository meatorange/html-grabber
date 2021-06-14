package DBConnectivity.DAO;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "sessions")
public class AppSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "filename")
    private String filename;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    List<UniqueWord> words;

    public List<UniqueWord> getWords() {
        return words;
    }

    public AppSession() {}

    public AppSession(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    public AppSession addUniqueWord(UniqueWord word) {
        if (words == null) {
            words = new LinkedList<UniqueWord>();
        }

        word.setSession(this);
        words.add(word);
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "AppSession{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
