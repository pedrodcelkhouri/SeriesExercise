package project.demo.series.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Series {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @JsonProperty("name")
    private String nameSeries;
}
