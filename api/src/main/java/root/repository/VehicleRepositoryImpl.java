package root.repository;


import org.springframework.stereotype.Repository;
import root.entity.Reading;
import root.entity.Tires;
import root.entity.Vehicle;
import root.service.VehicleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Vehicle> loadVehicles(List<Vehicle> vehicles) {
        for(Vehicle vehicle: vehicles){
            if(findByVin(vehicle.getVin()) == null){
                em.persist(vehicle);
            }
            else{
                em.merge(vehicle);
            }
        }
        return vehicles;
    }

    public Reading ingestReading(Reading reading) {
        
        Tires tires = new Tires();

        tires.setFrontLeft(reading.getTires().getFrontLeft());
        tires.setFrontRight(reading.getTires().getFrontRight());
        tires.setRearLeft(reading.getTires().getRearLeft());
        tires.setRearRight(reading.getTires().getRearRight());

        em.persist(tires);
        reading.setTires(tires);
        em.persist(reading);

        return reading;
    }

    public Vehicle findByVin(String vin) {
        return em.find(Vehicle.class, vin);
    }
}
