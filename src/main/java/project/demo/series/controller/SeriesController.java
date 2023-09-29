package project.demo.series.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.series.domain.Series;
import project.demo.series.service.SeriesService;
import project.demo.series.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("series")
@Log4j2
@RequiredArgsConstructor
public class SeriesController {
    private final DateUtil dateUtil;
    private final SeriesService seriesService;

    @GetMapping
    public ResponseEntity<List<Series>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(seriesService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Series> findById(@PathVariable long id){
        return ResponseEntity.ok(seriesService.findById(id));
    }
}
