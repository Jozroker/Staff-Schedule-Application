package server.dto;

import javafx.scene.control.Button;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UnitDTO {

    private int id;

    private int sequenceNumber;

    @NotNull(message = "{name.invalid}")
    @NotEmpty(message = "{name.invalid}")
    private String name;

    private int workerQty = 0;

    @NotNull(message = "{unit.leader.disable}")
    @Range(min = 1, message = "{unit.leader.disable}")
    private int leaderId;

    private String leaderFirstName;

    private String leaderLastName;

    private Set<Integer> workerIds = new HashSet<>();

    private Button changeBtn = new Button("Змінити");

    private Button deleteBtn = new Button();

    public void addWorker(WorkerDTO worker) {
        this.workerIds.add(worker.getId());
        this.workerQty++;
    }

    public void removeWorker(WorkerDTO worker) {
        this.workerIds.remove(worker.getId());
        this.workerQty--;
    }
}
