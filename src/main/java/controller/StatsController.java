package controller;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import model.game.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.database.DatabaseFilters;
import tuple.Triple;
import user.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**
 * controller for updating stats view.
 *
 */
public class StatsController extends AbstractStatsController implements Initializable {
    private final ControllerUtils contr = new ControllerUtils();
    @FXML
    private TextField txtFieldName = new TextField();
    @FXML
    private TextArea txtAreaStats = new TextArea();
    @FXML
    private TableColumn<User, String> firstPlayer;
    @FXML
    private TableColumn<User, String> secondPlayer;
    @FXML
    private TableColumn<LocalDate, String> date;
    @FXML
    private TableView<Triple<User, User, LocalDate>> tableView = new TableView<>();


    @FXML
    public void showHelp() {
        contr.showCompleteAlert("Guide", "DATABASE GUIDE", helpMessage(), INFORMATION);
    }
    private void showStats() {
        txtAreaStats.setText(getStats(txtFieldName.getText()));
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        database = new DatabaseFilters(wrappedRead());
        showStats();
        initializeTriple();
        initializeTxtField();
        initializeTable();
    }

    private void initializeTriple() {
        firstPlayer.setCellValueFactory(new PropertyValueFactory<>("x"));
        secondPlayer.setCellValueFactory(new PropertyValueFactory<>("y"));
        date.setCellValueFactory(new PropertyValueFactory<>("z"));
    }

    private void initializeTxtField() {
        txtFieldName.textProperty()
                .addListener((observableValue, s, s2) -> tableView.setItems(observableList(filterByName(s2))));
        txtFieldName.textProperty()
                .addListener((observableValue, s, s2) -> showStats());
    }

    private void initializeTable() {
        tableView.setItems(observableList(x -> true));
        tableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> txtAreaStats.setText(getWinner(newSelection)));
        tableView.setRowFactory(tableView2 -> addDeselectionRowEvent());
    }

    private List<Game> wrappedRead() {
        return handleRead(
                () -> contr.showCompleteAlert(
                        "Something went wrong", "error!", "unable to read database", ERROR));
    }
    private Predicate<Game> filterByName(final String s2) {
        return x -> x.getUsers().getX().getName().contains(s2) || x.getUsers().getY().getName().contains(s2);
    }
    private ObservableList<Triple<User, User, LocalDate>> observableList(final Predicate<Game> predicate) {
        return  FXCollections.observableArrayList(database.getTripleFromGame(predicate));
    }

    private TableRow<Triple<User, User, LocalDate>> addDeselectionRowEvent() {
        final TableRow<Triple<User, User, LocalDate>> row = new TableRow<>();
        row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> deselectRow(row, event));
        row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> showStats());
        return row;
    }

    private void deselectRow(final TableRow<Triple<User, User, LocalDate>> row, final MouseEvent event) {
        final int index = row.getIndex();
        if (index >= 0 && index < tableView.getItems().size() && tableView.getSelectionModel().isSelected(index)) {
            tableView.getSelectionModel().clearSelection();
            event.consume();
            txtAreaStats.setText("");
        }
    }

    @FXML
    private void backToMainMenu(final Event event) {
        contr.backToMenu(event);
    }

}
