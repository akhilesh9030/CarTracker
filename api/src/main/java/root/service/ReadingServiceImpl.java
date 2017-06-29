package root.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import root.entity.Alert;
import root.entity.Reading;
import root.entity.Tires;
import root.entity.Vehicle;
import root.exceptions.ResourceNotFoundException;
import root.repository.ReadingRepository;
import root.repository.VehicleRepository;

@Service
public class ReadingServiceImpl implements ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public Reading ingestReading(Reading reading) {
        Vehicle vehicle = vehicleRepository.findByVin(reading.getVin());
        Tires tires = reading.getTires();

        Alert alert = null;

        if(vehicle == null){
            throw new ResourceNotFoundException("Vehicle with vin id: " + reading.getVin() + " does not exist.");
        }
        else{
            if(reading.getEngineRpm() > vehicle.getRedLineRpm()){
                alert = new Alert();
                alert.setPriority("HIGH");
                alert.setRule("engineRpm > readlineRpm");
                alert.setVin(reading.getVin());
                readingRepository.storeAlert(alert);
            }
            if(reading.getFuelVolume() < 0.1*vehicle.getMaxFuelVolume()){
                alert = new Alert();
                alert.setPriority("MEDIUM");
                alert.setRule("fuelVolume < 10% of maxFuelVolume");
                alert.setVin(reading.getVin());
                readingRepository.storeAlert(alert);
            }
            if(!(tires.getFrontLeft() >= 32 && tires.getFrontLeft() <= 36) ||
                    !(tires.getFrontRight() >= 32 && tires.getFrontRight() <= 36) ||
                    !(tires.getRearLeft() >= 32 && tires.getRearLeft() <= 36)   ||
                    !(tires.getRearRight() >= 32 && tires.getRearRight() <= 36)){

                alert = new Alert();
                alert.setPriority("LOW");
                alert.setRule("tire pressure of any tire < 32psi || > 36psi");
                alert.setVin(reading.getVin());
                readingRepository.storeAlert(alert);

            }
            if(reading.isEngineCoolantLow() == true || reading.isCheckEngineLightOn() == true){
                alert = new Alert();
                alert.setPriority("LOW");
                alert.setRule("engineCoolantLow = true || checkEngineLightOn = true");
                alert.setVin(reading.getVin());
                readingRepository.storeAlert(alert);
            }
        }

        return readingRepository.ingestReading(reading);
    }
}
