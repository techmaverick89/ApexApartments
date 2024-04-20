package com.dong.controllers;

import com.dong.pojo.Customer;
import com.dong.pojo.Service;
import com.dong.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ApiServiceController {
    @Autowired
    private ServiceService Ser;
    @DeleteMapping("/service/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        this.Ser.deleteSer(id);
    }
    @RequestMapping("/service/")
    @CrossOrigin
    public ResponseEntity<List<Service>> list() {
        return new ResponseEntity<>(this.Ser.getServices(), HttpStatus.OK);
    }
}
