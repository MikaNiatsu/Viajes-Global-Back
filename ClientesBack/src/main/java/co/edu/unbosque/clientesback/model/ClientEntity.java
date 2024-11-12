package co.edu.unbosque.clientesback.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Customers")
@Getter
@Setter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;
    private String email;
    private String name;
    private String phone;
    private String password;

    public ClientEntity() {

    }

    public ClientEntity(int customer_id, String email, String name, String phone, String password) {
        this.customer_id = customer_id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }
}
