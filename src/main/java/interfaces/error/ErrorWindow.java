package interfaces.error;

import interfaces.windows.Grids;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorWindow extends Stage {

    public ErrorWindow(Stage mainStage, String errorMessage) {

        Label label = new Label(errorMessage);
        Button button = new Button("OK");
        button.setPrefWidth(60);
        button.setPrefHeight(18);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (regularFont != null) {
            label.setFont(regularFont);
        }

        GridPane root = new GridPane();
        root.getColumnConstraints().add(new ColumnConstraints(240));
        root.getRowConstraints().addAll(new RowConstraints(40),
                new RowConstraints(30));
        root.add(label, 0, 0);
        root.add(button, 0, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(button, HPos.CENTER);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Помилка");
        setWidth(250);
        setHeight(120);
        setResizable(false);
        show();

        button.setOnAction(e -> close());
    }

}
