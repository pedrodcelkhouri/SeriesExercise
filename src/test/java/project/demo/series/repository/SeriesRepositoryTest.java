package project.demo.series.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.demo.series.domain.Series;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Series Repository")
@Log4j2
class SeriesRepositoryTest {
    @Autowired
    private SeriesRepository seriesRepository;
    @Test
    @DisplayName("Save persists series when successful")
    void save_PersistSeries_WhenSuccessful(){
        Series seriesToBeSaved = createSeries();
        Series seriesSaved = this.seriesRepository.save(seriesToBeSaved);
        Assertions.assertThat(seriesSaved).isNotNull();
        Assertions.assertThat(seriesSaved.getId()).isNotNull();
        Assertions.assertThat(seriesSaved.getName()).isEqualTo(seriesToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates series when successful")
    void save_UpdatesSeries_WhenSuccessful(){
        Series seriesToBeSaved = createSeries();
        Series seriesSaved = this.seriesRepository.save(seriesToBeSaved);
        seriesSaved.setName("HIMYF");
        Series seriesUpdated = this.seriesRepository.save(seriesSaved);
//        log.info(seriesUpdated.getName());
        Assertions.assertThat(seriesUpdated).isNotNull();
        Assertions.assertThat(seriesUpdated.getId()).isNotNull();
        Assertions.assertThat(seriesUpdated.getName()).isEqualTo(seriesSaved.getName());
    }

    @Test
    @DisplayName("Delete removes series when successful")
    void delete_RemovesSeries_WhenSuccessful(){
        Series seriesToBeSaved = createSeries();
        Series seriesSaved = this.seriesRepository.save(seriesToBeSaved);
        this.seriesRepository.delete(seriesSaved);
        Optional<Series> seriesOptional = this.seriesRepository.findById(seriesSaved.getId());
        Assertions.assertThat(seriesOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of series when successful")
    void findByName_ReturnsListOfSeries_WhenSuccessful(){
        Series seriesToBeSaved = createSeries();
        Series seriesSaved = this.seriesRepository.save(seriesToBeSaved);
        String name = seriesSaved.getName();
        List<Series> series = this.seriesRepository.findByName(name);
        Assertions.assertThat(series).isNotEmpty();
        Assertions.assertThat(series).contains(seriesSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list of series when no series is found")
    void findByName_ReturnsEmptyList_WhenSeriesIsNotFound(){
        List<Series> series = this.seriesRepository.findByName("nameExample");
        Assertions.assertThat(series).isEmpty();
    }

    private Series createSeries(){
        return Series.builder().name("Vikings").build();
    }
}