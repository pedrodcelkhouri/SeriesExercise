package project.demo.series.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    }
}
