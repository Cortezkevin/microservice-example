package com.microservice.userservice.feignClients;

import com.microservice.userservice.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
 // FeignClient es un cliente REST declarativo en la aplicación web Spring Boot. El cliente REST declarativo significa que solo proporciona la especificación del cliente como una interfaz y Spring Boot se encarga de la implementación por usted. FeignClient se usa para consumir puntos finales de API RESTFul expuestos por terceros o microservicios.
//  un cliente REST declarativo
@FeignClient(
    name = "bike-service",
    url = "http://localhost:8003",
    path = "/bike"
)
public interface BikeFeignClient {

    @PostMapping
    Bike save(@RequestBody Bike b);

    @GetMapping("/byUser/{userId}")
    List<Bike> getBikes(@PathVariable("userId") int userId);

}
