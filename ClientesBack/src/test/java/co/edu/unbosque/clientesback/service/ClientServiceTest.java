package co.edu.unbosque.clientesback.service;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.model.ClientEntity;
import co.edu.unbosque.clientesback.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testFindById() {
        ClientEntity clientEntity = new ClientEntity(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));

        ClientDTO result = clientService.findById(1);

        assertEquals(1, result.getCustomer_id());
        assertEquals("Nicolas", result.getName());
        assertEquals("nbeltran@gmail.com", result.getEmail());
        verify(clientRepository, times(1)).findById(1);
    }

    @Test
    void testRegisterClientSuccess() {
        ClientDTO clientDTO = new ClientDTO(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(clientRepository.existsByEmail(clientDTO.getEmail())).thenReturn(false);
        when(clientRepository.existsByName(clientDTO.getName())).thenReturn(false);

        boolean result = clientService.registerClient(clientDTO);

        assertTrue(result);
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void testRegisterClientFailure() {
        ClientDTO clientDTO = new ClientDTO(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        when(clientRepository.existsByEmail(clientDTO.getEmail())).thenReturn(true);

        boolean result = clientService.registerClient(clientDTO);

        assertFalse(result);
        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    void testValidatePassword() {
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);

        boolean result = clientService.validatePassword("password123", "encodedPassword");

        assertFalse(result);
    }

    @Test
    void testFindAll() {
        ClientEntity client1 = new ClientEntity(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        ClientEntity client2 = new ClientEntity(2, "mmiguel@gmail.com", "Miguel", "0987654321", "password123");
        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<ClientDTO> result = clientService.findAll();

        assertEquals(2, result.size());
        assertEquals("Nicolas", result.get(0).getName());
        assertEquals("Miguel", result.get(1).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testDeleteByIdSuccess() {
        doNothing().when(clientRepository).deleteById(1);

        boolean result = clientService.deleteById(1);

        assertTrue(result);
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteByIdFailure() {
        doThrow(new RuntimeException("Error deleting client")).when(clientRepository).deleteById(1);

        boolean result = clientService.deleteById(1);

        assertFalse(result);
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindByName() {
        ClientEntity clientEntity = new ClientEntity(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        when(clientRepository.findByname("Nicolas")).thenReturn(Optional.of(clientEntity));

        ClientDTO result = clientService.findByname("Nicolas");

        assertEquals("Nicolas", result.getName());
        assertEquals("nbeltran@gmail.com", result.getEmail());
        verify(clientRepository, times(1)).findByname("Nicolas");
    }

    @Test
    void testUpdatePasswordSuccess() {
        ClientEntity clientEntity = new ClientEntity(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "oldPassword");
        when(clientRepository.findByEmail("nbeltran@gmail.com")).thenReturn(Optional.of(clientEntity));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedPassword");

        boolean result = clientService.updatePassword("nbeltran@gmail.com", "newPassword");

        assertTrue(result);
        verify(clientRepository, times(1)).save(clientEntity);
    }

    @Test
    void testUpdatePasswordFailure() {
        when(clientRepository.findByEmail("nbeltran@gmail.com")).thenReturn(Optional.empty());

        boolean result = clientService.updatePassword("nbeltran@gmail.com", "newPassword");

        assertFalse(result);
        verify(clientRepository, never()).save(any(ClientEntity.class));
    }

    @Test
    void testUpdateClient() {
        ClientEntity clientEntity = new ClientEntity(1, "nbeltran@gmail.com", "Nicolas", "1234567890", "password123");
        ClientDTO clientDTO = new ClientDTO(1, "updated@gmail.com", "Updated Nicolas", "0987654321", "password123");
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));

        boolean result = clientService.updateClient(clientDTO);

        assertTrue(result);
        assertEquals("updated@gmail.com", clientEntity.getEmail());
        assertEquals("Updated Nicolas", clientEntity.getName());
        assertEquals("0987654321", clientEntity.getPhone());
        verify(clientRepository, times(1)).save(clientEntity);
    }
}
