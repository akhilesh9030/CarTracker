package root.controller;


import org.springframework.web.bind.annotation.*;
import root.entity.Reading;
import root.entity.Vehicle;
import root.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    VehicleService service;

    @RequestMapping(method = RequestMethod.PUT, value = "vehicles", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin(origins = "http://mocker.egen.io", maxAge = 3600)
    public List<Vehicle> loadVehicles(@RequestBody List<Vehicle> vehicles){
        return service.loadVehicles(vehicles);
    }


}
