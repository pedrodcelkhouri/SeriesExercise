package project.demo.series.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.demo.series.domain.Series;
import project.demo.series.dto_requests.SeriesPostRequestBody;
import project.demo.series.dto_requests.SeriesPutRequestBody;
import project.demo.series.service.SeriesService;
import project.demo.series.util.SeriesCreator;
import project.demo.series.util.SeriesPostRequestBodyCreator;
import project.demo.series.util.SeriesPutRequestBodyCreator;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class SeriesControllerTest {
    @InjectMocks
    private SeriesController seriesController;
    @Mock
    private SeriesService seriesServiceMock;
    @BeforeEach
    void setUp(){
        PageImpl<Series> seriesPage = new PageImpl<>(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesServiceMock.listAll(ArgumentMatchers.any())).thenReturn(seriesPage);
        BDDMockito.when(seriesServiceMock.listAllNonPageable()).thenReturn(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(SeriesCreator.createValidSeries());
        BDDMockito.when(seriesServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(SeriesCreator.createValidSeries()));
        BDDMockito.when(seriesServiceMock.save(ArgumentMatchers.any(SeriesPostRequestBody.class)))
                .thenReturn(SeriesCreator.createValidSeries());
        BDDMockito.doNothing().when(seriesServiceMock).replace(ArgumentMatchers.any(SeriesPutRequestBody.class));
        BDDMockito.doNothing().when(seriesServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("list returns list of series inside page object when successful")
    void list_ReturnsListOfSeriesInsidePageObject_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        Page<Series> seriesPage = seriesController.list(null).getBody();
        Assertions.assertThat(seriesPage).isNotNull();
        Assertions.assertThat(seriesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(seriesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("listAll returns list of series when successful")
    void listAll_ReturnsListOfSeries_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        List<Series> series = seriesController.listAll().getBody();
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findById returns series when successful")
    void findById_ReturnsSeries_WhenSuccessful(){
        Long expectedId = SeriesCreator.createValidSeries().getId();
        Series series = seriesController.findById(1).getBody();
        Assertions.assertThat(series).isNotNull();
        Assertions.assertThat(series.getId()).isNotNull().isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findByName returns a list of series when successful")
    void findByName_ReturnsListOfSeries_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        List<Series> series = seriesController.findByName("series").getBody();
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty list of series when series is not found")
    void findByName_ReturnsEmptyListOfSeries_WhenSeriesIsNotFound(){
        BDDMockito.when(seriesServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        List<Series> series = seriesController.findByName("series").getBody();
        Assertions.assertThat(series).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns series when successful")
    void save_ReturnsSeries_WhenSuccessful(){
        Series series = seriesController.save(SeriesPostRequestBodyCreator.createSeriesPostRequestBody()).getBody();
        Assertions.assertThat(series).isNotNull().isEqualTo(SeriesCreator.createValidSeries());
    }
    @Test
    @DisplayName("replace updates series when successful")
    void replace_UpdatesSeries_WhenSuccessful(){
        Assertions.assertThatCode(() -> SeriesPutRequestBodyCreator.createSeriesPutRequestBody()).doesNotThrowAnyException();
        ResponseEntity<Void> entity = seriesController.replace(SeriesPutRequestBodyCreator.createSeriesPutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("delete removes series when successful")
    void delete_RemovesSeries_WhenSuccessful(){
        Assertions.assertThatCode(() -> seriesController.delete(1)).doesNotThrowAnyException();
        ResponseEntity<Void> entity = seriesController.delete(1);
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}