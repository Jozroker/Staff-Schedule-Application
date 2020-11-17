package server.dto;

import javafx.scene.control.Button;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WorkerDTO {

    private int id;

    private int sequenceNumber;

    @NotNull(message = "{firstname.invalid}")
    @NotEmpty(message = "{firstname.invalid}")
    private String firstName;

    @NotNull(message = "{lastname.invalid}")
    @NotEmpty(message = "{lastname.invalid}")
    private String lastName;

    @NotNull(message = "{date.invalid}")
    private LocalDate birthdayDate;

    @NotNull(message = "{phone.invalid}")
    @Size(max = 10, min = 10, message = "{phone.invalid}")
    private String phoneNumber;

    private int stage = 0;

    @NotNull(message = "{worker.shift.disable}")
    @Range(min = 1, message = "{worker.shift.disable}")
    private int shiftId;

    private String shiftName;

    private Set<Integer> unitIds = new HashSet<>();

    @NotEmpty(message = "{worker.positions.empty}")
    private Set<Integer> positionIds = new HashSet<>();

    private Button changeBtn = new Button("Змінити");

    private Button deleteBtn = new Button();

    public void addUnit(UnitDTO unit) {
        this.unitIds.add(unit.getId());
    }

    public void removeUnit(UnitDTO unit) {
        this.unitIds.remove(unit.getId());
    }

    public void addPosition(PositionDTO position) {
        this.positionIds.add(position.getId());
    }

    public void removePosition(PositionDTO position) {
        this.positionIds.remove(position.getId());
    }
}
