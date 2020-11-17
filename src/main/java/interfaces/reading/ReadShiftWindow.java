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
import server.dto.ShiftDTO;
import server.dto.WorkerDTO;
import server.service.impl.WorkerServiceImpl;

import java.util.List;

public class ReadShiftWindow extends Stage {

    public ReadShiftWindow(Stage mainStage, ShiftDTO mainShift) {

        List<WorkerDTO> workers =
                WorkerServiceImpl.getInstance().findByParameters(mainShift, 0,
                        0, null, null);

        Label titleLabel = new Label("Зміна №" + mainShift.getId());
        Label nameLabel = new Label("Назва:");
        Label startTimeLabel = new Label("Початок:");
        Label endTimeLabel = new Label("Кінець:");
        Label workerLabel = new Label("Працівники:");
        Label nameValue = new Label(mainShift.getName());
        Label startTimeValue =
                new Label(mainShift.getBeginTime().toString().substring(0, 5));
        Label endTimeValue =
                new Label(mainShift.getEndTime().toString().substring(0, 5));
        VBox workerBox = new VBox();
        ScrollPane workerScroll = new ScrollPane(workerBox);

        workers.forEach(e -> workerBox.getChildren().add(
                new Label(e.getFirstName() + " " + e.getLastName())));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            nameLabel.setFont(regularFont);
            startTimeLabel.setFont(regularFont);
            endTimeLabel.setFont(regularFont);
            workerLabel.setFont(regularFont);
            nameValue.setFont(regularFont);
            startTimeValue.setFont(regularFont);
            endTimeValue.setFont(regularFont);
            workerBox.getChildren().forEach(e -> ((Label) e).setFont(regularFont));
        }

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(60));
        root.getColumnConstraints().addAll(new ColumnConstraints(140),
                new ColumnConstraints(180));

        root.add(titleLabel, 0, 0, 2, 1);
        root.add(nameLabel, 0, 1);
        root.add(startTimeLabel, 0, 2);
        root.add(endTimeLabel, 0, 3);
        root.add(workerLabel, 0, 4);
        root.add(nameValue, 1, 1);
        root.add(startTimeValue, 1, 2);
        root.add(endTimeValue, 1, 3);
        root.add(workerScroll, 1, 4);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(workerLabel, VPos.TOP);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Зміна");
        setWidth(345);
        setHeight(230);
        setResizable(false);
        show();

    }
}
