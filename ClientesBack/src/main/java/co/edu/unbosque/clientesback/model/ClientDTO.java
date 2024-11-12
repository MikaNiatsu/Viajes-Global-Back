package co.edu.unbosque.clientesback.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ClientDTO {

    private int customer_id;
    private String email;
    private String name;
    private String phone;
    private String password;
    public ClientDTO() {

    }

    public ClientDTO(int customer_id, String email, String name, String phone, String password) {
        this.customer_id = customer_id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;

    }

    public ClientDTO(ClientDTO clientDTO) {
    }
}
