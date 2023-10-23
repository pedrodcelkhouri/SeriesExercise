package project.demo.series.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import project.demo.series.domain.Series;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Series> entity = new RestTemplate().getForEntity(
                "http://localhost:8080/series/{id}", Series.class, 20);
        log.info(entity);

        Series object = new RestTemplate().getForObject(
                "http://localhost:8080/series/{id}", Series.class, 20);
        log.info(object);

        Series[] series = new RestTemplate().getForObject(
                "http://localhost:8080/series/all", Series[].class);
        log.info(Arrays.toString(series));

        //@formatter:off
        ResponseEntity<List<Series>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/series/all", HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
        //formatter:on
        log.info(exchange.getBody());

//        Series houseOfDragon = Series.builder().name("House of Dragon").build();
//        Series houseOfDragonSaved = new RestTemplate().postForObject(
//              "http://localhost:8080/series/", houseOfDragon, Series.class);
//        log.info("Saved Series '{}'", houseOfDragonSaved);

        Series suits = Series.builder().name("Suits").build();
        ResponseEntity<Series> suitsSaved = new RestTemplate().exchange(
                "http://localhost:8080/series", HttpMethod.POST, new HttpEntity<>(suits, httpJsonHeaders()), Series.class);
        log.info("Saved Series '{}'", suitsSaved);

        Series seriesToBeUpdated = suitsSaved.getBody();
        seriesToBeUpdated.setName("Suits 2");

        ResponseEntity<Void> suitsUpdated = new RestTemplate().exchange(
                "http://localhost:8080/series", HttpMethod.PUT, new HttpEntity<>(seriesToBeUpdated, httpJsonHeaders()), Void.class);
        log.info(suitsUpdated);

        ResponseEntity<Void> suitsDeleted = new RestTemplate().exchange(
                "http://localhost:8080/series/{id}", HttpMethod.DELETE, null, Void.class, seriesToBeUpdated.getId());
        log.info(suitsDeleted);

    }

    private static HttpHeaders httpJsonHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
