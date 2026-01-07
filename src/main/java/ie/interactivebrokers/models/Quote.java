package ie.interactivebrokers.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    private String symbol;
    @JsonProperty("name_and_exchange")
    private String nameAndExchange;
    private Integer quantity;
}
