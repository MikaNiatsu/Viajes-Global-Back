package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.HotelDTO;
import co.edu.unbosque.bookingback.model.HotelEntity;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.bookingback.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public HotelService() {
	}
	public void deleteById(int id) {
		hotelRepository.deleteById(id);
	}
	public List<HotelDTO> findAll() {
		return hotelRepository.findAll().stream()
				.map(DataMapper::hotelEntityToDTO)
				.collect(Collectors.toList());
	}

	public HotelDTO findById(int id) {
		HotelEntity entity = hotelRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
		return DataMapper.hotelEntityToDTO(entity);
	}

	public HotelEntity findByIdEntity(int id) {
		Optional<HotelEntity> entity = hotelRepository.findById(id);
		return entity.orElse(null);
	}

	public HotelDTO save(HotelDTO hotelDTO) {
		HotelEntity entity = DataMapper.hotelDTOtoEntity(hotelDTO);
		HotelEntity savedEntity = hotelRepository.save(entity);
		return DataMapper.hotelEntityToDTO(savedEntity);
	}
}
