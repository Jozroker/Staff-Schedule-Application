package server.dto;

import javafx.scene.control.Button;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LeaderDTO {

    private int id;

    private int sequenceNumber;

    @NotNull(message = "{firstname.invalid}")
    @NotEmpty(message = "{firstname.invalid}")
    private String firstName;

    @NotNull(message = "{lastname.invalid}")
    @NotEmpty(message = "{lastname.invalid}")
    private String lastName;

    @NotNull(message = "{father.name.invalid}")
    @NotEmpty(message = "{father.name.invalid}")
    private String fatherName;

    @NotNull(message = "{date.invalid}")
    private LocalDate birthdayDate;

    @NotNull(message = "{phone.invalid}")
    @Size(max = 10, min = 10, message = "{phone.invalid}")
    private String phone;

    private Button changeBtn = new Button("Змінити");

    private Button deleteBtn = new Button();
}
