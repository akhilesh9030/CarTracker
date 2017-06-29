package root.repository;


import org.springframework.stereotype.Repository;
import root.entity.Alert;
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



    public Vehicle findByVin(String vin) {
        return em.find(Vehicle.class, vin);
    }


}
