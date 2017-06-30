package root.repository;


import root.entity.Alert;
import root.entity.Reading;

import java.util.List;

public interface ReadingRepository {
    public Reading ingestReading(Reading reading);
    public Alert storeAlert(Alert alert);

    public List<Reading> findAll();
}
