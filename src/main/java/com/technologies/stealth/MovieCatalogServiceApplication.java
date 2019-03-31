package com.technologies.stealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieCatalogServiceApplication {

    @Bean //this method creats a web client that can be used anywhere in this project to get an instance of the web client
    //because of it's @Bean annotation
    @LoadBalanced //this load balances request to eureka server and tells it the service to call based on request
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
    
//    @Bean //this is the new web client to be used by springboot because they will be deprecating RestTemplate soon
//    public WebClient.Builder getWebClientBuilder()
//    {
//        return WebClient.builder();
//    }
    
    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogServiceApplication.class, args);
    }

}
