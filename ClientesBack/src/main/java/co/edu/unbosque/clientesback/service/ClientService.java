package co.edu.unbosque.clientesback.service;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.model.ClientEntity;
import co.edu.unbosque.clientesback.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public ClientDTO findById(int id){
		ClientEntity entity = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found for id: " + id));
		return DataMapper.clientEntitytoDTO(entity);
	}
	public boolean registerClient(ClientDTO clientDTO){
		String encryptedPassword = passwordEncoder.encode(clientDTO.getPassword());
		if (clientRepository.existsByEmail(clientDTO.getEmail()) || clientRepository.existsByName(clientDTO.getName())) {
			return false;
		}
		ClientEntity clientEntity = new ClientEntity(
				clientDTO.getCustomer_id(),
				clientDTO.getEmail(),
				clientDTO.getName(),
				clientDTO.getPhone(),
				encryptedPassword
		);
		try {
			clientRepository.save(clientEntity);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validatePassword(String password, String encondedPassword){
		return passwordEncoder.matches(password,encondedPassword);
	}
	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream()
				.map(DataMapper::clientEntitytoDTO)
				.collect(Collectors.toList());
	}
	public boolean deleteById(int id) {
		try {
			clientRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public ClientDTO findByname(String name) {
		ClientEntity client = clientRepository.findByname(name)
				.orElseThrow(() -> new RuntimeException("Client not found with user: " + name));
		return DataMapper.clientEntitytoDTO(client);
	}
	public ClientService() {

	}

	public boolean updatePassword(String email, String password) {
		Optional<ClientEntity> client = clientRepository.findByEmail(email);
		if (client.isPresent()) {
			ClientEntity clientEntity = client.get();
			clientEntity.setPassword(passwordEncoder.encode(password));
			clientRepository.save(clientEntity);
			return true;
		}
		return false;
	}

	public boolean findByEmail(String email) {
		return clientRepository.existsByEmail(email);
	}

	public ClientDTO findByEmailDTO(String email) {
		ClientEntity client = clientRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Client not found with email: " + email));
		return DataMapper.clientEntitytoDTO(client);
	}

	public boolean updateClient(ClientDTO clientDTO) {
		Optional<ClientEntity> client = clientRepository.findById(clientDTO.getCustomer_id());
		if (client.isPresent()) {
			ClientEntity clientEntity = client.get();
			clientEntity.setEmail(clientDTO.getEmail());
			clientEntity.setName(clientDTO.getName());
			clientEntity.setPhone(clientDTO.getPhone());
			clientRepository.save(clientEntity);
			return true;
		}
		return false;
	}
}
