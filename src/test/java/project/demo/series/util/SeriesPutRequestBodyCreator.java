package project.demo.series.util;

import project.demo.series.dto_requests.SeriesPutRequestBody;

public class SeriesPutRequestBodyCreator {
    public static SeriesPutRequestBody createSeriesPutRequestBody(){
        return SeriesPutRequestBody.builder()
                .id(SeriesCreator.createValidUpdateSeries().getId())
                .name(SeriesCreator.createValidUpdateSeries().getName()).build();
    }
}
