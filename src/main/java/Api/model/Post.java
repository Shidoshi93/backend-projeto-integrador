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

    @Column(nullable = false)
    private String status;

    @Column(name = "user_id", nullable = false)
    private Integer user_id;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id.equals(post.id) && user_type.equals(post.user_type) && donation_type.equals(post.donation_type) && description.equals(post.description) && qtd.equals(post.qtd) && status.equals(post.status) && user_id.equals(post.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_type, donation_type, description, qtd, status, user_id);
    }

    public Post() {
    }

    public Post(Integer id, String user_type, String donation_type, String description, Integer qtd, String status, Integer user_id) {
        this.id = id;
        this.user_type = user_type;
        this.donation_type = donation_type;
        this.description = description;
        this.qtd = qtd;
        this.status = status;
        this.user_id = user_id;
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

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Integer getUser() { return this.user_id; }

    public void setUser(Integer user_id) { this.user_id = user_id; }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_type='" + user_type + '\'' +
                ", donation_type='" + donation_type + '\'' +
                ", description='" + description + '\'' +
                ", qtd=" + qtd +
                ", status='" + status + '\'' +
                '}';
    }
}
