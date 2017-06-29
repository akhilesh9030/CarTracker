package root.repository;


import root.entity.Alert;
import root.entity.Reading;
import root.entity.Vehicle;

import java.util.List;

public interface VehicleRepository {
    public List<Vehicle> loadVehicles(List<Vehicle> vehicles);
    public Vehicle findByVin(String vin);

}
