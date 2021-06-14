package DBConnectivity.DAO;

import javax.persistence.*;

@Entity
@Table(name = "unique_words")
public class UniqueWord {


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "session_id")
    private AppSession session;

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "value")
    private int value;

    public UniqueWord() {}

    public UniqueWord(String token, int value) {
        this.token = token;
        this.value = value;
    }

    public AppSession getSession() {
        return session;
    }

    public void setSession(AppSession session) {
        this.session = session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "token='" + token + '\'' +
                ", value=" + value +
                '}';
    }

}
