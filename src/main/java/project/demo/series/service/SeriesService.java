package project.demo.series.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.demo.series.domain.Series;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SeriesService {
    private static List<Series> seriesList;

    static {
        seriesList = new ArrayList<>(List.of(new Series(1L, "Friends"), new Series(2L, "HIMYM")));
    }

    public List<Series> listAll() {
        return seriesList;
    }

    public Series findById(long id) {
        return seriesList.stream().filter(series -> series.getId().equals(id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series not found"));
    }

    public Series save(Series series) {
        series.setId(ThreadLocalRandom.current().nextLong(3, 100000));
        seriesList.add(series);
        return series;
    }

    public void delete(long id) {
        seriesList.remove(findById(id));
    }

    public void replace(Series series) {
        delete(series.getId());
        seriesList.add(series);
    }
}
