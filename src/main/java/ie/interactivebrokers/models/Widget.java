package ie.interactivebrokers.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Widget {
    private List<String> active;
    private List<String> inactive;
}
