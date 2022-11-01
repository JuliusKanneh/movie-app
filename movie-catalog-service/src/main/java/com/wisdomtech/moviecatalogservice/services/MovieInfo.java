package com.wisdomtech.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wisdomtech.moviecatalogservice.models.CatalogItem;
import com.wisdomtech.moviecatalogservice.models.Movie;
import com.wisdomtech.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
//    Implementing the Bulkhead pattern for fault tolerance
    @HystrixCommand(
            fallbackMethod = "getFallbackUserRating",
            threadPoolKey = "movieInfo",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getOverview(), rating.getRating());
    }

    public CatalogItem getFallbackUserRating(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());
    }

}
