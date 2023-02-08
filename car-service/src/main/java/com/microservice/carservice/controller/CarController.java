package com.microservice.carservice.controller;

import com.microservice.carservice.entity.Car;
import com.microservice.carservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService service;

    @GetMapping
    public ResponseEntity<List<Car>> getAll(){
        List<Car> carList = service.getAll();
        if( carList.isEmpty() ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getById(@PathVariable("id") int id){
        Car car = service.getById(id);
        if( car == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int userId){
        List<Car> carList = service.getByUserId(userId);
        return ResponseEntity.ok(carList);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car c){
        return ResponseEntity.ok(service.create(c));
    }
}
