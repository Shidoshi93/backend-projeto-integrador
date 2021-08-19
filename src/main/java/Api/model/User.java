package Api.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "user_pi", schema = "pi_db")
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "user_name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "cellphone", nullable = false, length = 50)
    private String cellphone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_img", nullable = true)
    private String user_photo;

    @JoinColumn(name = "id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    public User(){

    }

    public User(Integer id, String name, String email, String cpf, String cellphone, String password, String user_photo){
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.cellphone = cellphone;
        this.password = password;
        this.user_photo = user_photo;
    }

    public Integer getUser_id() {
        return id;
    }

    public void setUser_id(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCellphone() { return cellphone; }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

}
