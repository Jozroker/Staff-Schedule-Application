package interfaces.windows;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import interfaces.changing.*;
import interfaces.reading.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.dto.*;
import server.service.*;
import server.service.impl.*;

import java.util.List;
import java.util.stream.Collectors;

public final class InformationalTable {

    private static final ShiftService shiftService =
            ShiftServiceImpl.getInstance();
    private static final PositionService positionService =
            PositionServiceImpl.getInstance();
    private static final LeaderService leaderService =
            LeaderServiceImpl.getInstance();
    private static final UnitService unitService =
            UnitServiceImpl.getInstance();
    private static final WorkerService workerService =
            WorkerServiceImpl.getInstance();

    public static TableView<Object> getShiftTable(Stage mainStage, Tab primaryTab) {
        List<ShiftDTO> shifts = shiftService.findAll();
        shifts.forEach(e -> {
            e.getChangeBtn().setOnAction(c -> changeAction(mainStage, e, null
                    , primaryTab));
            e.getDeleteBtn().setOnAction(c -> deleteAction(mainStage, e, null
                    , primaryTab));
            ImageView icon = new ImageView("image/cross.png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            e.getDeleteBtn().setGraphic(icon);
            e.setSequenceNumber(shifts.indexOf(e) + 1);
        });
        ObservableList<Object> shiftList =
                FXCollections.observableList(shifts.stream()
                        .map(e -> (Object) e).collect(Collectors.toList()));
        TableView<Object> table = new TableView<>(shiftList);

        TableColumn<Object, String> numColumn = new TableColumn<>("№");
        TableColumn<Object, String> nameColumn = new TableColumn<>("Назва");
        TableColumn<Object, String> startColumn = new TableColumn<>("Початок");
        TableColumn<Object, String> endColumn = new TableColumn<>("Кінець");
        TableColumn<Object, String> changeColumn = new TableColumn<>();
        TableColumn<Object, String> deleteColumn = new TableColumn<>();

        numColumn.setCellValueFactory(new PropertyValueFactory<>(
                "sequenceNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(((ShiftDTO) p.getValue())
                .getBeginTime().toString().substring(0, 5)));
        endColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(((ShiftDTO) p.getValue())
                .getEndTime().toString().substring(0, 5)));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "changeBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(
                "deleteBtn"));

        numColumn.setSortType(TableColumn.SortType.DESCENDING);
        nameColumn.setSortType(TableColumn.SortType.DESCENDING);
        startColumn.setSortType(TableColumn.SortType.DESCENDING);
        endColumn.setSortType(TableColumn.SortType.DESCENDING);
        changeColumn.setSortable(false);
        deleteColumn.setSortable(false);
        numColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(80);
        startColumn.setPrefWidth(60);
        endColumn.setPrefWidth(60);
        changeColumn.setPrefWidth(70);
        deleteColumn.setPrefWidth(45);

        table.getColumns().add(numColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(startColumn);
        table.getColumns().add(endColumn);
        table.getColumns().add(changeColumn);
        table.getColumns().add(deleteColumn);
        table.setPrefWidth(345);

        table.getColumns().forEach(e -> e.setResizable(false));
        table.setStyle("-fx-selection-bar: salmon;");
        table.setPlaceholder(new Label());

        table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup(
                    "TableHeaderRow");
            try {
                header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                        header.setReordering(false));
            } catch (Exception ignore) {
            }
        });

        table.setOnMouseClicked(e -> {
            readAction(mainStage, table.getSelectionModel().getSelectedItem());
            table.getSelectionModel().clearSelection();
        });

        return table;
    }

    public static TableView<Object> getPositionTable(Stage mainStage,
                                                     List<PositionDTO> positions, Tab primaryTab) {
        positions.forEach(e -> {
            e.getChangeBtn().setOnAction(c -> changeAction(mainStage, e,
                    positions, primaryTab));
            e.getDeleteBtn().setOnAction(c -> deleteAction(mainStage, e,
                    positions, primaryTab));
            ImageView icon = new ImageView("image/cross.png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            e.getDeleteBtn().setGraphic(icon);
            e.setSequenceNumber(positions.indexOf(e) + 1);
        });
        ObservableList<Object> positionList =
                FXCollections.observableList(positions.stream()
                        .map(e -> (Object) e).collect(Collectors.toList()));
        TableView<Object> table = new TableView<>(positionList);

        TableColumn<Object, String> numColumn = new TableColumn<>("№");
        TableColumn<Object, String> nameColumn = new TableColumn<>("Назва");
        TableColumn<Object, String> salaryColumn = new TableColumn<>(
                "Зарплата");
        TableColumn<Object, String> allowanceColumn = new TableColumn<>(
                "Надбавка");
        TableColumn<Object, String> changeColumn = new TableColumn<>();
        TableColumn<Object, String> deleteColumn = new TableColumn<>();

        numColumn.setCellValueFactory(new PropertyValueFactory<>(
                "sequenceNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        allowanceColumn.setCellValueFactory(new PropertyValueFactory<>("allowance"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "changeBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(
                "deleteBtn"));

        numColumn.setSortType(TableColumn.SortType.DESCENDING);
        nameColumn.setSortType(TableColumn.SortType.DESCENDING);
        salaryColumn.setSortType(TableColumn.SortType.DESCENDING);
        allowanceColumn.setSortType(TableColumn.SortType.DESCENDING);
        changeColumn.setSortable(false);
        deleteColumn.setSortable(false);
        numColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(150);
        salaryColumn.setPrefWidth(70);
        allowanceColumn.setPrefWidth(70);
        changeColumn.setPrefWidth(70);
        deleteColumn.setPrefWidth(45);

        table.getColumns().add(numColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(salaryColumn);
        table.getColumns().add(allowanceColumn);
        table.getColumns().add(changeColumn);
        table.getColumns().add(deleteColumn);
        table.setPrefWidth(435);

        table.getColumns().forEach(e -> e.setResizable(false));
        table.setStyle("-fx-selection-bar: salmon;");
        table.setPlaceholder(new Label());

        table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup(
                    "TableHeaderRow");
            try {
                header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                        header.setReordering(false));
            } catch (Exception ignore) {
            }
        });

        table.setOnMouseClicked(e -> {
            readAction(mainStage, table.getSelectionModel().getSelectedItem());
            table.getSelectionModel().clearSelection();
        });

        return table;
    }

    public static TableView<Object> getUnitTable(Stage mainStage,
                                                 List<UnitDTO> units, Tab primaryTab) {
        units.forEach(e -> {
            e.getChangeBtn().setOnAction(c -> changeAction(mainStage, e,
                    units, primaryTab));
            e.getDeleteBtn().setOnAction(c -> deleteAction(mainStage, e,
                    units, primaryTab));
            ImageView icon = new ImageView("image/cross.png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            e.getDeleteBtn().setGraphic(icon);
            e.setSequenceNumber(units.indexOf(e) + 1);
        });
        ObservableList<Object> unitList =
                FXCollections.observableList(units.stream()
                        .map(e -> (Object) e).collect(Collectors.toList()));
        TableView<Object> table = new TableView<>(unitList);

        TableColumn<Object, String> numColumn = new TableColumn<>("№");
        TableColumn<Object, String> nameColumn = new TableColumn<>("Назва");
        TableColumn<Object, String> workerQtyColumn = new TableColumn<>(
                "Кількість осіб");
        TableColumn<Object, String> changeColumn = new TableColumn<>();
        TableColumn<Object, String> deleteColumn = new TableColumn<>();

        numColumn.setCellValueFactory(new PropertyValueFactory<>(
                "sequenceNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerQtyColumn.setCellValueFactory(new PropertyValueFactory<>("workerQty"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "changeBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(
                "deleteBtn"));

        numColumn.setSortType(TableColumn.SortType.DESCENDING);
        nameColumn.setSortType(TableColumn.SortType.DESCENDING);
        workerQtyColumn.setSortType(TableColumn.SortType.DESCENDING);
        changeColumn.setSortable(false);
        deleteColumn.setSortable(false);
        numColumn.setPrefWidth(30);
        nameColumn.setPrefWidth(150);
        workerQtyColumn.setPrefWidth(150);
        changeColumn.setPrefWidth(70);
        deleteColumn.setPrefWidth(45);

        table.getColumns().add(numColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(workerQtyColumn);
        table.getColumns().add(changeColumn);
        table.getColumns().add(deleteColumn);
        table.setPrefWidth(445);

        table.getColumns().forEach(e -> e.setResizable(false));
        table.setStyle("-fx-selection-bar: salmon;");
        table.setPlaceholder(new Label());

        table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup(
                    "TableHeaderRow");
            try {
                header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                        header.setReordering(false));
            } catch (Exception ignore) {
            }
        });

        table.setOnMouseClicked(e -> {
            readAction(mainStage, table.getSelectionModel().getSelectedItem());
            table.getSelectionModel().clearSelection();
        });

        return table;
    }

    public static TableView<Object> getWorkerTable(Stage mainStage,
                                                   List<WorkerDTO> workers,
                                                   Tab primaryTab) {
        workers.forEach(e -> {
            e.getChangeBtn().setOnAction(c -> changeAction(mainStage, e,
                    workers, primaryTab));
            e.getDeleteBtn().setOnAction(c -> deleteAction(mainStage, e,
                    workers, primaryTab));
            ImageView icon = new ImageView("image/cross.png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            e.getDeleteBtn().setGraphic(icon);
            e.setSequenceNumber(workers.indexOf(e) + 1);
        });
        ObservableList<Object> workerList =
                FXCollections.observableList(workers.stream()
                        .map(e -> (Object) e).collect(Collectors.toList()));
        TableView<Object> table = new TableView<>(workerList);

        TableColumn<Object, String> numColumn = new TableColumn<>("№");
        TableColumn<Object, String> firstNameColumn = new TableColumn<>(
                "Ім'я");
        TableColumn<Object, String> lastNameColumn = new TableColumn<>(
                "Прізвище");
        TableColumn<Object, String> birthdayColumn = new TableColumn<>(
                "Дата\n" +
                        "народження");
        TableColumn<Object, String> phoneColumn = new TableColumn<>("Номер\n" +
                "телефону");
        TableColumn<Object, String> stageColumn = new TableColumn<>("Стаж");
        TableColumn<Object, String> changeColumn = new TableColumn<>();
        TableColumn<Object, String> deleteColumn = new TableColumn<>();

        numColumn.setCellValueFactory(new PropertyValueFactory<>(
                "sequenceNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "lastName"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>(
                "birthdayDate"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>(
                "phoneNumber"));
        stageColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "changeBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(
                "deleteBtn"));

        numColumn.setSortType(TableColumn.SortType.DESCENDING);
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        lastNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        birthdayColumn.setSortType(TableColumn.SortType.DESCENDING);
        stageColumn.setSortType(TableColumn.SortType.DESCENDING);
        phoneColumn.setSortable(false);
        changeColumn.setSortable(false);
        deleteColumn.setSortable(false);
        numColumn.setPrefWidth(30);
        firstNameColumn.setPrefWidth(100);
        lastNameColumn.setPrefWidth(100);
        birthdayColumn.setPrefWidth(80);
        phoneColumn.setPrefWidth(100);
        stageColumn.setPrefWidth(50);
        changeColumn.setPrefWidth(70);
        deleteColumn.setPrefWidth(45);

        table.getColumns().add(numColumn);
        table.getColumns().add(firstNameColumn);
        table.getColumns().add(lastNameColumn);
        table.getColumns().add(birthdayColumn);
        table.getColumns().add(phoneColumn);
        table.getColumns().add(stageColumn);
        table.getColumns().add(changeColumn);
        table.getColumns().add(deleteColumn);
        table.setPrefWidth(575);

        table.getColumns().forEach(e -> e.setResizable(false));
        table.setStyle("-fx-selection-bar: salmon;");
        table.setPlaceholder(new Label());

        table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup(
                    "TableHeaderRow");
            try {
                header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                        header.setReordering(false));
            } catch (Exception ignore) {
            }
        });

        table.setOnMouseClicked(e -> {
            readAction(mainStage, table.getSelectionModel().getSelectedItem());
            table.getSelectionModel().clearSelection();
        });

        return table;
    }

    public static TableView<Object> getLeaderTable(Stage mainStage,
                                                   List<LeaderDTO> leaders,
                                                   Tab primaryTab) {
        leaders.forEach(e -> {
            e.getChangeBtn().setOnAction(c -> changeAction(mainStage, e,
                    leaders, primaryTab));
            e.getDeleteBtn().setOnAction(c -> deleteAction(mainStage, e,
                    leaders, primaryTab));
            ImageView icon = new ImageView("image/cross.png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            e.getDeleteBtn().setGraphic(icon);
            e.setSequenceNumber(leaders.indexOf(e) + 1);
        });
        ObservableList<Object> leaderList =
                FXCollections.observableList(leaders.stream()
                        .map(e -> (Object) e).collect(Collectors.toList()));
        TableView<Object> table = new TableView<>(leaderList);

        TableColumn<Object, String> numColumn = new TableColumn<>("№");
        TableColumn<Object, String> firstNameColumn = new TableColumn<>(
                "Ім'я");
        TableColumn<Object, String> lastNameColumn = new TableColumn<>(
                "Прізвище");
        TableColumn<Object, String> fatherNameColumn = new TableColumn<>(
                "По батькові");
        TableColumn<Object, String> birthdayColumn = new TableColumn<>(
                "Дата \n" +
                        "народження");
        TableColumn<Object, String> phoneColumn = new TableColumn<>("Номер\n" +
                "телефону");
        TableColumn<Object, String> changeColumn = new TableColumn<>();
        TableColumn<Object, String> deleteColumn = new TableColumn<>();

        numColumn.setCellValueFactory(new PropertyValueFactory<>(
                "sequenceNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "lastName"));
        fatherNameColumn.setCellValueFactory(new PropertyValueFactory<>(
                "fatherName"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>(
                "birthdayDate"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>(
                "phone"));
        changeColumn.setCellValueFactory(new PropertyValueFactory<>(
                "changeBtn"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(
                "deleteBtn"));

        numColumn.setSortType(TableColumn.SortType.DESCENDING);
        firstNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        lastNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        fatherNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        birthdayColumn.setSortType(TableColumn.SortType.DESCENDING);
        phoneColumn.setSortable(false);
        changeColumn.setSortable(false);
        deleteColumn.setSortable(false);
        numColumn.setPrefWidth(30);
        firstNameColumn.setPrefWidth(100);
        lastNameColumn.setPrefWidth(100);
        fatherNameColumn.setPrefWidth(100);
        birthdayColumn.setPrefWidth(80);
        phoneColumn.setPrefWidth(80);
        changeColumn.setPrefWidth(70);
        deleteColumn.setPrefWidth(45);

        table.getColumns().add(numColumn);
        table.getColumns().add(firstNameColumn);
        table.getColumns().add(lastNameColumn);
        table.getColumns().add(fatherNameColumn);
        table.getColumns().add(birthdayColumn);
        table.getColumns().add(phoneColumn);
        table.getColumns().add(changeColumn);
        table.getColumns().add(deleteColumn);
        table.setPrefWidth(605);

        table.getColumns().forEach(e -> e.setResizable(false));
        table.setStyle("-fx-selection-bar: salmon;");
        table.setPlaceholder(new Label());

        table.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) table.lookup(
                    "TableHeaderRow");
            try {
                header.reorderingProperty().addListener((observable, oldValue, newValue) ->
                        header.setReordering(false));
            } catch (Exception ignore) {
            }
        });

        table.setOnMouseClicked(e -> {
            readAction(mainStage, table.getSelectionModel().getSelectedItem());
            table.getSelectionModel().clearSelection();
        });

        return table;
    }


    private static void changeAction(Stage mainStage, Object obj,
                                     List<?> objects, Tab primaryTab) {
        String className = "";
        try {
            className = obj.getClass().getSimpleName();
        } catch (NullPointerException ignore) {
        }
        List<?> list;
        switch (className) {
            case "ShiftDTO":
                new ChangeShiftWindow(mainStage, (ShiftDTO) obj, primaryTab);
                break;
            case "PositionDTO":
                list =
                        objects.stream().map(e -> (PositionDTO) e).collect(Collectors.toList());
                new ChangePositionWindow(mainStage, (PositionDTO) obj,
                        (List<PositionDTO>) list, primaryTab);
                break;
            case "LeaderDTO":
                list =
                        objects.stream().map(e -> (LeaderDTO) e).collect(Collectors.toList());
                new ChangeLeaderWindow(mainStage, (LeaderDTO) obj,
                        (List<LeaderDTO>) list, primaryTab);
                break;
            case "UnitDTO":
                list =
                        objects.stream().map(e -> (UnitDTO) e).collect(Collectors.toList());
                new ChangeUnitWindow(mainStage, (UnitDTO) obj,
                        (List<UnitDTO>) list, primaryTab);
                break;
            case "WorkerDTO":
                list =
                        objects.stream().map(e -> (WorkerDTO) e).collect(Collectors.toList());
                new ChangeWorkerWindow(mainStage, (WorkerDTO) obj,
                        (List<WorkerDTO>) list, primaryTab);
                break;
        }
    }

    private static void deleteAction(Stage mainStage, Object obj,
                                     List<?> objects, Tab primaryTab) {
        String className = "";
        try {
            className = obj.getClass().getSimpleName();
        } catch (NullPointerException ignore) {
        }
        List<?> list;
//        TableView<?> newTable = new TableView<>();
        switch (className) {
            case "ShiftDTO":
                shiftService.delete((ShiftDTO) obj);
//                newTable = getShiftTable(mainStage);
                primaryTab.setContent(Grids.getShiftGridPane(mainStage, primaryTab));
                break;
            case "PositionDTO":
                objects.remove(obj);
                list =
                        objects.stream().map(e -> (PositionDTO) e).collect(Collectors.toList());
                positionService.delete((PositionDTO) obj);
//                newTable = getPositionTable(mainStage, (List<PositionDTO>) list);
                primaryTab.setContent(Grids.getPositionGridPane(mainStage,
                        primaryTab, (List<PositionDTO>) list));
                break;
            case "LeaderDTO":
                objects.remove(obj);
                list =
                        objects.stream().map(e -> (LeaderDTO) e).collect(Collectors.toList());
                leaderService.delete((LeaderDTO) obj);
//                newTable = getLeaderTable(mainStage, (List<LeaderDTO>) list);
                primaryTab.setContent(Grids.getLeaderGridPane(mainStage,
                        primaryTab, (List<LeaderDTO>) list));
                break;
            case "UnitDTO":
                objects.remove(obj);
                list =
                        objects.stream().map(e -> (UnitDTO) e).collect(Collectors.toList());
                unitService.delete((UnitDTO) obj);
//                newTable = getUnitTable(mainStage, (List<UnitDTO>) list);
                primaryTab.setContent(Grids.getUnitGridPane(mainStage,
                        primaryTab, (List<UnitDTO>) list));
                break;
            case "WorkerDTO":
                objects.remove(obj);
                list =
                        objects.stream().map(e -> (WorkerDTO) e).collect(Collectors.toList());
                workerService.delete((WorkerDTO) obj);
//                newTable = getWorkerTable(mainStage, (List<WorkerDTO>) list);
                primaryTab.setContent(Grids.getWorkerGridPane(mainStage,
                        primaryTab, (List<WorkerDTO>) list));
                break;
        }
//        mainStage.setScene(new Scene(newTable));
    }

    private static void readAction(Stage mainStage, Object obj) {
        String className = "";
        try {
            className = obj.getClass().getSimpleName();
        } catch (NullPointerException ignore) {
        }
        switch (className) {
            case "ShiftDTO":
                new ReadShiftWindow(mainStage, (ShiftDTO) obj);
                break;
            case "PositionDTO":
                new ReadPositionWindow(mainStage, (PositionDTO) obj);
                break;
            case "LeaderDTO":
                new ReadLeaderWindow(mainStage, (LeaderDTO) obj);
                break;
            case "UnitDTO":
                new ReadUnitWindow(mainStage, (UnitDTO) obj);
                break;
            case "WorkerDTO":
                new ReadWorkerWindow(mainStage, (WorkerDTO) obj);
                break;
        }
    }
}
