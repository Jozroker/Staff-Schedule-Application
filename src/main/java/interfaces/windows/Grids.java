package interfaces.windows;

import interfaces.creation.*;
import interfaces.error.ErrorWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.dto.*;
import server.service.*;
import server.service.impl.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class Grids {

    private static final PositionService positionService =
            PositionServiceImpl.getInstance();
    private static final LeaderService leaderService =
            LeaderServiceImpl.getInstance();
    private static final UnitService unitService =
            UnitServiceImpl.getInstance();
    private static final WorkerService workerService =
            WorkerServiceImpl.getInstance();
    private static final ShiftService shiftService =
            ShiftServiceImpl.getInstance();
    private static Font titleFont;
    private static Font regularFont;
    private static boolean failedFontsStatus = false;

    public static GridPane getShiftGridPane(Stage primaryStage,
                                            Tab primaryTab) {
        GridPane shiftGrid = new GridPane();
        shiftGrid.setPadding(new Insets(5, 5, 5, 5));
//        ScrollPane shiftScroll =
//                new ScrollPane(InformationalTable.getShiftTable(primaryStage,
//                        primaryTab));
        TableView<Object> shiftTable =
                InformationalTable.getShiftTable(primaryStage, primaryTab);
        Button addButton = new Button("Додати");

        addButton.setPrefWidth(100);
        addButton.setPrefHeight(18);

        shiftGrid.getColumnConstraints().add(new ColumnConstraints(360));
        shiftGrid.getRowConstraints().addAll(new RowConstraints(220),
                new RowConstraints(30));

        shiftGrid.add(shiftTable, 0, 0);
        shiftGrid.add(addButton, 0, 1);

        GridPane.setHalignment(addButton, HPos.RIGHT);
        addButton.setOnAction(e -> new CreateShiftWindow(primaryStage, primaryTab));

        return shiftGrid;
    }

    public static GridPane getPositionGridPane(Stage primaryStage,
                                               Tab primaryTab,
                                               List<PositionDTO> positions) {
        GridPane positionGrid = new GridPane();
        positionGrid.setPadding(new Insets(5, 5, 5, 5));
//        ScrollPane positionScroll =
//                new ScrollPane(InformationalTable.getPositionTable(primaryStage, positions,
//                        primaryTab));
        TableView<Object> positionTable =
                InformationalTable.getPositionTable(primaryStage, positions,
                        primaryTab);
        Button addButton = new Button("Додати");
        TextField nameField = new TextField();
        Label salaryLabel = new Label("Зарплата від ");
        Label salaryLabel2 = new Label(" до ");
        Spinner<Double> minSalaryField = new Spinner<>(0, Double.MAX_VALUE
                , 0,
                0.01);
        Spinner<Double> maxSalaryField = new Spinner<>(0, Double.MAX_VALUE
                , 0,
                0.01);
        Button findButton = new Button("Знайти");
        Button clearButton = new Button("Очистити");
        HBox salaryBox = new HBox(salaryLabel, minSalaryField, salaryLabel2,
                maxSalaryField);

        minSalaryField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                minSalaryField.increment(0);
            }
        });

        maxSalaryField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                maxSalaryField.increment(0);
            }
        });

        addButton.setPrefWidth(100);
        addButton.setPrefHeight(18);
        findButton.setPrefWidth(80);
        findButton.setPrefHeight(18);
        clearButton.setPrefWidth(80);
        clearButton.setPrefHeight(18);
        nameField.setPromptText("Назва");
        nameField.setMaxWidth(180);
        minSalaryField.setMaxWidth(70);
        maxSalaryField.setMaxWidth(70);
        minSalaryField.setEditable(true);
        maxSalaryField.setEditable(true);

        positionGrid.getColumnConstraints().addAll(new ColumnConstraints(200),
                new ColumnConstraints(150), new ColumnConstraints(100));
        positionGrid.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(160),
                new RowConstraints(30));

        positionGrid.add(nameField, 0, 0, 2, 1);
        positionGrid.add(salaryBox, 0, 1, 2, 1);
        positionGrid.add(findButton, 2, 0);
        positionGrid.add(clearButton, 2, 1);
        positionGrid.add(positionTable, 0, 2, 3, 1);
        positionGrid.add(addButton, 2, 3);

        GridPane.setHalignment(addButton, HPos.RIGHT);
        GridPane.setHalignment(findButton, HPos.RIGHT);
        GridPane.setHalignment(clearButton, HPos.RIGHT);
        addButton.setOnAction(e -> new CreatePositionWindow(primaryStage,
                primaryTab, positions));
        findButton.setOnAction(e -> {
            try {
                List<PositionDTO> filteringPositions = positions;
                if (nameField.getText().equals("")) {
                    filteringPositions =
                            positionService.findBySalary(minSalaryField.getValue(), maxSalaryField.getValue());
                } else {
                    filteringPositions = Collections.singletonList(
                            positionService.findByName(nameField.getText()));
                }
                primaryTab.setContent(Grids.getPositionGridPane(primaryStage,
                        primaryTab, filteringPositions));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });
        clearButton.setOnAction(e -> {
            try {
                nameField.clear();
                minSalaryField.getValueFactory().setValue((double) 0);
                maxSalaryField.getValueFactory().setValue((double) 0);
                primaryTab.setContent(Grids.getPositionGridPane(primaryStage,
                        primaryTab, positionService.findAll()));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });

        return positionGrid;
    }

    public static GridPane getUnitGridPane(Stage primaryStage,
                                           Tab primaryTab,
                                           List<UnitDTO> units) {

        List<Label> leaders = leaderService.findAll().stream().map(e -> {
            Label leader = new Label(e.getFirstName() + " " + e.getLastName());
            leader.setUserData(e);
            return leader;
        }).collect(Collectors.toList());

        List<Label> workers = workerService.findAll().stream().map(e -> {
            Label worker = new Label(e.getFirstName() + " " + e.getLastName());
            worker.setUserData(e);
            return worker;
        }).collect(Collectors.toList());

        GridPane unitGrid = new GridPane();
        unitGrid.setPadding(new Insets(5, 5, 5, 5));
//        ScrollPane unitScroll =
//                new ScrollPane(InformationalTable.getUnitTable(primaryStage,
//                        units,
//                        primaryTab));
        TableView<Object> unitTable =
                InformationalTable.getUnitTable(primaryStage, units,
                        primaryTab);
        Button addButton = new Button("Додати");
        TextField nameField = new TextField();
        ObservableList<Label> leaderList =
                FXCollections.observableList(leaders);
        ComboBox<Label> leaderField = new ComboBox<>(leaderList);
        ObservableList<Label> workerList =
                FXCollections.observableList(workers);
        ComboBox<Label> workerField = new ComboBox<>(workerList);
        Button findButton = new Button("Знайти");
        Button clearButton = new Button("Очистити");

        addButton.setPrefWidth(100);
        addButton.setPrefHeight(18);
        findButton.setPrefWidth(80);
        findButton.setPrefHeight(18);
        clearButton.setPrefWidth(80);
        clearButton.setPrefHeight(18);
        nameField.setPromptText("Назва");
        leaderField.setPromptText("Керівник");
        workerField.setPromptText("Працівник");
        nameField.setMaxWidth(160);
        leaderField.setMaxWidth(130);
        workerField.setMaxWidth(130);

        unitGrid.getColumnConstraints().addAll(new ColumnConstraints(170),
                new ColumnConstraints(145), new ColumnConstraints(145));
        unitGrid.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(160),
                new RowConstraints(30));

        unitGrid.add(nameField, 0, 0);
        unitGrid.add(leaderField, 1, 0);
        unitGrid.add(workerField, 2, 0);
        unitGrid.add(findButton, 1, 1);
        unitGrid.add(clearButton, 2, 1);
        unitGrid.add(unitTable, 0, 2, 3, 1);
        unitGrid.add(addButton, 2, 3);

        GridPane.setHalignment(addButton, HPos.RIGHT);
        addButton.setOnAction(e -> new CreateUnitWindow(primaryStage,
                primaryTab, units));
        findButton.setOnAction(e -> {
            try {
                List<UnitDTO> filteringUnits = units;
                if (nameField.getText().equals("")) {
                    LeaderDTO tempLeader;
                    WorkerDTO tempWorker;
                    if (leaderField.getSelectionModel().getSelectedItem() != null) {
                        tempLeader =
                                (LeaderDTO) leaderField.getSelectionModel().getSelectedItem().getUserData();
                        if (workerField.getSelectionModel().getSelectedItem() != null) {
                            tempWorker =
                                    (WorkerDTO) workerField.getSelectionModel().getSelectedItem().getUserData();
                            filteringUnits =
                                    unitService.findByLeaderAndWorker(tempLeader,
                                            tempWorker);
                        } else {
                            filteringUnits = unitService.findByLeader(tempLeader);
                        }
                    } else {
                        if (workerField.getSelectionModel().getSelectedItem() != null) {
                            tempWorker =
                                    (WorkerDTO) workerField.getSelectionModel().getSelectedItem().getUserData();
                            filteringUnits = unitService.findByWorker(tempWorker);
                        }
                    }
                } else {
                    filteringUnits =
                            Collections.singletonList(unitService.findByName(nameField.getText()));
                }
                primaryTab.setContent(Grids.getUnitGridPane(primaryStage,
                        primaryTab, filteringUnits));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });
        clearButton.setOnAction(e -> {
            try {
                nameField.clear();
                leaderField.getSelectionModel().clearSelection();
                workerField.getSelectionModel().clearSelection();
                primaryTab.setContent(Grids.getUnitGridPane(primaryStage,
                        primaryTab, unitService.findAll()));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });

        return unitGrid;
    }

    public static GridPane getLeaderGridPane(Stage primaryStage,
                                             Tab primaryTab,
                                             List<LeaderDTO> leaders) {
        GridPane leaderGrid = new GridPane();
        leaderGrid.setPadding(new Insets(5, 5, 5, 5));
//        ScrollPane leaderScroll =
//                new ScrollPane(InformationalTable.getLeaderTable(primaryStage, leaders,
//                        primaryTab));
        TableView<Object> leaderTable =
                InformationalTable.getLeaderTable(primaryStage
                        , leaders, primaryTab);
        Button addButton = new Button("Додати");
        TextField lastNameField = new TextField();
        TextField phoneField = new TextField();
        Button findButton = new Button("Знайти");
        Button clearButton = new Button("Очистити");

        addButton.setPrefWidth(100);
        addButton.setPrefHeight(18);
        findButton.setPrefWidth(80);
        findButton.setPrefHeight(18);
        clearButton.setPrefWidth(80);
        clearButton.setPrefHeight(18);
        lastNameField.setMaxWidth(180);
        phoneField.setMaxWidth(180);
        lastNameField.setPromptText("Прізвище");
        phoneField.setPromptText("Номер телефону");

        leaderGrid.getColumnConstraints().addAll(new ColumnConstraints(215),
                new ColumnConstraints(215), new ColumnConstraints(190));
        leaderGrid.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(160),
                new RowConstraints(30));

        leaderGrid.add(lastNameField, 0, 0);
        leaderGrid.add(phoneField, 1, 0);
        leaderGrid.add(findButton, 2, 0);
        leaderGrid.add(clearButton, 2, 1);
        leaderGrid.add(leaderTable, 0, 2, 3, 1);
        leaderGrid.add(addButton, 2, 3);

        GridPane.setHalignment(addButton, HPos.RIGHT);
        GridPane.setHalignment(findButton, HPos.RIGHT);
        GridPane.setHalignment(clearButton, HPos.RIGHT);
        addButton.setOnAction(e -> new CreateLeaderWindow(primaryStage,
                primaryTab, leaders));
        findButton.setOnAction(e -> {
            try {
                List<LeaderDTO> filteringLeader = leaders;
                if (lastNameField.getText().equals("")) {
                    if (!phoneField.getText().equals("")) {
                        filteringLeader =
                                Collections.singletonList(leaderService.findByPhone(phoneField.getText()));
                    }
                } else {
                    filteringLeader =
                            leaderService.findByLastName(lastNameField.getText());
                }
                primaryTab.setContent(Grids.getLeaderGridPane(primaryStage,
                        primaryTab, filteringLeader));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });
        clearButton.setOnAction(e -> {
            try {
                lastNameField.clear();
                phoneField.clear();
                primaryTab.setContent(Grids.getLeaderGridPane(primaryStage,
                        primaryTab, leaderService.findAll()));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });

        return leaderGrid;
    }

    public static GridPane getWorkerGridPane(Stage primaryStage,
                                             Tab primaryTab,
                                             List<WorkerDTO> workers) {

        List<Label> shifts = shiftService.findAll().stream().map(e -> {
            Label shift = new Label(e.getName());
            shift.setUserData(e);
            return shift;
        }).collect(Collectors.toList());

        List<Label> units = unitService.findAll().stream().map(e -> {
            Label unit = new Label(e.getName());
            unit.setUserData(e);
            return unit;
        }).collect(Collectors.toList());

        List<Label> positions = positionService.findAll().stream().map(e -> {
            Label position = new Label(e.getName());
            position.setUserData(e);
            return position;
        }).collect(Collectors.toList());

        GridPane workerGrid = new GridPane();
        workerGrid.setPadding(new Insets(5, 5, 5, 5));
//        ScrollPane workerScroll =
//                new ScrollPane(InformationalTable.getWorkerTable(primaryStage,
//                        workers,
//                        primaryTab));
        TableView<Object> workerTable =
                InformationalTable.getWorkerTable(primaryStage, workers,
                        primaryTab);
        Button addButton = new Button("Додати");
        TextField lastNameField = new TextField();
        TextField phoneField = new TextField();
        ObservableList<Label> shiftList =
                FXCollections.observableList(shifts);
        ComboBox<Label> shiftField = new ComboBox<>(shiftList);
        ObservableList<Label> unitList =
                FXCollections.observableList(units);
        ComboBox<Label> unitField = new ComboBox<>(unitList);
        ObservableList<Label> positionList =
                FXCollections.observableList(positions);
        ComboBox<Label> positionField = new ComboBox<>(positionList);
        Button findButton = new Button("Знайти");
        Button clearButton = new Button("Очистити");

        Label stageLabel1 = new Label("Стаж від ");
        Label stageLabel2 = new Label(" до ");
        Spinner<Integer> stageSpinner1 = new Spinner<>(0, 100, 0);
        Spinner<Integer> stageSpinner2 = new Spinner<>(0, 100, 0);
        HBox stageField = new HBox(stageLabel1, stageSpinner1, stageLabel2,
                stageSpinner2);

        stageSpinner1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                stageSpinner1.increment(0);
            }
        });

        stageSpinner2.focusedProperty().addListener((observable, oldValue,
                                                     newValue) -> {
            if (!newValue) {
                stageSpinner2.increment(0);
            }
        });

        addButton.setPrefWidth(100);
        addButton.setPrefHeight(18);
        findButton.setPrefWidth(80);
        findButton.setPrefHeight(18);
        clearButton.setPrefWidth(80);
        clearButton.setPrefHeight(18);
        lastNameField.setPromptText("Прізвище");
        phoneField.setPromptText("Номер телефону");
        shiftField.setPromptText("Зміна");
        unitField.setPromptText("Підрозділ");
        positionField.setPromptText("Посада");
        lastNameField.setMaxWidth(170);
        phoneField.setMaxWidth(180);
        shiftField.setMaxWidth(130);
        positionField.setMaxWidth(130);
        unitField.setMaxWidth(130);
        stageSpinner1.setMaxWidth(70);
        stageSpinner2.setMaxWidth(70);
        stageSpinner1.setEditable(true);
        stageSpinner2.setEditable(true);

        workerGrid.getColumnConstraints().addAll(new ColumnConstraints(180),
                new ColumnConstraints(110), new ColumnConstraints(150),
                new ColumnConstraints(150));
        workerGrid.getRowConstraints().addAll(new RowConstraints(30),
                new RowConstraints(30), new RowConstraints(30),
                new RowConstraints(130), new RowConstraints(30));

        workerGrid.add(lastNameField, 0, 0);
        workerGrid.add(phoneField, 1, 0, 2, 1);
        workerGrid.add(shiftField, 3, 0);
        workerGrid.add(stageField, 0, 1, 2, 1);
        workerGrid.add(unitField, 2, 1);
        workerGrid.add(positionField, 3, 1);
        workerGrid.add(findButton, 2, 2, 2, 1);
        workerGrid.add(clearButton, 3, 2);
        workerGrid.add(workerTable, 0, 3, 4, 1);
        workerGrid.add(addButton, 3, 4);

        GridPane.setHalignment(addButton, HPos.RIGHT);
        addButton.setOnAction(e -> new CreateWorkerWindow(primaryStage,
                primaryTab, workers));
        findButton.setOnAction(e -> {
            try {
                List<WorkerDTO> filteringWorkers = workers;
                if (!lastNameField.getText().equals("")) {
                    filteringWorkers = workerService.findByLastName(lastNameField.getText());
                } else if (!phoneField.getText().equals("")) {
                    filteringWorkers = Collections.singletonList(
                            workerService.findByPhone(phoneField.getText()));
                } else {
                    ShiftDTO tempShift = null;
                    UnitDTO tempUnit = null;
                    PositionDTO tempPosition = null;
                    try {
                        tempPosition =
                                (PositionDTO) positionField.getSelectionModel().getSelectedItem().getUserData();
                    } catch (Exception ignore) {
                    }
                    try {
                        tempUnit =
                                (UnitDTO) unitField.getSelectionModel().getSelectedItem().getUserData();
                    } catch (Exception ignore) {
                    }
                    try {
                        tempShift =
                                (ShiftDTO) shiftField.getSelectionModel().getSelectedItem().getUserData();
                    } catch (Exception ignore) {
                    }
                    int minStage = stageSpinner1.getValue();
                    int maxStage = stageSpinner2.getValue();
                    filteringWorkers =
                            workerService.findByParameters(tempShift, minStage,
                                    maxStage, tempUnit, tempPosition);
                }

                primaryTab.setContent(Grids.getWorkerGridPane(primaryStage,
                        primaryTab, filteringWorkers));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });
        clearButton.setOnAction(e -> {
            try {
                lastNameField.clear();
                phoneField.clear();
                shiftField.getSelectionModel().clearSelection();
                positionField.getSelectionModel().clearSelection();
                unitField.getSelectionModel().clearSelection();
                stageSpinner1.getValueFactory().setValue(0);
                stageSpinner2.getValueFactory().setValue(0);
                primaryTab.setContent(Grids.getWorkerGridPane(primaryStage,
                        primaryTab, workerService.findAll()));
            } catch (Exception ex) {
                new ErrorWindow(primaryStage, ex.getMessage());
            }
        });

        return workerGrid;
    }

    public static List<Font> getFonts(Stage mainStage) {
        try {
            if (titleFont == null) {
                titleFont = Font.loadFont("file:src/main/resources/font" +
                        "/Nunito-Bold.ttf", 16);
            }
            if (regularFont == null) {
                regularFont = Font.loadFont("file:src/main/resources/font" +
                                "/Nunito" +
                                "-Regular.ttf",
                        14);
            }
        } catch (Exception e) {
            if (!failedFontsStatus) {
                failedFontsStatus = true;
                new ErrorWindow(mainStage, "Додаткові шрифти не знайдено");
            }
        }
        return Arrays.asList(titleFont, regularFont);
    }
}
