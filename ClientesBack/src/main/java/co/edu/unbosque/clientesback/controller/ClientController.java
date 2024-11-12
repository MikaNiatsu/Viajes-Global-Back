package co.edu.unbosque.clientesback.controller;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.model.ClientEntity;
import co.edu.unbosque.clientesback.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/clients")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ClientDTO getClientById(@PathVariable int id) {
        return clientService.findById(id);
    }

    @PostMapping("/register")
    public ClientDTO registerClient(@RequestBody ClientDTO clientDTO){
        return clientService.registerClient(clientDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String user,
            @RequestParam String password) {

        ClientDTO clientDTO = clientService.findByname(user);

        if (clientDTO != null && clientService.validatePassword(password, clientDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Login con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente o detalles no existen");
        }
    }
    @GetMapping("/showAll")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
