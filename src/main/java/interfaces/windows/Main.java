package interfaces.windows;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.service.impl.LeaderServiceImpl;
import server.service.impl.PositionServiceImpl;
import server.service.impl.UnitServiceImpl;
import server.service.impl.WorkerServiceImpl;

public class Main extends Application {

    public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        Tab unitTab = tab("Підрозділи");
        Tab leaderTab = tab("Керівники");
        Tab positionTab = tab("Посади");
        Tab workerTab = tab("Працівники");
        Tab shiftTab = tab("Зміни");
        TabPane menu = new TabPane();
        menu.setRotateGraphic(true);
        menu.setTabMinHeight(140);
        menu.setTabMaxWidth(30);

        menu.getTabs().addAll(leaderTab, workerTab, positionTab, unitTab,
                shiftTab);
        menu.setSide(Side.LEFT);
        menu.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        menu.getSelectionModel().selectedItemProperty().addListener((ov, oldTab,
                                                                     newTab) -> {
            if (newTab == shiftTab) {
                primaryStage.setWidth(535);
                shiftTab.setContent(Grids.getShiftGridPane(primaryStage, shiftTab));
            } else if (newTab == unitTab) {
                primaryStage.setWidth(635);
                unitTab.setContent(Grids.getUnitGridPane(primaryStage,
                        unitTab, UnitServiceImpl.getInstance().findAll()));
            } else if (newTab == leaderTab) {
                primaryStage.setWidth(795);
                leaderTab.setContent(Grids.getLeaderGridPane(primaryStage,
                        leaderTab, LeaderServiceImpl.getInstance().findAll()));
            } else if (newTab == positionTab) {
                primaryStage.setWidth(625);
                positionTab.setContent(Grids.getPositionGridPane(primaryStage,
                        positionTab, PositionServiceImpl.getInstance().findAll()));
            } else if (newTab == workerTab) {
                primaryStage.setWidth(765);
                workerTab.setContent(Grids.getWorkerGridPane(primaryStage,
                        workerTab, WorkerServiceImpl.getInstance().findAll()));
            }
        });

        shiftTab.setContent(Grids.getShiftGridPane(primaryStage, shiftTab));
        leaderTab.setContent(Grids.getLeaderGridPane(primaryStage,
                leaderTab, LeaderServiceImpl.getInstance().findAll()));
        unitTab.setContent(Grids.getUnitGridPane(primaryStage, unitTab,
                UnitServiceImpl.getInstance().findAll()));
        positionTab.setContent(Grids.getPositionGridPane(primaryStage,
                positionTab, PositionServiceImpl.getInstance().findAll()));
        workerTab.setContent(Grids.getWorkerGridPane(primaryStage,
                workerTab, WorkerServiceImpl.getInstance().findAll()));

        Scene scene = new Scene(menu);
        primaryStage.setScene(scene);

//        GridPane table = InformationalTable.getShiftTable(primaryStage);
//        Scene scene = new Scene(table);
//        primaryStage.setScene(scene);

//        TableView<Object> table =
//                InformationalTable.getShiftTable(primaryStage);
//        Scene scene = new Scene(table);
//        primaryStage.setScene(scene);

        primaryStage.setWidth(795);
//        table.setGridLinesVisible(true);
        primaryStage.setHeight(300);
        primaryStage.show();
    }

    static Tab tab(String labelText) {
        Tab tab = new Tab();
        Label label = new Label(labelText);
        label.setRotate(90);
        StackPane stackPane = new StackPane(label);
        stackPane.setMinWidth(70);
        stackPane.setMaxHeight(30);
        tab.setGraphic(stackPane);
        return tab;
    }
}
