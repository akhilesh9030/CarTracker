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

    @Transactional
    public Reading ingestReading(Reading reading) {
        Vehicle vehicle = repository.findByVin(reading.getVin());
        Tires tires = reading.getTires();

        Alert alert = null;

        if(vehicle == null){
           System.out.println("Reading for unknown vehicle not stored in database");
        }
        else{
            if(reading.getEngineRpm() > vehicle.getRedLineRpm()){
                alert = new Alert();
                alert.setPriority("HIGH");
                alert.setRule("engineRpm > readlineRpm");
                alert.setVin(reading.getVin());
                repository.storeAlert(alert);
            }
            if(reading.getFuelVolume() < 0.1*vehicle.getMaxFuelVolume()){
                alert = new Alert();
                alert.setPriority("MEDIUM");
                alert.setRule("fuelVolume < 10% of maxFuelVolume");
                alert.setVin(reading.getVin());
                repository.storeAlert(alert);
            }
            if(!(tires.getFrontLeft() >= 32 && tires.getFrontLeft() <= 36) ||
                    !(tires.getFrontRight() >= 32 && tires.getFrontRight() <= 36) ||
                        !(tires.getRearLeft() >= 32 && tires.getRearLeft() <= 36)   ||
                            !(tires.getRearRight() >= 32 && tires.getRearRight() <= 36)){

                alert = new Alert();
                alert.setPriority("LOW");
                alert.setRule("tire pressure of any tire < 32psi || > 36psi");
                alert.setVin(reading.getVin());
                repository.storeAlert(alert);

            }
            if(reading.isEngineCoolantLow() == true || reading.isCheckEngineLightOn() == true){
                alert = new Alert();
                alert.setPriority("LOW");
                alert.setRule("engineCoolantLow = true || checkEngineLightOn = true");
                alert.setVin(reading.getVin());
                repository.storeAlert(alert);
            }
        }

        return repository.ingestReading(reading);
    }
}
