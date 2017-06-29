package root.repository;


import org.springframework.stereotype.Repository;
import root.entity.Alert;
import root.entity.Reading;
import root.entity.Tires;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ReadingRepositoryImpl implements ReadingRepository{

    @PersistenceContext
    private EntityManager em;

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

    public Alert storeAlert(Alert alert) {
        em.persist(alert);
        return alert;
    }
}
