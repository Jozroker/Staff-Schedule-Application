package server.dto;

import javafx.scene.control.Button;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PositionDTO {

    private int id;

    private int sequenceNumber;

    @NotNull(message = "{name.invalid}")
    @NotEmpty(message = "{name.invalid}")
    private String name;

    @NotNull(message = "{number.invalid}")
    @DecimalMin(value = "0.01", message = "{negative.salary}")
    @Digits(integer = 8, fraction = 2, message = "{number.invalid}")
    private double salary;

    @Digits(integer = 6, fraction = 2, message = "{number.invalid}")
    private double allowance;

    private Button changeBtn = new Button("Змінити");

    private Button deleteBtn = new Button();
}
