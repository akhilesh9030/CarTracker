package root.service;

import root.entity.Reading;
import root.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    public List<Vehicle> loadVehicles(List<Vehicle> vehicles);
    public Reading ingestReading(Reading reading);
}
