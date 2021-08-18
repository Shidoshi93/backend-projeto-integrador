package Api.model;

import org.springframework.test.web.servlet.MvcResult;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private File user_photo;

    @JoinColumn(name = "id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @JoinColumn(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    public User(){

    }

    public User(Integer id, String name, String email, String cpf, String cellphone, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.cellphone = cellphone;
        this.password = password;
    }

    public User(Integer id, String name, String email, String cpf, String cellphone, String password, File user_photo){
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.cellphone = cellphone;
        this.password = password;
        this.user_photo = user_photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public File getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(File user_photo) {
        this.user_photo = user_photo;
    }

    public void subirFotoPerfil() {
        BufferedImage imagemQualquer = null;
        try {
            imagemQualquer = ImageIO.read(new File("caminho_da_imagem.jpg"));
        } catch (IOException e) {
        }

        //        NomeDaClasse instancia = new NomeDaClasse();
        //        instancia.setImage(imagemQualquer);
    }
}
