package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.model.PackageEntity;
import co.edu.unbosque.bookingback.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {

	@Autowired
	private PackageRepository packageRepository;
	
	public PackageService() {

	}
	public List<PackageDTO> findAll() {
		return packageRepository.findAll().stream()
				.map(DataMapper::packageEntityToDTO)
				.collect(Collectors.toList());
	}
	public PackageDTO findById(int id) {
		PackageEntity entity = packageRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Package not found with id: " + id));
		return DataMapper.packageEntityToDTO(entity);
	}

	public PackageDTO save(PackageDTO packageDTO) {
		PackageEntity entity = DataMapper.packageDTOToEntity(packageDTO);
		PackageEntity savedEntity = packageRepository.save(entity);
		return DataMapper.packageEntityToDTO(savedEntity);
	}

	public void deleteById(int id) {
		packageRepository.deleteById(id);
	}
}
