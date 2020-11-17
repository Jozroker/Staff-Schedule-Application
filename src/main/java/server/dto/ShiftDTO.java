package server.dto;

import javafx.scene.control.Button;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShiftDTO {

    private int id;

    private int sequenceNumber;

    @NotNull(message = "{name.invalid}")
    @NotEmpty(message = "{name.invalid}")
    private String name;

    @NotNull(message = "{time.invalid}")
    private LocalTime beginTime;

    @NotNull(message = "{time.invalid}")
    private LocalTime endTime;

    private Button changeBtn = new Button("Змінити");

    private Button deleteBtn = new Button();

}
