package Api.model;

import javax.persistence.*;

@Entity
@Table(name = "address_pi")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "postalCode", nullable = false, length = 50)
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Address(){

    }

    public Address(Integer id, String neighborhood, String city, String state, String postalCode, User user) {
        this.id = id;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.user = user;
    }

    public Integer getAddress_id() { return id; }

    public void setAddress_id(Integer id) { this.id = id; }

    public String getNeighborhood() { return neighborhood; }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() { return postalCode; }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}

