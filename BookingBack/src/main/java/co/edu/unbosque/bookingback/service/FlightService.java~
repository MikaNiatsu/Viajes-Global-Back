package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.FlightDTO;
import co.edu.unbosque.bookingback.model.FlightEntity;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.bookingback.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

	@Autowired
	private FlightRepository flightRepository;

	public FlightService() {

	}

	public void deleteById(int id) {
		flightRepository.deleteById(id);
	}

	public FlightDTO save(FlightDTO flightDTO) {
		FlightEntity entity = DataMapper.flightDTOtoEntity(flightDTO);
		FlightEntity savedEntity = flightRepository.save(entity);
		return DataMapper.flightEntityToDTO(savedEntity);
	}

	public FlightDTO findById(int id) {
		FlightEntity entity = flightRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
		return DataMapper.flightEntityToDTO(entity);
	}
	public List<FlightDTO> findAll() {
		return flightRepository.findAll().stream()
				.map(DataMapper::flightEntityToDTO)
				.collect(Collectors.toList());
	}

}
