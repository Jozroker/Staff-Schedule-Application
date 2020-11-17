package interfaces.creation;

import interfaces.error.ErrorWindow;
import interfaces.windows.Grids;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import server.config.ValidationClass;
import server.dto.PositionDTO;
import server.dto.ShiftDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.service.WorkerService;
import server.service.impl.PositionServiceImpl;
import server.service.impl.ShiftServiceImpl;
import server.service.impl.UnitServiceImpl;
import server.service.impl.WorkerServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateWorkerWindow extends Stage {

    private final List<ShiftDTO> listOfShift =
            ShiftServiceImpl.getInstance().findAll();
    private final List<UnitDTO> listOfUnit =
            UnitServiceImpl.getInstance().findAll();
    private final List<PositionDTO> listOfPositions =
            PositionServiceImpl.getInstance().findAll();
    private final WorkerService workerService = WorkerServiceImpl.getInstance();

    Label titleLabel = new Label("Створення запису \"Працівник\"");
    Label firstNameLabel = new Label("Ім'я");
    Label lastNameLabel = new Label("Прізвище");
    Label birthdayLabel = new Label("Дата народження");
    Label phoneLabel = new Label("Номер телефону");
    Label stageLabel = new Label("Стаж");
    Label shiftLabel = new Label("Зміна");
    Label unitLabel = new Label("Підрозділ");
    Label positionLabel = new Label("Посада");
    Button button = new Button("Підтвердити");
    TextField firstNameField = new TextField();
    TextField lastNameField = new TextField();
    TextField phoneField = new TextField();
    DatePicker birthdayField = new DatePicker();
    Spinner<Integer> stageField = new Spinner<>(0, 50, 0, 1);
    VBox shiftBox = new VBox();
    VBox unitBox = new VBox();
    VBox positionBox = new VBox();
    ToggleGroup shiftRadioButtons = new ToggleGroup();
    ScrollPane shiftScroll = new ScrollPane(shiftBox);
    ScrollPane unitScroll = new ScrollPane(unitBox);
    ScrollPane positionScroll = new ScrollPane(positionBox);

    public CreateWorkerWindow(Stage mainStage, Tab primaryTab,
                              List<WorkerDTO> workers) {

        stageField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stageField.increment(0);
            }
        });

        listOfShift.forEach(e -> {
            RadioButton radioButton =
                    new RadioButton(e.getName());
            radioButton.setUserData(e);
            radioButton.setToggleGroup(shiftRadioButtons);
            shiftBox.getChildren().add(radioButton);
        });

        listOfUnit.forEach(e -> {
            CheckBox checkBox =
                    new CheckBox(e.getName());
            checkBox.setUserData(e);
            unitBox.getChildren().add(checkBox);
        });

        listOfPositions.forEach(e -> {
            CheckBox checkBox = new CheckBox(e.getName());
            checkBox.setUserData(e);
            positionBox.getChildren().add(checkBox);
        });

        button.setPrefWidth(100);
        button.setPrefHeight(18);
        firstNameField.setMaxWidth(150);
        lastNameField.setMaxWidth(150);
        birthdayField.setMaxWidth(150);
        phoneField.setMaxWidth(150);
        stageField.setMaxWidth(70);
        shiftBox.setMaxWidth(150);
        unitBox.setMaxWidth(150);
        positionBox.setMaxWidth(150);
        stageField.setEditable(true);
        birthdayField.setConverter(converter);

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getColumnConstraints().addAll(new ColumnConstraints(160),
                new ColumnConstraints(80), new ColumnConstraints(80),
                new ColumnConstraints(80), new ColumnConstraints(80),
                new ColumnConstraints(80), new ColumnConstraints(80));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(90),
                new RowConstraints(30));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            firstNameLabel.setFont(regularFont);
            lastNameLabel.setFont(regularFont);
            birthdayLabel.setFont(regularFont);
            phoneLabel.setFont(regularFont);
            stageLabel.setFont(regularFont);
            shiftLabel.setFont(regularFont);
            unitLabel.setFont(regularFont);
            positionLabel.setFont(regularFont);
        }

        root.add(titleLabel, 0, 0, 7, 1);
        root.add(firstNameLabel, 0, 1);
        root.add(lastNameLabel, 1, 1, 2, 1);
        root.add(birthdayLabel, 3, 1, 2, 1);
        root.add(phoneLabel, 5, 1, 2, 1);
        root.add(firstNameField, 0, 2);
        root.add(lastNameField, 1, 2, 2, 1);
        root.add(birthdayField, 3, 2, 2, 1);
        root.add(phoneField, 5, 2, 2, 1);
        root.add(stageLabel, 0, 3);
        root.add(unitLabel, 1, 3, 3, 1);
        root.add(positionLabel, 4, 3, 3, 1);
        root.add(stageField, 0, 4);
        root.add(shiftLabel, 0, 5);
        root.add(shiftScroll, 0, 6);
        root.add(unitScroll, 1, 4, 3, 3);
        root.add(positionScroll, 4, 4, 3, 3);
        root.add(button, 0, 7, 7, 1);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Створити");
        setWidth(665);
        setHeight(345);
        setResizable(false);
        show();

        button.setOnAction(e -> createWorker(mainStage, primaryTab,
                workers));

    }

    private void createWorker(Stage primaryStage, Tab primaryTab,
                              List<WorkerDTO> workers) {
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setFirstName(firstNameField.getText());
        workerDTO.setLastName(lastNameField.getText());
        workerDTO.setBirthdayDate(birthdayField.getValue());
        workerDTO.setPhoneNumber(phoneField.getText());
        workerDTO.setStage(stageField.getValue());
        ShiftDTO shiftDTO = new ShiftDTO();
        try {
            shiftDTO = (ShiftDTO) shiftRadioButtons.getSelectedToggle().getUserData();
        } catch (Exception ignore) {
        }
        workerDTO.setShiftId(shiftDTO.getId());
        unitBox.getChildren().forEach(e -> {
            CheckBox checkBox = (CheckBox) e;
            if (checkBox.isSelected()) {
                workerDTO.addUnit((UnitDTO) checkBox.getUserData());
            }
        });
        positionBox.getChildren().forEach(e -> {
            CheckBox checkBox = (CheckBox) e;
            if (checkBox.isSelected()) {
                workerDTO.addPosition((PositionDTO) checkBox.getUserData());
            }
        });
        String message = ValidationClass.validate(workerDTO);
        if (message.equals("")) {
            workerService.create(workerDTO);
            workers.add(workerDTO);
            primaryTab.setContent(Grids.getWorkerGridPane(primaryStage,
                    primaryTab, workers));
            this.close();
        } else {
            new ErrorWindow(this, message);
        }

    }

    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        final DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };
}
