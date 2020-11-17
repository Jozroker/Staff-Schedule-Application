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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import server.config.ValidationClass;
import server.dto.LeaderDTO;
import server.service.LeaderService;
import server.service.impl.LeaderServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateLeaderWindow extends Stage {

    private final LeaderService leaderService = LeaderServiceImpl.getInstance();

    Label titleLabel = new Label("Створення запису \"Керівник\"");
    Label firstNameLabel = new Label("Ім'я");
    Label birthdayLabel = new Label("Дата народження");
    Label lastNameLabel = new Label("Прізвище");
    Label phoneLabel = new Label("Номер телефону");
    Label fatherNameLabel = new Label("По батькові");
    Button button = new Button("Підтвердити");
    TextField firstNameField = new TextField();
    TextField lastNameField = new TextField();
    TextField phoneField = new TextField();
    TextField fatherNameField = new TextField();
    DatePicker birthdayField = new DatePicker();

    public CreateLeaderWindow(Stage mainStage, Tab primaryTab,
                              List<LeaderDTO> leaders) {

        birthdayField.setConverter(converter);
        button.setPrefWidth(100);
        button.setPrefHeight(18);
        firstNameField.setMaxWidth(150);
        lastNameField.setMaxWidth(150);
        fatherNameField.setMaxWidth(150);
        birthdayField.setMaxWidth(150);
        phoneField.setMaxWidth(150);

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getColumnConstraints().addAll(new ColumnConstraints(160),
                new ColumnConstraints(160));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            firstNameLabel.setFont(regularFont);
            lastNameLabel.setFont(regularFont);
            fatherNameLabel.setFont(regularFont);
            birthdayLabel.setFont(regularFont);
            phoneLabel.setFont(regularFont);
        }

        root.add(titleLabel, 0, 0, 2, 1);//
        root.add(firstNameLabel, 0, 1);
        root.add(firstNameField, 0, 2);
        root.add(lastNameLabel, 0, 3);
        root.add(lastNameField, 0, 4);
        root.add(fatherNameLabel, 0, 5);
        root.add(fatherNameField, 0, 6);
        root.add(button, 0, 7, 2, 1);
        root.add(birthdayLabel, 1, 1);
        root.add(birthdayField, 1, 2);
        root.add(phoneLabel, 1, 3);
        root.add(phoneField, 1, 4);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Створити");
        setWidth(340);
        setHeight(290);
        setResizable(false);
        show();

        button.setOnAction(e -> createLeader(mainStage, primaryTab, leaders));

    }

    private void createLeader(Stage primaryStage, Tab primaryTab,
                              List<LeaderDTO> leaders) {
        LeaderDTO leaderDTO = new LeaderDTO();
        leaderDTO.setFirstName(firstNameField.getText());
        leaderDTO.setLastName(lastNameField.getText());
        leaderDTO.setFatherName(fatherNameField.getText());
        leaderDTO.setPhone(phoneField.getText());
        leaderDTO.setBirthdayDate(birthdayField.getValue());
        String message = ValidationClass.validate(leaderDTO);
        if (message.equals("")) {
            leaderService.create(leaderDTO);
            leaders.add(leaderDTO);
            primaryTab.setContent(Grids.getLeaderGridPane(primaryStage,
                    primaryTab, leaders));
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
