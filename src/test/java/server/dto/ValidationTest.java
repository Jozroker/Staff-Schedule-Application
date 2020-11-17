package server.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

class ValidationTest {

    @Test
    void main(String[] args) {

        LeaderDTO leader = new LeaderDTO();
        leader.setFirstName("Oleg");
        leader.setLastName("K");
        leader.setFatherName("S");
        leader.setBirthdayDate(LocalDate.of(2020, 10, 10));
        leader.setPhone("050669379");

        LeaderDTO leader2 = new LeaderDTO();

        ValidatorFactory validFact = Validation.buildDefaultValidatorFactory();
        Validator validator = validFact.getValidator();
        Set<ConstraintViolation<LeaderDTO>> setLeader1 =
                validator.validate(leader);
        Set<ConstraintViolation<LeaderDTO>> setLeader2 =
                validator.validate(leader2);

        if (!setLeader1.isEmpty()) {
            System.out.println("Message 1");
            for (ConstraintViolation<LeaderDTO> except : setLeader1) {
                System.out.println(except.getMessage());
            }
        }
        if (!setLeader2.isEmpty()) {
            System.out.println("Message 2");
            for (ConstraintViolation<LeaderDTO> except : setLeader2) {
                System.out.println(except.getMessage());
            }
        }
    }

}