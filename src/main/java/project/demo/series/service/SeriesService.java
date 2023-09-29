package project.demo.series.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.demo.series.domain.Series;

import java.util.List;

@Service
public class SeriesService {
    private List<Series> seriesList = List.of(new Series(1L, "Friends"), new Series(2L,"HIMYM"));
    public List<Series> listAll(){
        return seriesList;
    }
    public Series findById(long id){
        return seriesList.stream().filter(series -> series.getId().equals(id)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series not found"));
    }
}
