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
import java.util.stream.Collectors;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public ClientDTO findById(int id){
		ClientEntity entity = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found for id: " + id));
		return DataMapper.clientEntitytoDTO(entity);
	}
	public ClientDTO registerClient(ClientDTO clientDTO){
		String encryptedPassword = passwordEncoder.encode(clientDTO.getPassword());

		ClientEntity clientEntity = new ClientEntity(
				clientDTO.getCustomer_id(),
				clientDTO.getEmail(),
				clientDTO.getName(),
				clientDTO.getPhone(),
				clientDTO.getPassword()
		);

		clientRepository.save(clientEntity);

		return clientDTO;
	}

	public boolean validatePassword(String password, String encondedPassword){
		return passwordEncoder.matches(password,encondedPassword);
	}
	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream()
				.map(DataMapper::clientEntitytoDTO)
				.collect(Collectors.toList());
	}
	public void deleteById(int id) {
		clientRepository.deleteById(id);
	}
	public ClientDTO findByname(String name) {
		ClientEntity client = clientRepository.findByname(name)
				.orElseThrow(() -> new RuntimeException("Client not found with user: " + name));
		return DataMapper.clientEntitytoDTO(client);
	}
	public ClientService() {

	}

}
