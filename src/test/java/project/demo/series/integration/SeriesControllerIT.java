package project.demo.series.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import project.demo.series.domain.Series;
import project.demo.series.repository.SeriesRepository;
import project.demo.series.util.SeriesCreator;
import wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class SeriesControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private SeriesRepository seriesRepository;
    @Test
    @DisplayName("list returns list of series inside page object when successful")
    void list_ReturnsListOfSeriesInsidePageObject_WhenSuccessful(){
        Series savedSeries = seriesRepository.save(SeriesCreator.createValidSeries());
        String expectedName = savedSeries.getName();
        PageableResponse<Series> seriesPage = testRestTemplate.exchange("/series",
                HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Series>>(){}).getBody();
        Assertions.assertThat(seriesPage).isNotNull();
        Assertions.assertThat(seriesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(seriesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }
}
