package com.microservice.userservice.controller;

import com.microservice.userservice.entity.User;
import com.microservice.userservice.model.Bike;
import com.microservice.userservice.model.Car;
import com.microservice.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> userList = service.getAll();
        if( userList.isEmpty() ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id){
        User user = service.getById(id);
        if( user == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> getById(@RequestBody User u){
        return ResponseEntity.ok(service.create(u));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId){
        User user = service.getById(userId);
        if( user == null){
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = service.getCars( userId );
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car c){
        if( service.getById( userId) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.createCar(userId, c));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId){
        User user = service.getById(userId);
        if( user == null){
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = service.getBikes( userId );
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallBackSaveBike")
    @PostMapping("/bike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike b){
        if( service.getById( userId) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.createBike(userId,b));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/getVehicles/{userId}")
    public ResponseEntity<Map<String, Object>> getVehicles(@PathVariable("userId") int userId){
        return ResponseEntity.ok(service.getUserAndVehicles( userId ));
    }

    private ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int userId, RuntimeException e){
        return new ResponseEntity("The user "+ userId +" have cars in the taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int userId, @RequestBody Car c, RuntimeException e){
        return new ResponseEntity("The user "+ userId +" not have money for the car", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("userId") int userId, RuntimeException e){
        return new ResponseEntity("The user "+ userId +" have bikes in the taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallBackSaveBike(@PathVariable("userId") int userId, @RequestBody Bike b, RuntimeException e){
        return new ResponseEntity("The user "+ userId +" not have money for the bike", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e){
        return new ResponseEntity("The user "+ userId +" have vehicles in the taller", HttpStatus.OK);
    }
}
