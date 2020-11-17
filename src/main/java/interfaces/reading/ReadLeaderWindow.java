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
import server.dto.LeaderDTO;
import server.dto.UnitDTO;
import server.service.impl.UnitServiceImpl;

import java.util.List;

public class ReadLeaderWindow extends Stage {

    public ReadLeaderWindow(Stage mainStage, LeaderDTO mainLeader) {

        List<UnitDTO> units =
                UnitServiceImpl.getInstance().findByLeader(mainLeader);

        Label titleLabel = new Label("Керівник №" + mainLeader.getId());
        Label firstNameLabel = new Label("Ім'я:");
        Label lastNameLabel = new Label("Прізвище:");
        Label fatherNameLabel = new Label("По батькові:");
        Label birthdayLabel = new Label("Дата народження:");
        Label phoneLabel = new Label("Номер телефону:");
        Label unitLabel = new Label("Підрозділи:");
        Label firstNameValue = new Label(mainLeader.getFirstName());
        Label lastNameValue = new Label(mainLeader.getLastName());
        Label fatherNameValue = new Label(mainLeader.getFatherName());
        Label birthdayValue = new Label(mainLeader.getBirthdayDate().toString());
        Label phoneValue = new Label(mainLeader.getPhone());
        VBox unitBox = new VBox();
        ScrollPane unitScroll = new ScrollPane(unitBox);

        units.forEach(e -> unitBox.getChildren().add(new Label(e.getName())));

        GridPane root = new GridPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        root.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(60));
        root.getColumnConstraints().addAll(new ColumnConstraints(150),
                new ColumnConstraints(170));

        Font titleFont = Grids.getFonts(mainStage).get(0);
        Font regularFont = Grids.getFonts(mainStage).get(1);
        if (titleFont != null && regularFont != null) {
            titleLabel.setFont(titleFont);
            firstNameLabel.setFont(regularFont);
            lastNameLabel.setFont(regularFont);
            fatherNameLabel.setFont(regularFont);
            birthdayLabel.setFont(regularFont);
            phoneLabel.setFont(regularFont);
            unitLabel.setFont(regularFont);
            firstNameValue.setFont(regularFont);
            lastNameValue.setFont(regularFont);
            fatherNameValue.setFont(regularFont);
            birthdayValue.setFont(regularFont);
            phoneValue.setFont(regularFont);
            unitBox.getChildren().forEach(e -> ((Label) e).setFont(regularFont));
        }

        root.add(titleLabel, 0, 0, 2, 1);
        root.add(firstNameLabel, 0, 1);
        root.add(lastNameLabel, 0, 2);
        root.add(fatherNameLabel, 0, 3);
        root.add(birthdayLabel, 0, 4);
        root.add(phoneLabel, 0, 5);
        root.add(unitLabel, 0, 6);
        root.add(firstNameValue, 1, 1);
        root.add(lastNameValue, 1, 2);
        root.add(fatherNameValue, 1, 3);
        root.add(birthdayValue, 1, 4);
        root.add(phoneValue, 1, 5);
        root.add(unitScroll, 1, 6);

        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(unitLabel, VPos.TOP);

//        root.setGridLinesVisible(true);

        Scene scene = new Scene(root);
        setScene(scene);
        initOwner(mainStage);
        initModality(Modality.WINDOW_MODAL);
        setTitle("Керівник");
        setWidth(345);
        setHeight(290);
        setResizable(false);
        show();

    }
}
