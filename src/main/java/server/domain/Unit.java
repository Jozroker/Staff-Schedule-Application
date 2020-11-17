package server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "unit")
@NamedQueries({
        @NamedQuery(name = "findUnitByName", query = "select u from Unit u " +
                "where u.name = ?1"),
        @NamedQuery(name = "findUnitByLeader", query = "select u from Unit u " +
                "where u.leader = ?1"),
        @NamedQuery(name = "findUnitByWorker", query = "select u from Unit u " +
                "where ?1 member of u.workers"),
        @NamedQuery(name = "findAllUnits", query = "select u from Unit u")
})
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "worker_qty", nullable = false)
    private int workerQuantity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "leader_id")
    private Leader leader;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "unit_worker",
            joinColumns = @JoinColumn(name = "unit_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "worker_id", nullable = false))
    private Set<Worker> workers = new HashSet<>();
}
