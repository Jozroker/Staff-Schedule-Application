package server.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leader")
@NamedQueries({
        @NamedQuery(name = "findLeaderByPhone", query = "select l from Leader" +
                " l where l.phone = ?1"),
        @NamedQuery(name = "findLeaderByLastName", query = "select l from " +
                "Leader l where l.lastName = ?1"),
        @NamedQuery(name = "findAllLeaders", query = "select l from Leader l")
})
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "father_name", nullable = false)
    private String fatherName;

    @Column(name = "date", nullable = false)
    private Date birthdayDate;

    @Column(nullable = false, length = 10)
    private String phone;
}
