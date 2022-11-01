package com.wisdomtech.moviecatalogservice.resources;

import com.wisdomtech.moviecatalogservice.models.CatalogItem;
import com.wisdomtech.moviecatalogservice.models.UserRating;
import com.wisdomtech.moviecatalogservice.services.MovieInfo;
import com.wisdomtech.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getMovieCatalog(@PathVariable("userId") String userId){

        UserRating userRating = userRatingInfo.getUserRating(userId);

        return userRating.getUserRating().stream()
                .map(rating -> movieInfo.getCatalogItem(rating))
                .collect(Collectors.toList());

    }


//    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
//        return Arrays.asList(new CatalogItem("No Item", "", 0));
//    }
}
