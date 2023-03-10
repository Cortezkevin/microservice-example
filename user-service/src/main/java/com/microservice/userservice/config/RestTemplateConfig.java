package com.microservice.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration indica que el contenedor Spring IoC puede usar la clase como fuente de definiciones de beans .
@Configuration
public class RestTemplateConfig {

    //registramos restemplate en el ioc container
    //ioc container -> El contenedor IoC es un marco para implementar la inyección de dependencia automatizada . Contiene la creación de objetos para las formas más largas de usar e inyecta dependencias dentro de la clase.
    //@Bean le dice a Spring que un método anotado con @Bean devolverá un objeto que debe registrarse como un bean en el contexto de la aplicación Spring.
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
