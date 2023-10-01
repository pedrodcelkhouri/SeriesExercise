package project.demo.series.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<Series> save(@RequestBody Series series){
        return new ResponseEntity<>(seriesService.save(series), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        seriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
