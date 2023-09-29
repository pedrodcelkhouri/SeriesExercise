package project.demo.series.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.series.domain.Series;
import project.demo.series.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@Log4j2
@RequiredArgsConstructor
public class SeriesController {
    private final DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Series> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Series("Friends"), new Series("HIMYM"));
    }
}
