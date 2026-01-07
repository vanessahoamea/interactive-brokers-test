package ie.interactivebrokers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @JsonProperty("source_currency")
    private String sourceCurrency;
    @JsonProperty("target_currency")
    private String targetCurrency;
    @JsonProperty("source_amount")
    private Double sourceAmount;
    @JsonProperty("target_amount")
    private Double targetAmount;
}
