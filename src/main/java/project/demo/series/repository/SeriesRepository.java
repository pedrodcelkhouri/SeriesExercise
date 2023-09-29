package project.demo.series.repository;

import project.demo.series.domain.Series;

import java.util.List;

public interface SeriesRepository {
    List<Series> listAll();
}
