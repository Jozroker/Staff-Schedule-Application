package server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "worker")
@NamedQueries({
        @NamedQuery(name = "findWorkerByLastName", query = "select w from " +
                "Worker w " +
                "where w.lastName = ?1"),
        @NamedQuery(name = "findWorkerByPhone", query = "select w from Worker w " +
                "where w.phone = ?1"),
        @NamedQuery(name = "findWorkerByShift", query = "select w from Worker w " +
                "where w.shift = ?1"),
        @NamedQuery(name = "findWorkerByPosition", query = "select w from Worker w " +
                "where ?1 member of w.positions"),
        @NamedQuery(name = "findWorkerByUnit", query = "select w from Worker w " +
                "where ?1 member of w.units"),
        @NamedQuery(name = "findWorkerByStage", query = "select w from Worker w " +
                "where w.stage >= ?1 and w.stage <= ?2"),
        @NamedQuery(name = "findAllWorkers", query = "select w from Worker w")

})
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "date", nullable = false)
    private Date birthdayDate;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(nullable = false)
    private int stage;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "worker_position",
            joinColumns = @JoinColumn(name = "worker_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "position_id", nullable = false))
    private Set<Position> positions = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "unit_worker",
            joinColumns = @JoinColumn(name = "worker_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "unit_id", nullable = false))
    private Set<Unit> units = new HashSet<>();
}
