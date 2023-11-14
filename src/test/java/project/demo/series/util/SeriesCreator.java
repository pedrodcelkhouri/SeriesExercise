package project.demo.series.util;

import project.demo.series.domain.Series;

public class SeriesCreator {
    public static Series createSeriesToBeSaved(){
        return Series.builder().name("Vikings").build();
    }
    public static Series createValidSeries(){
        return Series.builder().name("Vikings").id(1L).build();
    }
    public static Series createValidUpdateSeries(){
        return Series.builder().name("Vikings 2").id(1L).build();
    }
}
