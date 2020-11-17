package server.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shift")
@NamedQueries({
        @NamedQuery(name = "findAllShifts", query = "select s from Shift s")
})
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "start", nullable = false)
    private Time beginTime;

    @Column(name = "end", nullable = false)
    private Time endTime;
}
