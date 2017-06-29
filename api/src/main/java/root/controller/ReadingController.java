package root.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import root.entity.Reading;
import root.service.ReadingService;

@RestController
public class ReadingController {

    @Autowired
    private ReadingService service;

    @RequestMapping(method = RequestMethod.POST, value = "readings", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "http://mocker.egen.io", maxAge = 3600)
    public Reading ingestReading(@RequestBody Reading reading){
        return service.ingestReading(reading);
    }
}
