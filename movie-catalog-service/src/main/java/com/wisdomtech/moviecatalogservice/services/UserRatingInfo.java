package com.wisdomtech.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wisdomtech.moviecatalogservice.models.Rating;
import com.wisdomtech.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(
            fallbackMethod = "getFallbackCatalogIdem"
//            Additional parameter of hystrix
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
//            }
    )
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://rattings-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackCatalogIdem(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(
                new Rating("0", 0)
        ));
        return userRating;
    }
}
