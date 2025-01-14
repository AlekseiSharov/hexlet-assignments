package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Setter
@Getter
public class CarUpdateDTO {

    private JsonNullable<String> model;

    private JsonNullable<String> manufacturer;

    private JsonNullable<Integer> enginePower;
}
