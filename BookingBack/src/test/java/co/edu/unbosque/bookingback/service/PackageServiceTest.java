package co.edu.unbosque.bookingback.service;


import co.edu.unbosque.bookingback.model.PackageDTO;
import co.edu.unbosque.bookingback.model.PackageEntity;
import co.edu.unbosque.bookingback.repository.PackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSavePackage() {
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setPackage_id(1);
        packageDTO.setPrice(500);

        when(packageRepository.save(DataMapper.packageDTOToEntity(packageDTO))).thenReturn(DataMapper.packageDTOToEntity(packageDTO));

        PackageDTO savedPackage = packageService.save(packageDTO);

        assertEquals(packageDTO, savedPackage);
        verify(packageRepository, times(1)).save(DataMapper.packageDTOToEntity(packageDTO));
    }

    @Test
    void testFindById() {
        PackageDTO packageDTO = new PackageDTO();
        packageDTO.setPackage_id(1);
        packageDTO.setPrice(500);

        PackageEntity packageEntity = null;
        when(packageRepository.findById(1)).thenReturn(Optional.of(packageEntity));

        PackageDTO foundPackage = packageService.findById(1);

        assertEquals(packageDTO, foundPackage);
        verify(packageRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        when(packageRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> packageService.findById(1));

        assertEquals("Package not found with id: 1", exception.getMessage());
        verify(packageRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteById() {
        doNothing().when(packageRepository).deleteById(1);

        packageService.deleteById(1);

        verify(packageRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindAllPackages() {
        PackageDTO package1 = new PackageDTO();
        package1.setPackage_id(1);
        package1.setPrice(500);

        PackageDTO package2 = new PackageDTO();
        package2.setPackage_id(2);
        package2.setPrice(800);

        PackageEntity entity1 = DataMapper.packageDTOToEntity(package1);
        PackageEntity entity2 = DataMapper.packageDTOToEntity(package2);

        List<PackageEntity> entities = Arrays.asList(entity1, entity2);

        when(packageRepository.findAll()).thenReturn(entities);

        List<PackageDTO> foundPackages = packageService.findAll();

        assertEquals(2, foundPackages.size());
        assertEquals(package1.getPackage_id(), foundPackages.get(0).getPackage_id());
        assertEquals(package2.getPackage_id(), foundPackages.get(1).getPackage_id());

        verify(packageRepository, times(1)).findAll();
    }
}
