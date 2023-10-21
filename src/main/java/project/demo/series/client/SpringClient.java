package project.demo.series.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import project.demo.series.domain.Series;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Series> entity = new RestTemplate().getForEntity(
                "http://localhost:8080/series/{id}", Series.class, 20);
        log.info(entity);

        Series object = new RestTemplate().getForObject("http://localhost:8080/series/{id}", Series.class, 20);
        log.info(object);
    }
}
