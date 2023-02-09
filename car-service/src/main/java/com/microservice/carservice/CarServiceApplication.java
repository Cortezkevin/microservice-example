package com.microservice.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CarServiceApplication {

	//circuitBreaker: corta circuitos, impide que un microservicio caido o con algun error, pase a mayores y afecte a los demas
	//loadBalancer: distribuye automáticamente el tráfico entrante entre varios destinos, en este caso en microservicios con multiples
	//gateway: enrutador que funciona como un punto de parada para los datos en su camino hacia otras redes, en este caso sirve para tener un unico punto de acceso con un puerto especifico
	// y asi no preocuparse por los puertos aleatorios que puedan generarse

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

}
