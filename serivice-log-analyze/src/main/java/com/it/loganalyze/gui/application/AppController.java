package com.it.loganalyze.gui.application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.it.loganalyze.App;
import com.it.loganalyze.log.Log;
import com.it.loganalyze.log.LogData;
import com.it.loganalyze.util.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppController {
	private ArrayList<Button> btns = new ArrayList<>();
	private String whatLog;
	private TabPane tabPane = new TabPane();
	private Tab tab1 = new Tab();
	private Tab tab2 = new Tab();

	private LogData modsecurityAuditLog = App.getModSecurityAuditLog();
	private LogData modsecurityDebugLog = App.getModSecurityDebugLog();
	private LogData iptablesLogData = App.getIptablesLogData();
	private LogData apacheAccess = App.getApacheAccess();
	private LogData apacheError = App.getApacheError();
	private ShowLogTable apacheAccessLogTable = new ShowLogTable(apacheAccess, apacheAccess.getMainKeys());
	private ShowLogTable apacheErrorLogTable = new ShowLogTable(apacheError, apacheError.getMainKeys());
	private ShowLogTable iptablesLogTable = new ShowLogTable(iptablesLogData, iptablesLogData.getMainKeys());
	private ShowLogTable auditLogTable = new ShowLogTable(modsecurityAuditLog, modsecurityAuditLog.getMainKeys());
	private ShowLogTable debugLogTable = new ShowLogTable(modsecurityDebugLog, modsecurityDebugLog.getMainKeys());
	@FXML
	private StackPane infoPane;

	@FXML
	private VBox apacheBox;

	@FXML
	private Button apacheGraphBtn;

	@FXML
	private Label apacheLabel;

	@FXML
	private Button apacheTableBtn;

	@FXML
	private DatePicker dateSearch;

	@FXML
	private Label dashboardLabel;

	@FXML
	private ScrollPane detailInfoPane;

	@FXML
	private TextField ipSearch;

	@FXML
	private VBox iptablesBox;

	@FXML
	private Button iptablesGraphBtn;

	@FXML
	private Label iptablesLabel;

	@FXML
	private Button iptablesTableBtn;

	@FXML
	private VBox modsecBox;

	@FXML
	private Button modsecGraphBtn;

	@FXML
	private Label modsecLabel;

	@FXML
	private Button modsecTableBtn;

	@FXML
	private FlowPane searchBar;

	@FXML
	private Button searchBtn;

	@FXML
	void apacheGraphBtnPressed(ActionEvent event) {
		if (searchBar.isVisible()) {
			searchBar.setVisible(false);
		}
		displaySelectedBtn(apacheGraphBtn);

		// Create the charts
		ShowLogCharts showLogChartsAccess = new ShowLogCharts(apacheAccess, "Ip_address", "Timestamp");
		HBox hboxAccess = showLogChartsAccess.createCharts();

		ShowLogCharts showLogChartsError = new ShowLogCharts(apacheError, "Client_Ip", "Timestamp");
		HBox hboxError = showLogChartsError.createCharts();

		// Create a TabPane to hold both charts
		TabPane tabPane = new TabPane();
		Tab accessTab = new Tab("Access");
		accessTab.setContent(hboxAccess);
		Tab errorTab = new Tab("Error");
		errorTab.setContent(hboxError);
		tabPane.getTabs().addAll(accessTab, errorTab);

		// Display the charts in the infoPane
		infoPane.getChildren().set(0, tabPane);
	}

	@FXML
	void iptablesGraphBtnPressed(ActionEvent event) {
		if (searchBar.isVisible()) {
			searchBar.setVisible(false);
		}
		displaySelectedBtn(iptablesGraphBtn);

		// Create the charts
		ShowLogChartsForIPTables showLogCharts = new ShowLogChartsForIPTables(
				"src/main/resources/com/it/loganalyze/log/log_1.json", "Source ip address (SRC)", "Date");
		HBox hbox = showLogCharts.createCharts();

		// Display the charts in the infoPane
		infoPane.getChildren().set(0, hbox);
	}

	@FXML
	void modsecGraphBtnPressed(ActionEvent event) {
		if (searchBar.isVisible()) {
			searchBar.setVisible(false);
		}
		displaySelectedBtn(modsecGraphBtn);

		// Create the audit chart
		ShowLogCharts showLogChartsAudit = new ShowLogCharts(modsecurityAuditLog, "remote_address", "Timestamp");
		HBox hboxAudit = showLogChartsAudit.createCharts();

		// Create the debug chart
		// ShowLogCharts showLogChartsDebug = new ShowLogCharts(modsecurityDebugLog,
		// "id", "Time");
		// HBox hboxDebug = showLogChartsDebug.createCharts();

		// Create a TabPane to hold both charts
		TabPane tabPane = new TabPane();
		Tab auditTab = new Tab("Audit");
		auditTab.setContent(hboxAudit);
		// Tab debugTab = new Tab("Debug");
		// debugTab.setContent(hboxDebug);
		tabPane.getTabs().addAll(auditTab);

		// Display the charts in the infoPane
		infoPane.getChildren().set(0, tabPane);
	}

	@FXML
	void apacheLabelClick(MouseEvent event) {
		whatLog = "apache";
		showButton(0);
	}

	@FXML
	void apacheTablePressed(ActionEvent event) {
		if (!searchBar.isVisible()) {
			searchBar.setVisible(true);
		}
		displaySelectedBtn(apacheTableBtn);

		try {
			tab1.setText("Access.log");
			tab2.setText("Error.log");
			tab1.setContent(attachDetail(apacheAccessLogTable.geTableView()));
			tab2.setContent(attachDetail(apacheErrorLogTable.geTableView()));
		} catch (IOException e) {
			System.err.println("Error while creating table");
		}

		showTable(tabPane);
	}

	@FXML
	void dashboardClick(MouseEvent event) {
		if (searchBar.isVisible()) {
			searchBar.setVisible(false);
		}
		showButton(6);

	}

	@FXML
	void iptablesLabelClick(MouseEvent event) {
		whatLog = "iptables";
		showButton(2);
	}

	@FXML
	void iptablesTablePressed(ActionEvent event) {
		if (!searchBar.isVisible()) {
			searchBar.setVisible(true);
		}
		displaySelectedBtn(iptablesTableBtn);

		try {
			showTable(attachDetail(iptablesLogTable.geTableView()));
		} catch (IOException e) {
			System.err.println("Error while creating table");
		}
	}

	@FXML
	void modsecLabelClick(MouseEvent event) {
		whatLog = "modsec";
		showButton(4);
	}

	@FXML
	void modsecTablePressed(ActionEvent event) {
		if (!searchBar.isVisible()) {
			searchBar.setVisible(true);
		}
		displaySelectedBtn(modsecTableBtn);

		try {
			tab1.setText("Audit.log");
			tab2.setText("Debug.log");
			tab1.setContent(attachDetail(auditLogTable.geTableView()));
			tab2.setContent(attachDetail(debugLogTable.geTableView()));
		} catch (IOException e) {
			System.err.println("Error while creating table");
		}

		showTable(tabPane);
	}

	@FXML
	void searchBtnPressed() {
		String ipString = ipSearch.getText();
		LocalDate date;
		try {
			date = dateSearch.getValue();
		} catch (Exception e) {
			date = null;
		}
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("Date", date);
		searchMap.put("Src IP address", ipString);
		search(searchMap);
	}

	@FXML
	void initialize() {
		btns.add(apacheTableBtn);
		btns.add(apacheGraphBtn);
		btns.add(iptablesTableBtn);
		btns.add(iptablesGraphBtn);
		btns.add(modsecTableBtn);
		btns.add(modsecGraphBtn);

		bindVisible();
		searchBar.setHgap(10);
		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tab1.setClosable(false);
		tab2.setClosable(false);
		tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
			if (newTab == tab1) {
				searchBar.setVisible(true);
			} else if (newTab == tab2) {
				searchBar.setVisible(true);
			}
		});
	}

	void bindVisible() {
		searchBar.setVisible(false);
		searchBar.managedProperty().bind(searchBar.visibleProperty());

		for (Button b : btns) {
			b.setVisible(false);
			b.managedProperty().bind(b.visibleProperty());
		}
	}

	void displaySelectedBtn(Button b) {
		for (Button btn : btns) {
			if (b.equals(btn)) {
				btn.setStyle("-fx-background-color: #1299f6");
			} else {
				btn.setStyle("-fx-background-color: #dddddd");
			}
		}
	}

	void showButton(int index) {
		for (int i = 0; i < btns.size(); i++) {
			Button b = btns.get(i);
			if (!b.isVisible() && (i == index || i == index + 1)) {
				b.setVisible(true);
			} else if (btns.get(i).isVisible() && (i != index && i != index + 1)) {
				b.setVisible(false);
			}
		}
	}

	private void showTable(Node node) {
		infoPane.getChildren().set(0, (Node) node);
	}

	public void search(HashMap<String, Object> searchMap) {

		if (whatLog == null) {
			return;
		}
		switch (whatLog) {
			case "apache":
				if (tabPane.getSelectionModel().getSelectedItem().equals(tab1)) {
					apacheAccessLogTable.filterByFields(searchMap);
				} else {
					apacheErrorLogTable.filterByFields(searchMap);
				}
				break;
			case "iptables":
				iptablesLogTable.filterByFields(searchMap);
				break;
			case "modsec":
				if (tabPane.getSelectionModel().getSelectedItem().equals(tab1)) {
					auditLogTable.filterByFields(searchMap);
				} else {
					debugLogTable.filterByFields(searchMap);
				}
				break;
			default:
				break;
		}
	}

	private TableView<Log> attachDetail(TableView<Log> tableView) {
		if (tableView.getRowFactory() == null) {
			tableView.setRowFactory(tv -> {
				TableRow<Log> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && !row.isEmpty()) {
						Log rowData = row.getItem();
						createPopup(Util.prettyPrintMap(rowData.getLogLine()));
					}
				});
				return row;
			});
		}
		return tableView;
	}

	private void createPopup(String s) {

		Stage stage = new Stage();
		stage.setTitle("Log detail");

		Text text = new Text(s);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(text);
		Scene scene = new Scene(scrollPane, 600, 600);

		stage.setScene(scene);
		stage.show();
	}
}
