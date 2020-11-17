package interfaces.elements;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class TimeSpinnerClass {

    public static Spinner<LocalTime> getSpinner() {
        return (Spinner<LocalTime>) new Spinner(new SpinnerValueFactory() {

            {
                setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"),
                        DateTimeFormatter.ofPattern("HH:mm")));
            }

            @Override
            public void decrement(int steps) {
                if (getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(steps));
                }
            }

            @Override
            public void increment(int steps) {
                if (this.getValue() == null)
                    setValue(LocalTime.now());
                else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(steps));
                }
            }
        });
    }
}
