package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.BookingDetailDTO;
import co.edu.unbosque.bookingback.model.BookingDetailEntity;
import co.edu.unbosque.bookingback.repository.BookingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDetailService {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    public BookingDetailService() {

    }
    public List<BookingDetailDTO> findAll() {
        return bookingDetailRepository.findAll().stream()
                .map(DataMapper::bookingDetailEntityToDTO)
                .collect(Collectors.toList());
    }
    public BookingDetailDTO findById(int id) {
        BookingDetailEntity entity = bookingDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking detail not found with id: " + id));
        return DataMapper.bookingDetailEntityToDTO(entity);
    }
    public BookingDetailDTO save(BookingDetailDTO bookingDetailDTO) {
        BookingDetailEntity entity = DataMapper.bookingDetailDTOtoEntity(bookingDetailDTO);
        BookingDetailEntity savedEntity = bookingDetailRepository.save(entity);
        return DataMapper.bookingDetailEntityToDTO(savedEntity);
    }
    public void deleteById(int id) {
        bookingDetailRepository.deleteById(id);
    }

}
