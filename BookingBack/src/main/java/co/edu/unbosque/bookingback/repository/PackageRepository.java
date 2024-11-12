package co.edu.unbosque.bookingback.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.bookingback.model.PackageEntity;

public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {

}
