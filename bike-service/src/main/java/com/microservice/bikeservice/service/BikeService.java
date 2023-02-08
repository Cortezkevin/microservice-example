package com.microservice.bikeservice.service;

import com.microservice.bikeservice.entity.Bike;
import com.microservice.bikeservice.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository repository;

    public List<Bike> getAll(){
        return repository.findAll();
    }

    public Bike getById(int id){
        return repository.findById(id).orElse(null);
    }

    public Bike create(Bike b){
        return repository.save(b);
    }

    public List<Bike> getByUserId(int userId){
        return repository.findByUserId(userId);
    }
}
