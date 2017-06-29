package root.repository;


import root.entity.Alert;
import root.entity.Reading;

public interface ReadingRepository {
    public Reading ingestReading(Reading reading);
    public Alert storeAlert(Alert alert);
}
