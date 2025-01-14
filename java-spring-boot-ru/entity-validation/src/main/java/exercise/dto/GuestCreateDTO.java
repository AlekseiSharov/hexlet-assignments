package exercise.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class GuestCreateDTO {
    @NotNull
    private String name;

    @Email
    private String email;

    @Size(min = 11, max = 13, message = "Введен невалидный номер, он должен содержать не менее 11 и не более 13 символов")
    @Pattern(regexp = "^\\+\\d+$", message = "Номер телефона должен начинаться с '+'")
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @FutureOrPresent(message = "Срок действия клубной карты должен быть в будущем или сегодня.")
    private LocalDate cardValidUntil;
}
