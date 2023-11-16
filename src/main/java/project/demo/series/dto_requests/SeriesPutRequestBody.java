package project.demo.series.dto_requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeriesPutRequestBody {
    private Long id;
    private String name;
}
