package project.demo.series.util;

import project.demo.series.domain.Series;
import project.demo.series.dto_requests.SeriesPostRequestBody;

public class SeriesPostRequestBodyCreator {
    public static SeriesPostRequestBody createSeriesPostRequestBody(){
        return SeriesPostRequestBody.builder().name(SeriesCreator.createSeriesToBeSaved().getName()).build();
    }
}
