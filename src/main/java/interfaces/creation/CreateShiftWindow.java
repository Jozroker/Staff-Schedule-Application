package interfaces.creation;

import interfaces.elements.TimeSpinnerClass;
import interfaces.error.ErrorWindow;
import interfaces.windows.Grids;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.config.ValidationClass;
import server.dto.ShiftDTO;
import server.service.ShiftService;
import server.service.impl.ShiftServiceImpl;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CreateShiftWindow extends Stage {

    ShiftService shiftService = ShiftServiceImpl.getInstance();

    Label titleLabel = new Label("Стоврення запису \"Зміна\"");
    Label nameLabel = new Label("Назва");
    Label startTimeLabel = new Label("Початок");
    Label endTimeLabel = new Label("Кінець");
    TextField nameField = new TextField();
    Spinner<LocalTime> startTimeField = TimeSpinnerClass.getSpinner();
    Spinner<LocalTime> endTimeField = TimeSpinnerClass.getSpinner();
    Button button = new Button("Підтвердити");

    public CreateShiftWindow(Stage mainStage, Tab primaryTab) {

        button.setPrefWidth(100);
        button.setPrefHeight(18);
        nameField.setMaxWidth(150);
        startTimeField.setMaxWidth(110);
        endTimeField.setMaxWidth(110);
        startTimeField.setEditable(true);
        endTimeField.setEditable(true);

        startTimeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                startTimeField.increment(0);
            }
        });

        endTimeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                endTimeField.increment(0);
            }
        });

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getColumnConstraints().addAll(new ColumnConstraints(160),
                new ColumnConstraints(120), new ColumnConstraints(120));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            nameField.setFont(regularFont);
            startTimeLabel.setFont(regularFont);
            endTimeLabel.setFont(regularFont);
        }

        root.add(titleLabel, 0, 0, 3, 1);
        root.add(nameLabel, 0, 1);
        root.add(startTimeLabel, 1, 1);
        root.add(endTimeLabel, 2, 1);
        root.add(nameField, 0, 2);
        root.add(startTimeField, 1, 2);
        root.add(endTimeField, 2, 2);
        root.add(button, 0, 3, 3, 1);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Створити");
        setWidth(425);
        setHeight(170);
        setResizable(false);
        show();

        button.setOnAction(e -> createShift(mainStage, primaryTab));
    }

    private void createShift(Stage mainStage, Tab primaryTab) {
        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setName(nameField.getText());
        try {
            startTimeField.getValue().minusSeconds(startTimeField.getValue().getSecond());
            endTimeField.getValue().minusSeconds(endTimeField.getValue().getSecond());
            shiftDTO.setBeginTime(startTimeField.getValue());
            shiftDTO.setEndTime(endTimeField.getValue());
        } catch (DateTimeParseException e) {
            new ErrorWindow(this, "Некоректний час");
        }
        String message = ValidationClass.validate(shiftDTO);
        if (message.equals("")) {
            shiftService.create(shiftDTO);
            primaryTab.setContent(Grids.getShiftGridPane(mainStage, primaryTab));
            this.close();
        } else {
            new ErrorWindow(this, message);
        }
    }
}
