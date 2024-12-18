package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.model.PackageEntity;
import co.edu.unbosque.bookingback.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

	public int save(PackageDTO packageDTO) {
		PackageEntity entity = DataMapper.packageDTOToEntity(packageDTO);
		try {
			PackageEntity savedEntity = packageRepository.save(entity);
			return savedEntity.getPackage_id();
		} catch (Exception e) {
			return -1;
		}
	}

	public void deleteById(int id) {
		packageRepository.deleteById(id);
	}

	public PackageEntity findByIdEntity(int packageId) {
		Optional<PackageEntity> entity = packageRepository.findById(packageId);
		return entity.orElse(null);
	}
}
