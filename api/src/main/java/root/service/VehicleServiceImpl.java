package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Alert;
import root.entity.Reading;
import root.entity.Tires;
import root.entity.Vehicle;
import root.repository.VehicleRepository;
import root.service.VehicleService;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Transactional
    public List<Vehicle> loadVehicles(List<Vehicle> vehicles) {
        return repository.loadVehicles(vehicles);
    }


}
