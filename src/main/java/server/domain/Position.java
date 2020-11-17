package server.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "position")
@NamedQueries({
        @NamedQuery(name = "findAllPositions", query = "select p from " +
                "Position p"),
        @NamedQuery(name = "findPositionByName", query = "select p from " +
                "Position p where p.name = ?1"),
        @NamedQuery(name = "findPositionBySalary", query = "select p from " +
                "Position  p where p.salary >= ?1 and p.salary <= ?2")
})
public class Position {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal salary;

    private BigDecimal allowance;
}
