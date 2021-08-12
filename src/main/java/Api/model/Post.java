package Api.model;

import javax.persistence.*;

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

    @Column(name = "user_id")
    private Integer user_id;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

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

    public Integer getUser() { return this.user_id; }

    public void setUser(Integer user_id) { this.user_id = user_id; }

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
