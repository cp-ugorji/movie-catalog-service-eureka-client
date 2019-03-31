/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technologies.stealth.controller;

import com.technologies.stealth.models.CatalogItem;
import com.technologies.stealth.models.Movie;
import com.technologies.stealth.models.Rating;
import com.technologies.stealth.models.UserRating;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 *
 * @author Chux
 */
@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
    
    @Autowired
    private RestTemplate restTemplate;
    
//    @Autowired
//    private WebClient.Builder webClientBuilder;
    
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
    {
        //get all movie rated ids the user has watched
        //UserRating ratings = restTemplate.getForObject("http://localhost:8081/ratingsdata/users/" + userId, UserRating.class);
        //since this is an eureka client, which has been load balanced on the rest template,
        //all we need to do is pass in the eureka service name of the endpoint instead of the server:port
        //so we are replacing localhost:8081 to ratings-data-service
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
        
        
        return ratings.getUserRatings().stream().map((rating) -> {
            //for each movie id, call movies info service and get movie details
            //replacing endpoint localhost:8082 with eureka endpoint for movie-info-service
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            //the single line above can be done using webclient builder as seen below
            //this does an asynchronous web client call
//            Movie movie = webClientBuilder.build()
//                    .get()//this shows web client is performing a get operation. this can be replaced with post or put depending on the operation
//                    .uri("http://localhost:8081/movies/" + rating.getMovieId())//this is the url the web client is calling
//                    .retrieve()//this is telling the web client to get everything returned and map the body to the class Movie
//                    .bodyToMono(Movie.class)
//                    .block();//since this is an asynchronous call, and we are suppose to return a list of movies from the method definition
            //we have to wait for the execution to finish and return the Movie thus we use the block()s to wait
            
            //put them all together
            return new CatalogItem(movie.getName(), "", rating.getRating());
            
        }).collect(Collectors.toList());
        
        //put them all together
        //return Collections.singletonList(new CatalogItem("Shooter", "Story of an ex-marin forced to protect his family", 5));
    }
}
