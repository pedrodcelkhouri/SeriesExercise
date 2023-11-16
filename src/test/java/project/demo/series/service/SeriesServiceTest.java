package project.demo.series.service;

import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.demo.series.domain.Series;
import project.demo.series.exception.BadRequestException;
import project.demo.series.repository.SeriesRepository;
import project.demo.series.util.SeriesCreator;
import project.demo.series.util.SeriesPostRequestBodyCreator;
import project.demo.series.util.SeriesPutRequestBodyCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class SeriesServiceTest {
    @InjectMocks
    private SeriesService seriesService;
    @Mock
    private SeriesRepository seriesRepositoryMock;
    @BeforeEach
    void setUp(){
        PageImpl<Series> seriesPage = new PageImpl<>(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(seriesPage);
        BDDMockito.when(seriesRepositoryMock.findAll()).thenReturn(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesRepositoryMock.save(ArgumentMatchers.any(Series.class)))
                .thenReturn(SeriesCreator.createValidSeries());
        BDDMockito.doNothing().when(seriesRepositoryMock).delete(ArgumentMatchers.any(Series.class));
    }
    @Test
    @DisplayName("listAll returns list of series inside page object when successful")
    void listAll_ReturnsListOfSeriesInsidePageObject_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        Page<Series> seriesPage = seriesService.listAll(PageRequest.of(1,1));
        Assertions.assertThat(seriesPage).isNotNull();
        Assertions.assertThat(seriesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(seriesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("listAllNonPageable returns list of series when successful")
    void listAllNonPageable_ReturnsListOfSeries_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        List<Series> series = seriesService.listAllNonPageable();
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns series when successful")
    void findByIdOrThrowBadRequestException_ReturnsSeries_WhenSuccessful(){
        Long expectedId = SeriesCreator.createValidSeries().getId();
        Series series = seriesService.findByIdOrThrowBadRequestException(1);
        Assertions.assertThat(series).isNotNull();
        Assertions.assertThat(series.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when series is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenSeriesIsNotFound(){
        BDDMockito.when(seriesRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class).isThrownBy(() -> seriesService.findByIdOrThrowBadRequestException(1));
    }
    @Test
    @DisplayName("findByName returns a list of series when successful")
    void findByName_ReturnsListOfSeries_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        List<Series> series = seriesService.findByName("series");
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty list of series when series is not found")
    void findByName_ReturnsEmptyListOfSeries_WhenSeriesIsNotFound(){
        BDDMockito.when(seriesRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        List<Series> series = seriesService.findByName("series");
        Assertions.assertThat(series).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns series when successful")
    void save_ReturnsSeries_WhenSuccessful(){
        Series series = seriesService.save(SeriesPostRequestBodyCreator.createSeriesPostRequestBody());
        Assertions.assertThat(series).isNotNull().isEqualTo(SeriesCreator.createValidSeries());
    }
    @Test
    @DisplayName("replace updates series when successful")
    void replace_UpdatesSeries_WhenSuccessful(){
        Assertions.assertThatCode(() -> SeriesPutRequestBodyCreator.createSeriesPutRequestBody()).doesNotThrowAnyException();
    }
    @Test
    @DisplayName("delete removes series when successful")
    void delete_RemovesSeries_WhenSuccessful(){
        Assertions.assertThatCode(() -> seriesService.delete(1)).doesNotThrowAnyException();
    }
}