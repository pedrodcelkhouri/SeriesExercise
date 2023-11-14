package project.demo.series.controller;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.demo.series.domain.Series;
import project.demo.series.service.SeriesService;
import project.demo.series.util.SeriesCreator;

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
    }
    @Test
    @DisplayName("List returns list of series inside page object when successful")
    void list_ReturnsListOfSeriesInsidePageObject_WhenSuccessful(){
        String expectedName = SeriesCreator.createValidSeries().getName();
        Page<Series> seriesPage = seriesController.list(null).getBody();
        org.assertj.core.api.Assertions.assertThat(seriesPage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(seriesPage.toList()).isNotEmpty().hasSize(1);
        org.assertj.core.api.Assertions.assertThat(seriesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }
}