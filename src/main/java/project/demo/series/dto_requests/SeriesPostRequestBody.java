package project.demo.series.dto_requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SeriesPostRequestBody {
    @NotEmpty(message = "The name of this series cannot be empty")
    private String name;
}
