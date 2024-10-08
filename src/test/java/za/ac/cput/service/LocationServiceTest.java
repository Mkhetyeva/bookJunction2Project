package za.ac.cput.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.cput.domain.Location;
import za.ac.cput.repository.LocationRepository;
import za.ac.cput.service.LocationService;

import java.util.Optional;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveLocation_ValidLocation_Success() {
        // Arrange
        Location location = new Location.Builder().setAddress("123 Main St").build();
        when(locationRepository.save(any(Location.class))).thenReturn(location);


        Location savedLocation = locationService.saveLocation(location);


        assertNotNull(savedLocation, "Saved location should not be null.");
        assertEquals(location.getAddress(), savedLocation.getAddress(), "Addresses should match.");
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void saveLocation_NullLocation_ThrowsException() {

        assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(null);
        }, "Location cannot be null");
    }

    @Test
    void getLocationById_ValidId_ReturnsLocation() {

        Long locationId = 1L;
        Location location = new Location.Builder().setAddress("456 Elm St").build();
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));


        Location foundLocation = locationService.getLocationById(locationId);


        assertNotNull(foundLocation, "Location should not be null.");
        assertEquals(location.getAddress(), foundLocation.getAddress(), "Addresses should match.");
        verify(locationRepository, times(1)).findById(locationId);
    }

    @Test
    void getLocationById_InvalidId_ThrowsException() {

        Long locationId = 1L;
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> {
            locationService.getLocationById(locationId);
        }, "No location found with ID: " + locationId);
        verify(locationRepository, times(1)).findById(locationId);
    }

    @Test
    void deleteLocation_ValidId_Success() {

        Long locationId = 1L;
        when(locationRepository.existsById(locationId)).thenReturn(true);
        doNothing().when(locationRepository).deleteById(locationId);


        locationService.deleteLocation(locationId);


        verify(locationRepository, times(1)).deleteById(locationId);
    }

    @Test
    void deleteLocation_InvalidId_ThrowsException() {

        Long locationId = 1L;
        when(locationRepository.existsById(locationId)).thenReturn(false);


        assertThrows(NoSuchElementException.class, () -> {
            locationService.deleteLocation(locationId);
        }, "No location found with ID: " + locationId);
        verify(locationRepository, never()).deleteById(locationId);
    }
}
