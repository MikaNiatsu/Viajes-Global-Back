package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.ActivityDTO;
import co.edu.unbosque.bookingback.model.ActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;

import co.edu.unbosque.bookingback.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	
	public ActivityService() {

	}
	public List<ActivityDTO> findAll() {
		return activityRepository.findAll().stream()
				.map(DataMapper::activityEntityToDTO)
				.collect(Collectors.toList());
	}
	public ActivityDTO findById(int id) {
		ActivityEntity entity = activityRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
		return DataMapper.activityEntityToDTO(entity);
	}
	public ActivityDTO save(ActivityDTO activityDTO) {
		ActivityEntity entity = DataMapper.activityDTOtoEntity(activityDTO);
		ActivityEntity savedEntity = activityRepository.save(entity);
		return DataMapper.activityEntityToDTO(savedEntity);
	}
	public void deleteById(int id) {
		activityRepository.deleteById(id);
	}
	
}
