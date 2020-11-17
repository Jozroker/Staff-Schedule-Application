package interfaces.reading;

import interfaces.windows.Grids;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.dto.PositionDTO;
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.service.impl.PositionServiceImpl;
import server.service.impl.UnitServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ReadWorkerWindow extends Stage {

    public ReadWorkerWindow(Stage mainStage, WorkerDTO mainWorker) {

        List<UnitDTO> units =
                UnitServiceImpl.getInstance().findByWorker(mainWorker);
        List<PositionDTO> positions =
                mainWorker.getPositionIds().stream().map(e -> PositionServiceImpl
                        .getInstance().findById(e)).collect(Collectors.toList());

        Label titleLabel = new Label("Працівник №" + mainWorker.getId());
        Label firstNameLabel = new Label("Ім'я:");
        Label lastNameLabel = new Label("Прізвище:");
        Label birthdayLabel = new Label("Дата народження:");
        Label phoneLabel = new Label("Номер телефону:");
        Label stageLabel = new Label("Стаж:");
        Label shiftLabel = new Label("Зміна:");
        Label unitLabel = new Label("Підрозділи");
        Label positionLabel = new Label("Посади");
        Label firstNameValue = new Label(mainWorker.getFirstName());
        Label lastNameValue = new Label(mainWorker.getLastName());
        Label birthdayValue =
                new Label(mainWorker.getBirthdayDate().toString());
        Label phoneValue = new Label(mainWorker.getPhoneNumber());
        Label stageValue = new Label(String.valueOf(mainWorker.getStage()));
        Label shiftValue = new Label(mainWorker.getShiftName());
        VBox unitBox = new VBox();
        VBox positionBox = new VBox();
        ScrollPane unitScroll = new ScrollPane(unitBox);
        ScrollPane positionScroll = new ScrollPane(positionBox);

        units.forEach(e -> unitBox.getChildren().add(
                new Label(e.getName())));
        positions.forEach(e -> positionBox.getChildren().add(
                new Label(e.getName())));

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30));
        root.getColumnConstraints().addAll(new ColumnConstraints(150),
                new ColumnConstraints(130), new ColumnConstraints(200));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            firstNameLabel.setFont(regularFont);
            lastNameLabel.setFont(regularFont);
            birthdayLabel.setFont(regularFont);
            phoneLabel.setFont(regularFont);
            stageLabel.setFont(regularFont);
            firstNameValue.setFont(regularFont);
            lastNameValue.setFont(regularFont);
            birthdayValue.setFont(regularFont);
            phoneValue.setFont(regularFont);
            stageValue.setFont(regularFont);
            shiftLabel.setFont(regularFont);
            shiftValue.setFont(regularFont);
            unitLabel.setFont(regularFont);
            positionLabel.setFont(regularFont);
            unitBox.getChildren().forEach(e -> ((Label) e).setFont(regularFont));
            positionBox.getChildren().forEach(e -> ((Label) e).setFont(regularFont));
        }

        root.add(titleLabel, 0, 0, 3, 1);
        root.add(firstNameLabel, 0, 1);
        root.add(lastNameLabel, 0, 2);
        root.add(birthdayLabel, 0, 3);
        root.add(phoneLabel, 0, 4);
        root.add(stageLabel, 0, 5);
        root.add(shiftLabel, 0, 6);
        root.add(unitLabel, 2, 1);
        root.add(positionLabel, 2, 4);
        root.add(firstNameValue, 1, 1);
        root.add(lastNameValue, 1, 2);
        root.add(birthdayValue, 1, 3);
        root.add(phoneValue, 1, 4);
        root.add(stageValue, 1, 5);
        root.add(shiftValue, 1, 6);
        root.add(unitScroll, 2, 2, 1, 2);
        root.add(positionScroll, 2, 5, 1, 2);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(positionBox, VPos.TOP);
        GridPane.setValignment(unitBox, VPos.TOP);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Працівник");
        setWidth(505);
        setHeight(260);
        setResizable(false);
        show();
    }
}
