package com.microservice.userservice.feignClients;

import com.microservice.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "car-service",
    path = "/car"
)
public interface CarFeignClient {

    @PostMapping
    Car save(@RequestBody Car c);

    @GetMapping("/byUser/{userId}")
    List<Car> getCars(@PathVariable("userId") int userId);
}
