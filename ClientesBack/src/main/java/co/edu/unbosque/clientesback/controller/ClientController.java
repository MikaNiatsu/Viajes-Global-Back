package co.edu.unbosque.clientesback.controller;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ClientDTO getClientById(@PathVariable int clientId) {
        return clientService.findById(clientId);
    }

    @PostMapping("/getByEmail")
    public boolean getClientByEmail(@RequestBody String email) {
        return clientService.findByEmail(email);
    }

    @PostMapping("/register")
    public boolean registerClient(@RequestBody ClientDTO clientDTO) {
        return clientService.registerClient(clientDTO);
    }

    @PostMapping("/update")
    public boolean updateClient(@RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(clientDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String password = body.get("password");

        ClientDTO clientDTO = clientService.findByEmailDTO(email);

        if (clientDTO != null && clientService.validatePassword(password, clientDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente o detalles no existen");
        }
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        if (clientService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/updatePassword")
    public boolean updatePassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("newPassword");
        {
            return clientService.updatePassword(email, password);
        }
    }
}
