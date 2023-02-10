package com.microservice.userservice.service;

import com.microservice.userservice.entity.User;
import com.microservice.userservice.feignClients.BikeFeignClient;
import com.microservice.userservice.feignClients.CarFeignClient;
import com.microservice.userservice.model.Bike;
import com.microservice.userservice.model.Car;
import com.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service anota clases en la capa de servicio.
@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    private final UserRepository repository;

    private final CarFeignClient carFeignClient;

    private final BikeFeignClient bikeFeignClient;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(int id){
        return repository.findById(id).orElse(null);
    }

    public User create(User u){
        return repository.save(u);
    }

    public List getCars(int userId){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List> cars = restTemplate.exchange(
                "http://car-service/car/byUser/" + userId,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                List.class);
        return cars.getBody();
    }

    public List getBikes(int userId){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List> bikes = restTemplate.exchange(
                "http://bike-service/bike/byUser/" + userId,
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                List.class);
        return bikes.getBody();
    }

    public Car createCar(int userId, Car c){
        c.setUserId(userId);
        return carFeignClient.save(c);
    }

    public Bike createBike(int userId, Bike b){
        b.setUserId(userId);
        return bikeFeignClient.save(b);
    }

    public Map<String, Object> getUserAndVehicles(int userId){
        Map<String, Object> result = new HashMap<>();

        User user = repository.findById(userId).orElse(null);
        if( user == null ){
            result.put("message", "User not exists with id: " + userId);
            return result;
        }

        result.put("user", user);

        List<Car> cars = carFeignClient.getCars(userId);
        if( cars.isEmpty() ){
            result.put("cars", "This user not have cars");
        }else {
            result.put("cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if( bikes.isEmpty() ){
            result.put("bikes", "This user not have bikes");
        }else {
            result.put("bikes", bikes);
        }

        return result;
    }
}
