package co.edu.unbosque.clientesback.service;

import co.edu.unbosque.clientesback.model.ClientDTO;
import co.edu.unbosque.clientesback.model.ClientEntity;

public class DataMapper {
    public static ClientDTO clientEntitytoDTO(ClientEntity entity) {
        return new ClientDTO(
                entity.getCustomer_id(),
                entity.getEmail(),
                entity.getName(),
                entity.getPhone(),
                entity.getPassword()
        );
    }

    public static ClientEntity clientDTOtoEntity(ClientDTO dto) {
        return new ClientEntity(
                dto.getCustomer_id(),
                dto.getEmail(),
                dto.getName(),
                dto.getPhone(),
                dto.getPassword()
        );
    }
}
