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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import project.demo.series.domain.Series;
import project.demo.series.dto_requests.SeriesPostRequestBody;
import project.demo.series.repository.SeriesRepository;
import project.demo.series.util.SeriesCreator;
import project.demo.series.util.SeriesPostRequestBodyCreator;
import wrapper.PageableResponse;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        String expectedName = savedSeries.getName();
        PageableResponse<Series> seriesPage = testRestTemplate.exchange("/series",
                HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Series>>(){}).getBody();
        Assertions.assertThat(seriesPage).isNotNull();
        Assertions.assertThat(seriesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(seriesPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of series when successful")
    void listAll_ReturnsListOfSeries_WhenSuccessful(){
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        String expectedName = savedSeries.getName();
        List<Series> series = testRestTemplate.exchange("/series/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Series>>(){}).getBody();
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns series when successful")
    void findById_ReturnsSeries_WhenSuccessful(){
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        Long expectedId = savedSeries.getId();
        Series series = testRestTemplate.getForObject("/series/{id}", Series.class, expectedId);
        Assertions.assertThat(series).isNotNull();
        Assertions.assertThat(series.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns list a of series when successful")
    void findByName_ReturnsListOfSeries_WhenSuccessful() {
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        String expectedName = savedSeries.getName();
        String url = String.format("/series/find?name=%s", expectedName);
        List<Series> series = testRestTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Series>>() {}).getBody();
        Assertions.assertThat(series).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(series.get(0).getName()).isNotNull().isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of series when series is not found")
    void findByName_ReturnsEmptyListOfSeries_WhenSeriesIsNotFound(){
        List<Series> series = testRestTemplate.exchange("/series/find?name=%rbd",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Series>>() {}).getBody();
        Assertions.assertThat(series).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns series when successful")
    void save_ReturnsSeries_WhenSuccessful(){
        SeriesPostRequestBody seriesPostRequestBody = SeriesPostRequestBodyCreator.createSeriesPostRequestBody();
        ResponseEntity<Series> seriesResponseEntity = testRestTemplate.postForEntity("/series", seriesPostRequestBody, Series.class);
        Assertions.assertThat(seriesResponseEntity).isNotNull();
        Assertions.assertThat(seriesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(seriesResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(seriesResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates series when successful")
    void replace_UpdatesSeries_WhenSuccessful(){
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        savedSeries.setName("new name");
        ResponseEntity<Void> seriesResponseEntity = testRestTemplate.exchange(
                "/series", HttpMethod.PUT, new HttpEntity<>(savedSeries), Void.class);
        Assertions.assertThat(seriesResponseEntity).isNotNull();
        Assertions.assertThat(seriesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes series when successful")
    void delete_RemovesSeries_WhenSuccessful(){
        Series savedSeries = seriesRepository.save(SeriesCreator.createSeriesToBeSaved());
        ResponseEntity<Void> seriesResponseEntity = testRestTemplate.exchange(
                "/series/{id}", HttpMethod.DELETE,null,  Void.class, savedSeries.getId());
        Assertions.assertThat(seriesResponseEntity).isNotNull();
        Assertions.assertThat(seriesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
