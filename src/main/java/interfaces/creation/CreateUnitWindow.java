package interfaces.creation;

import interfaces.error.ErrorWindow;
import interfaces.windows.Grids;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.config.ValidationClass;
import server.dto.LeaderDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.service.UnitService;
import server.service.impl.LeaderServiceImpl;
import server.service.impl.UnitServiceImpl;
import server.service.impl.WorkerServiceImpl;

import java.util.List;

public class CreateUnitWindow extends Stage {

    private final UnitService unitService = UnitServiceImpl.getInstance();
    private final List<LeaderDTO> listOfLeader =
            LeaderServiceImpl.getInstance().findAll();
    private final List<WorkerDTO> listOfWorkers =
            WorkerServiceImpl.getInstance().findAll();

    Label titleLabel = new Label("Створення запису \"Підрозділ\"");
    Label nameLabel = new Label("Назва");
    Label leaderLabel = new Label("Керівник");
    Label workerLabel = new Label("Працівники");
    Button button = new Button("Підтвердити");
    TextField nameField = new TextField();
    VBox leaderBox = new VBox();
    VBox workerBox = new VBox();
    ToggleGroup leaderRadioButtons = new ToggleGroup();
    ScrollPane leaderScroll = new ScrollPane(leaderBox);
    ScrollPane workerScroll = new ScrollPane(workerBox);

    public CreateUnitWindow(Stage mainStage, Tab primaryTab,
                            List<UnitDTO> units) {

        listOfLeader.forEach(e -> {
            RadioButton radioButton =
                    new RadioButton(e.getFirstName() + " " + e.getLastName());
            radioButton.setUserData(e);
            radioButton.setToggleGroup(leaderRadioButtons);
            leaderBox.getChildren().add(radioButton);
        });

        listOfWorkers.forEach(e -> {
            CheckBox checkBox =
                    new CheckBox(e.getFirstName() + " " + e.getLastName());
            checkBox.setUserData(e);
            workerBox.getChildren().add(checkBox);
        });

        button.setPrefWidth(100);
        button.setPrefHeight(18);
        nameField.setMaxWidth(150);
        leaderBox.setMaxWidth(200);
        workerBox.setMaxWidth(200);

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getColumnConstraints().addAll(new ColumnConstraints(160),
                new ColumnConstraints(210), new ColumnConstraints(210));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(90),
                new RowConstraints(30));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            nameField.setFont(regularFont);
            leaderLabel.setFont(regularFont);
            workerLabel.setFont(regularFont);
        }

        root.add(titleLabel, 0, 0, 3, 1);
        root.add(nameLabel, 0, 1);
        root.add(leaderLabel, 1, 1);
        root.add(workerLabel, 2, 1);
        root.add(nameField, 0, 2);
        root.add(leaderScroll, 1, 2);
        root.add(workerScroll, 2, 2);
        root.add(button, 0, 3, 3, 1);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setValignment(nameField, VPos.TOP);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Створити");
        setWidth(605);
        setHeight(230);
        setResizable(false);
        show();

        button.setOnAction(e -> createUnit(mainStage, primaryTab, units));
    }

    private void createUnit(Stage primaryStage, Tab primaryTab,
                            List<UnitDTO> units) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setName(nameField.getText());
        LeaderDTO leaderDTO = new LeaderDTO();
        try {
            leaderDTO =
                    (LeaderDTO) leaderRadioButtons.getSelectedToggle().getUserData();
        } catch (NullPointerException ignore) {
//            new ErrorWindow(this, "Leader not selected");
        }
        unitDTO.setLeaderId(leaderDTO.getId());
        workerBox.getChildren().forEach(e -> {
            CheckBox checkBox = (CheckBox) e;
            if (checkBox.isSelected()) {
                unitDTO.addWorker((WorkerDTO) checkBox.getUserData());
            }
        });
        String message = ValidationClass.validate(unitDTO);
        if (message.equals("")) {
            unitService.create(unitDTO);
            units.add(unitDTO);
            primaryTab.setContent(Grids.getUnitGridPane(primaryStage,
                    primaryTab, units));
            this.close();
        } else {
            new ErrorWindow(this, message);
        }
    }
}
