package project.demo.series.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.demo.series.domain.Series;

@DataJpaTest
@DisplayName("Tests for Series Repository")
class SeriesRepositoryTest {
    @Autowired
    private SeriesRepository seriesRepository;
    @Test
    @DisplayName("Save creates series when successful")
    void save_PersistSeries_WhenSuccessful(){
        Series seriesToBeSaved = createSeries();
        Series seriesSaved = this.seriesRepository.save(seriesToBeSaved);
        Assertions.assertThat(seriesSaved).isNotNull();
        Assertions.assertThat(seriesSaved.getId()).isNotNull();
        Assertions.assertThat(seriesSaved.getName()).isEqualTo(seriesToBeSaved.getName());
    }

    private Series createSeries(){
        return Series.builder().name("Vikings").build();
    }
}