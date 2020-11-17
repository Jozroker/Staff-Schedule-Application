package interfaces.changing;

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
import java.util.stream.Collectors;

public class ChangeUnitWindow extends Stage {

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

    public ChangeUnitWindow(Stage mainStage, UnitDTO mainUnit,
                            List<UnitDTO> units, Tab primaryTab) {

        nameField.setText(mainUnit.getName());

        listOfLeader.forEach(e -> {
            RadioButton radioButton =
                    new RadioButton(e.getFirstName() + " " + e.getLastName());
            radioButton.setUserData(e);
            radioButton.setToggleGroup(leaderRadioButtons);
            leaderBox.getChildren().add(radioButton);
            if (e.getId() == mainUnit.getLeaderId()) {
                radioButton.setSelected(true);
            }
        });

        listOfWorkers.forEach(e -> {
            CheckBox checkBox =
                    new CheckBox(e.getFirstName() + " " + e.getLastName());
            checkBox.setUserData(e);
            workerBox.getChildren().add(checkBox);
            if (mainUnit.getWorkerIds().contains(e.getId())) {
                checkBox.setSelected(true);
            }
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
        setTitle("Змінити");
        setWidth(605);
        setHeight(230);
        setResizable(false);
        show();

        button.setOnAction(e -> changeUnit(mainUnit, mainStage, units, primaryTab));
    }

    private void changeUnit(UnitDTO mainUnit, Stage mainStage,
                            List<UnitDTO> units, Tab primaryTab) {
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setId(mainUnit.getId());
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
            unitService.update(unitDTO);
            units = units.stream().map(e -> {
                UnitDTO unit = e;
                if (e.getId() == mainUnit.getId()) {
                    unit = unitDTO;
                }
                return unit;
            }).collect(Collectors.toList());
//            mainStage.setScene(new Scene(InformationalTable.getUnitTable(mainStage, units)));
            primaryTab.setContent(Grids.getUnitGridPane(mainStage, primaryTab, units));
            this.close();
        } else {
            new ErrorWindow(this, message);
        }
    }

}
