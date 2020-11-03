package moviecatalog.io.ratingsdataservice.resources;

import moviecatalog.io.ratingsdataservice.models.Rating;
import moviecatalog.io.ratingsdataservice.models.UserRating;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@EnableEurekaClient
@RequestMapping("/ratingsdata")
public class RatingsDataSource {
    @RequestMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }
    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

    }

}
