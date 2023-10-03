package project.demo.series.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.series.domain.Series;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByName(String name);
}
