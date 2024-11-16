package co.edu.unbosque.clientesback.controller;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testGetClientById() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setCustomer_id(1);
        clientDTO.setName("Nicolas");
        when(clientService.findById(1)).thenReturn(clientDTO);

        ClientDTO result = clientController.getClientById(1);

        assertEquals(1, result.getCustomer_id());
        assertEquals("Nicolas", result.getName());
        verify(clientService, times(1)).findById(1);
    }

    @Test
    void testGetClientByEmail() {
        when(clientService.findByEmail("nbeltran@gmail.com")).thenReturn(true);

        boolean result = clientController.getClientByEmail("nbeltran@gmail.com");

        assertTrue(result);
        verify(clientService, times(1)).findByEmail("nbeltran@gmail.com");
    }

    @Test
    void testRegisterClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Nicolas");
        clientDTO.setEmail("nbeltran@gmail.com");
        when(clientService.registerClient(clientDTO)).thenReturn(true);

        boolean result = clientController.registerClient(clientDTO);

        assertTrue(result);
        verify(clientService, times(1)).registerClient(clientDTO);
    }

    @Test
    void testUpdateClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("Nicolas Updated");
        clientDTO.setEmail("nbeltran@gmail.com");
        when(clientService.updateClient(clientDTO)).thenReturn(true);

        boolean result = clientController.updateClient(clientDTO);

        assertTrue(result);
        verify(clientService, times(1)).updateClient(clientDTO);
    }

    @Test
    void testLoginSuccess() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "nbeltran@gmail.com");
        body.put("password", "password123");

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setEmail("nbeltran@gmail.com");
        clientDTO.setPassword("password123");

        when(clientService.findByEmailDTO("nbeltran@gmail.com")).thenReturn(clientDTO);
        when(clientService.validatePassword("password123", "password123")).thenReturn(true);

        ResponseEntity<Object> response = clientController.login(body);

        assertEquals(202, response.getStatusCodeValue());
        assertEquals(clientDTO, response.getBody());
        verify(clientService, times(1)).findByEmailDTO("nbeltran@gmail.com");
        verify(clientService, times(1)).validatePassword("password123", "password123");
    }

    @Test
    void testLoginFailure() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "nbeltran@gmail.com");
        body.put("password", "wrongpassword");

        when(clientService.findByEmailDTO("nbeltran@gmail.com")).thenReturn(null);

        ResponseEntity<Object> response = clientController.login(body);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Cliente o detalles no existen", response.getBody());
        verify(clientService, times(1)).findByEmailDTO("nbeltran@gmail.com");
    }

    @Test
    void testGetAllClients() {
        ClientDTO client1 = new ClientDTO();
        client1.setCustomer_id(1);
        client1.setName("Nicolas");

        ClientDTO client2 = new ClientDTO();
        client2.setCustomer_id(2);
        client2.setName("Juan");

        when(clientService.findAll()).thenReturn(Arrays.asList(client1, client2));

        ResponseEntity<List<ClientDTO>> response = clientController.getAllClients();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(clientService, times(1)).findAll();
    }

    @Test
    void testDeleteClientSuccess() {
        when(clientService.deleteById(1)).thenReturn(true);

        ResponseEntity<Void> response = clientController.deleteClient(1);

        assertEquals(200, response.getStatusCodeValue());
        verify(clientService, times(1)).deleteById(1);
    }

    @Test
    void testDeleteClientFailure() {
        when(clientService.deleteById(1)).thenReturn(false);

        ResponseEntity<Void> response = clientController.deleteClient(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(clientService, times(1)).deleteById(1);
    }

    @Test
    void testUpdatePassword() {
        Map<String, String> body = new HashMap<>();
        body.put("email", "nbeltran@gmail.com");
        body.put("newPassword", "newpassword123");

        when(clientService.updatePassword("nbeltran@gmail.com", "newpassword123")).thenReturn(true);

        boolean result = clientController.updatePassword(body);

        assertTrue(result);
        verify(clientService, times(1)).updatePassword("nbeltran@gmail.com", "newpassword123");
    }
}
