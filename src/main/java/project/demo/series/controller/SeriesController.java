package project.demo.series.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.series.domain.Series;
import project.demo.series.dto_requests.SeriesPostRequestBody;
import project.demo.series.dto_requests.SeriesPutRequestBody;
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
    public ResponseEntity<Page<Series>> list(Pageable pageable){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(seriesService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Series> findById(@PathVariable long id){
        return ResponseEntity.ok(seriesService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Series>> findByName(@RequestParam String name){
        return ResponseEntity.ok(seriesService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Series> save(@RequestBody @Valid SeriesPostRequestBody seriesPostRequestBody){
        return new ResponseEntity<>(seriesService.save(seriesPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        seriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody SeriesPutRequestBody seriesPutRequestBody){
        seriesService.replace(seriesPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
