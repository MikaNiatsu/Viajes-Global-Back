package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.BookingDTO;
import co.edu.unbosque.bookingback.model.BookingEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.bookingback.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	
	public BookingService() {

	}
	public List<BookingDTO> findAll() {
		return bookingRepository.findAll().stream()
				.map(DataMapper::bookingEntityToDTO)
				.collect(Collectors.toList());
	}
	public BookingDTO findById(int id){
		BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found for id: " + id));
		return DataMapper.bookingEntityToDTO(entity);
	}
	public BookingDTO save(BookingDTO bookingDTO) {
		BookingEntity entity = DataMapper.bookingDTOtoEntity(bookingDTO);
		BookingEntity savedEntity = bookingRepository.save(entity);
		return DataMapper.bookingEntityToDTO(savedEntity);
	}

	public void deleteById(int id) {
		bookingRepository.deleteById(id);
	}
}
