package interfaces.changing;

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
import server.dto.PositionDTO;
import server.service.PositionService;
import server.service.impl.PositionServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ChangePositionWindow extends Stage {

    PositionService positionService = PositionServiceImpl.getInstance();

    Label titleLabel = new Label("Зміна запису \"Посада\"");
    Label nameLabel = new Label("Назва");
    Label salaryLabel = new Label("Зарплата");
    Label allowanceLabel = new Label("Надбавка");
    TextField nameField = new TextField();
    Spinner<Double> salaryField = new Spinner<>(0.01, Double.MAX_VALUE, 0,
            0.01);
    Spinner<Double> allowanceField = new Spinner<>(0.01, Double.MAX_VALUE, 0,
            0.01);
    Button button = new Button("Підтвердити");

    public ChangePositionWindow(Stage mainStage, PositionDTO mainPosition,
                                List<PositionDTO> positions, Tab primaryTab) {

        nameField.setText(mainPosition.getName());
        salaryField.getValueFactory().setValue(mainPosition.getSalary());
        allowanceField.getValueFactory().setValue(mainPosition.getAllowance());

        button.setPrefWidth(100);
        button.setPrefHeight(18);
        nameField.setMaxWidth(150);
        salaryField.setMaxWidth(110);
        allowanceField.setMaxWidth(110);
        salaryField.setEditable(true);
        allowanceField.setEditable(true);

        salaryField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                salaryField.increment(0);
            }
        });

        allowanceField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                allowanceField.increment(0);
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
            salaryLabel.setFont(regularFont);
            allowanceLabel.setFont(regularFont);
        }

        root.add(titleLabel, 0, 0, 3, 1);
        root.add(nameLabel, 0, 1);
        root.add(salaryLabel, 1, 1);
        root.add(allowanceLabel, 2, 1);
        root.add(nameField, 0, 2);
        root.add(salaryField, 1, 2);
        root.add(allowanceField, 2, 2);
        root.add(button, 0, 3, 3, 1);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Змінити");
        setWidth(425);
        setHeight(170);
        setResizable(false);
        show();

        button.setOnAction(e -> changePosition(mainPosition, mainStage,
                positions, primaryTab));
    }

    private void changePosition(PositionDTO mainPosition, Stage mainStage,
                                List<PositionDTO> positions, Tab primaryTab) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setId(mainPosition.getId());
        positionDTO.setName(nameField.getText());
        positionDTO.setSalary(salaryField.getValue());
        positionDTO.setAllowance(allowanceField.getValue());
        String message = ValidationClass.validate(positionDTO);
        if (message.equals("")) {
            positionService.update(positionDTO);
            positions = positions.stream().map(e -> {
                PositionDTO position = e;
                if (e.getId() == mainPosition.getId()) {
                    position = positionDTO;
                }
                return position;
            }).collect(Collectors.toList());
//            mainStage.setScene(new Scene(InformationalTable.getPositionTable(mainStage, positions)));
            primaryTab.setContent(Grids.getPositionGridPane(mainStage,
                    primaryTab, positions));
            this.close();
        } else {
            new ErrorWindow(this, message);
        }
    }

}
