package moviecatalog.io.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import moviecatalog.io.moviecatalogservice.models.CatalogItem;
import moviecatalog.io.moviecatalogservice.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(moviecatalog.io.moviecatalogservice.models.Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }
    public CatalogItem getFallbackCatalogItem(moviecatalog.io.moviecatalogservice.models.Rating rating) {
        CatalogItem catalogItem = new CatalogItem("No movie","",rating.getRating());
        return catalogItem;
    }
}
