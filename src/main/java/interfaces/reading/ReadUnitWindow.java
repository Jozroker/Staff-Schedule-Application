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
import server.dto.UnitDTO;
import server.dto.WorkerDTO;
import server.service.impl.WorkerServiceImpl;

import java.util.List;

public class ReadUnitWindow extends Stage {

    public ReadUnitWindow(Stage mainStage, UnitDTO mainUnit) {

        List<WorkerDTO> workers =
                WorkerServiceImpl.getInstance().findByParameters(null,
                        0, 0, mainUnit, null);

        Label titleLabel = new Label("Підрозділ №" + mainUnit.getId());
        Label nameLabel = new Label("Назва:");
        Label workerQtyLabel = new Label("Кількість осіб:");
        Label leaderLabel = new Label("Керівник:");
        Label workerLabel = new Label("Працівники:");
        Label nameValue = new Label(mainUnit.getName());
        Label workerQtyValue =
                new Label(String.valueOf(mainUnit.getWorkerQty()));
        Label leaderValue =
                new Label(mainUnit.getLeaderFirstName() + " " +
                        mainUnit.getLeaderLastName());
        VBox workerBox = new VBox();
        ScrollPane workerScroll = new ScrollPane(workerBox);

        workers.forEach(e -> workerBox.getChildren().add(
                new Label(e.getFirstName() + " " + e.getLastName())));

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(60));
        root.getColumnConstraints().addAll(new ColumnConstraints(140),
                new ColumnConstraints(180));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            nameLabel.setFont(regularFont);
            workerQtyLabel.setFont(regularFont);
            leaderLabel.setFont(regularFont);
            workerLabel.setFont(regularFont);
            nameValue.setFont(regularFont);
            workerQtyValue.setFont(regularFont);
            leaderValue.setFont(regularFont);
            workerBox.getChildren().forEach(e -> ((Label) e).setFont(regularFont));
        }

        root.add(titleLabel, 0, 0, 2, 1);
        root.add(nameLabel, 0, 1);
        root.add(workerQtyLabel, 0, 2);
        root.add(leaderLabel, 0, 3);
        root.add(workerLabel, 0, 4);
        root.add(nameValue, 1, 1);
        root.add(workerQtyValue, 1, 2);
        root.add(leaderValue, 1, 3);
        root.add(workerScroll, 1, 4);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(workerLabel, VPos.TOP);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Підрозділ");
        setWidth(345);
        setHeight(230);
        setResizable(false);
        show();

    }
}
