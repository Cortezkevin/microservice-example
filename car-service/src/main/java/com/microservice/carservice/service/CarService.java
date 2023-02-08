package com.microservice.carservice.service;

import com.microservice.carservice.entity.Car;
import com.microservice.carservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;

    public List<Car> getAll(){
        return repository.findAll();
    }

    public Car getById(int id){
        return repository.findById(id).orElse(null);
    }

    public Car create(Car c){
        return repository.save(c);
    }

    public List<Car> getByUserId(int userId){
        return repository.findByUserId(userId);
    }
}
