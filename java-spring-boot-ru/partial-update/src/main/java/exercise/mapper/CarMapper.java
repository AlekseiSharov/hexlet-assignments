package exercise.mapper;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarDTO;
import exercise.dto.CarUpdateDTO;
import exercise.model.Car;
import org.mapstruct.*;

// BEGIN
@Mapper(
    uses = {JsonNullableMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CarMapper {
    public abstract CarDTO map(Car model);
    public abstract Car map(CarCreateDTO dto);
    public abstract void update(CarUpdateDTO dto, @MappingTarget Car model);
}
// END
