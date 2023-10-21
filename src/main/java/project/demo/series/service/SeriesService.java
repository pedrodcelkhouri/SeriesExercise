package project.demo.series.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.demo.series.domain.Series;
import project.demo.series.dto_requests.SeriesPostRequestBody;
import project.demo.series.dto_requests.SeriesPutRequestBody;
import project.demo.series.exception.BadRequestException;
import project.demo.series.repository.SeriesRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public Page<Series> listAll(Pageable pageable) {
        return seriesRepository.findAll(pageable);
    }

    public List<Series> findByName(String name) {
        return seriesRepository.findByName(name);
    }

    public Series findByIdOrThrowBadRequestException(long id) {
        return seriesRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Series not found"));
    }

    @Transactional
    public Series save(SeriesPostRequestBody seriesPostRequestBody) {
        return seriesRepository.save(Series.builder().name(seriesPostRequestBody.getName()).build());
    }

    public void delete(long id) {
        seriesRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(SeriesPutRequestBody seriesPutRequestBody) {
        Series savedSeries = findByIdOrThrowBadRequestException(seriesPutRequestBody.getId());
        seriesRepository.save(Series.builder()
                .id(savedSeries.getId())
                .name(seriesPutRequestBody.getName())
                .build());
    }
}
