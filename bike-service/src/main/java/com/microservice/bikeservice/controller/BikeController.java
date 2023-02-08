package com.microservice.bikeservice.controller;

import com.microservice.bikeservice.entity.Bike;
import com.microservice.bikeservice.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
@RequiredArgsConstructor
public class BikeController {

    private final BikeService service;

    @GetMapping
    public ResponseEntity<List<Bike>> getAll(){
        List<Bike> bikeList = service.getAll();
        if( bikeList.isEmpty() ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bikeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bike> getById(@PathVariable("id") int id){
        Bike bike = service.getById(id);
        if( bike == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId){
        List<Bike> bikeList = service.getByUserId(userId);
        return ResponseEntity.ok(bikeList);
    }

    @PostMapping
    public ResponseEntity<Bike> save(@RequestBody Bike b){
        return ResponseEntity.ok(service.create(b));
    }
}
