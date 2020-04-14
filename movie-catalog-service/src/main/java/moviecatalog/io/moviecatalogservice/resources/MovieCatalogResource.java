package moviecatalog.io.moviecatalogservice.resources;

import moviecatalog.io.moviecatalogservice.models.CatalogItem;
import moviecatalog.io.moviecatalogservice.models.Movie;
import moviecatalog.io.moviecatalogservice.models.Rating;
import moviecatalog.io.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableEurekaClient
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired // consumer
    private RestTemplate restTemplate;  //spring searches and maps the bean declared by @bean for rest template i.e dependency injection
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)  {
        //get ratings for movieId from ratings service
        // get movie desc from movie info service
        // combine all three together


        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);// response from rating api but localhost is now replaced with the corresponding eureka client name
        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(),Movie.class);   //important
           /* Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/" + rating.getMovieId()).retrieve().bodyToMono(Movie.class).block();*/
            return new CatalogItem(movie.getName(),"Test",rating.getRating());
        }).collect(Collectors.toList());



       /* return Collections.singletonList(
                new CatalogItem("Transformers","Test",4)
        );*/

    }
}
