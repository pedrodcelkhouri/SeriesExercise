package project.demo.series.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Series {
    private Long id;
    @JsonProperty("name")
    private String nameSeries;
}
