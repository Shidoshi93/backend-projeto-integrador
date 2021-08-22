package Api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post_pi")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_type", nullable = false, length = 50)
    private String user_type;

    @Column(name = "donation_type", nullable = false, length = 50)
    private String donation_type;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "qtd", nullable = false, length = 50)
    private Integer qtd;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(){

    }

    public Post(Integer id, String user_type, String donation_type, String description, Integer qtd, String status, User user) {
        this.id = id;
        this.user_type = user_type;
        this.donation_type = donation_type;
        this.description = description;
        this.qtd = qtd;
        this.status = status;
        this.user = user;
    }

    public int getPost_id() {
        return id;
    }

    public void setPost_id(Integer id) {
        this.id = id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getDonation_type() {
        return donation_type;
    }

    public void setDonation_type(String donation_type) {
        this.donation_type = donation_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post: {" +
                "id=" + this.id +
                ", user_type='" + this.user_type + '\'' +
                ", donation_type='" + this.donation_type + '\'' +
                ", description='" + this.description + '\'' +
                ", qtd=" + this.qtd +
                '}';
    }
}
