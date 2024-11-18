package co.edu.unbosque.clientesback.controller;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
@Tag(name = "Client Controller", description = "Manage client-related operations")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Get client by ID", description = "Retrieve details of a client using their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{clientId}")
    public ClientDTO getClientById(@PathVariable int clientId) {
        return clientService.findById(clientId);
    }

    @Operation(summary = "Check if client exists by email", description = "Verify if a client exists based on their email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email checked successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    @PostMapping("/getByEmail")
    public boolean getClientByEmail(@RequestBody String email) {
        return clientService.findByEmail(email);
    }

    @Operation(summary = "Register a new client", description = "Create a new client entry in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid client data provided")
    })
    @PostMapping("/register")
    public boolean registerClient(@RequestBody ClientDTO clientDTO) {
        return clientService.registerClient(clientDTO);
    }

    @Operation(summary = "Update client information", description = "Modify the details of an existing client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid client data provided")
    })
    @PostMapping("/update")
    public boolean updateClient(@RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(clientDTO);
    }

    @Operation(summary = "Client login", description = "Validate client credentials and return client details if valid.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Login successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "404", description = "Invalid credentials or client not found")
    })
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

    @Operation(summary = "Get all clients", description = "Retrieve a list of all registered clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/showAll")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(summary = "Delete a client", description = "Remove a client entry from the system by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable int id) {
        if (clientService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update client password", description = "Change the password of a client.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password data provided")
    })
    @PostMapping("/updatePassword")
    public boolean updatePassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("newPassword");
        return clientService.updatePassword(email, password);
    }
}