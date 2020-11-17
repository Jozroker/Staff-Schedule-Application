package server.config;

import server.dto.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationClass {

    private static final ValidatorFactory vf =
            Validation.buildDefaultValidatorFactory();
    private static final Validator validator = vf.getValidator();

    public static String validate(LeaderDTO leader) {
        Set<ConstraintViolation<LeaderDTO>> violations =
                validator.validate(leader);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }
        return "";
    }

    public static String validate(PositionDTO position) {
        Set<ConstraintViolation<PositionDTO>> violations =
                validator.validate(position);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }
        return "";
    }

    public static String validate(UnitDTO unit) {
        Set<ConstraintViolation<UnitDTO>> violations =
                validator.validate(unit);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }
        return "";
    }

    public static String validate(ShiftDTO shift) {
        Set<ConstraintViolation<ShiftDTO>> violations =
                validator.validate(shift);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }
        return "";
    }

    public static String validate(WorkerDTO worker) {
        Set<ConstraintViolation<WorkerDTO>> violations =
                validator.validate(worker);
        if (!violations.isEmpty()) {
            return violations.stream().findFirst().get().getMessage();
        }
        return "";
    }
}
