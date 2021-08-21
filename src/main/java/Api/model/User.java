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
    private String user_name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "cellphone", nullable = false, length = 50)
    private String cellphone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_img", nullable = true)
    private String user_img;

    public User(){

    }

    public User(Integer id, String name, String email, String cpf, String cellphone, String password, String user_photo){

        this.id = id;
        this.user_name = name;
        this.email = email;
        this.cpf = cpf;
        this.cellphone = cellphone;
        this.password = password;
        this.user_img = user_photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

}
