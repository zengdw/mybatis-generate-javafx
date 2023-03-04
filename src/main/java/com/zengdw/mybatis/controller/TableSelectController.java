package com.zengdw.mybatis.controller;

import com.zengdw.mybatis.component.LoadingCom;
import com.zengdw.mybatis.config.Context;
import com.zengdw.mybatis.config.DatabaseConfig;
import com.zengdw.mybatis.domain.Table;
import com.zengdw.mybatis.service.IDatabaseService;
import com.zengdw.mybatis.service.MysqlServiceImpl;
import com.zengdw.mybatis.service.OracleServiceImpl;
import com.zengdw.mybatis.utils.StageUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/27 17:26
 */
public class TableSelectController implements Initializable {
    @FXML
    private AnchorPane ap;
    @FXML
    private Text errorMsg;
    @FXML
    private TextField filterTableName;
    @FXML
    private CheckBox allSelected;
    @FXML
    private TableView<Table> tableList;
    @FXML
    private TableColumn<Table, CheckBox> selected;
    @FXML
    private TableColumn<Table, String> tableName;
    @FXML
    private TableColumn<Table, String> remark;
    private int clickAllCheckBox = 0;
    private List<Table> tables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        queryTableList();

        allSelected.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (clickAllCheckBox == 2) {
                return;
            }
            clickAllCheckBox = 1;
            ObservableList<Table> items = tableList.getItems();
            if (allSelected.isSelected()) {
                for (Table table : items) {
                    table.getSelected().setSelected(true);
                }
            } else {
                for (Table table : items) {
                    table.getSelected().setSelected(false);
                }
            }
            clickAllCheckBox = 0;
        });
    }

    private void queryTableList() {
        LoadingCom loading = LoadingCom.show(ap, "loading...", 590, 375);
        Service<List<Table>> queryTableService = new Service<>() {
            @Override
            protected Task<List<Table>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Table> call() throws Exception {
                        String dataType = DatabaseConfig.instance().getDataType();
                        IDatabaseService service;
                        if ("Oracle".equals(dataType)) {
                            service = new OracleServiceImpl();
                        } else {
                            service = new MysqlServiceImpl();
                        }
                        return service.selectTable();
                    }
                };
            }
        };
        queryTableService.start();

        queryTableService.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            loading.close();
            tables = newValue;
            ObservableList<Table> teamMembers = FXCollections.observableArrayList(tables);
            selected.setCellValueFactory(cellData -> cellData.getValue().getCheckBox());
            selected.setCellFactory(new Callback<>() {
                @Override
                public TableCell<Table, CheckBox> call(TableColumn<Table, CheckBox> tableCheckBoxTableColumn) {
                    TableCell<Table, CheckBox> cell = new TableCell<>() {
                        @Override
                        public void updateItem(CheckBox item, boolean empty) {
                            super.updateItem(item, empty);
                            setGraphic(item);
                            if (null != item) {
                                item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                                    if (clickAllCheckBox == 1) {
                                        return;
                                    }
                                    clickAllCheckBox = 2;
                                    boolean booSelectAll = true;
                                    ObservableList<Table> items2 = tableList.getItems();
                                    for (Table table : items2) {
                                        if (!table.getSelected().isSelected()) {
                                            booSelectAll = false;
                                            break;
                                        }
                                    }
                                    allSelected.setSelected(booSelectAll);
                                    clickAllCheckBox = 0;
                                });
                            }
                        }
                    };
                    cell.setStyle("-fx-alignment: CENTER;");
                    return cell;
                }
            });
            tableName.setCellValueFactory(new PropertyValueFactory<>("tableName"));
            remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            tableList.setItems(teamMembers);
            tableList.setEditable(true);
        });
        queryTableService.exceptionProperty().addListener((observableValue, oldValue, newValue) -> {
            errorMsg.setText(newValue.getMessage());
            errorMsg.setVisible(true);
            Context.getStage("tableList").sizeToScene();
            loading.close();
        });
    }

    @FXML
    private void cancel() {
        Context.getStage("login").show();
        Context.getStage("tableList").close();
    }

    @FXML
    private void submit() throws IOException {
        List<Table> selectedTable = tableList.getItems().stream().filter(t -> t.getSelected().isSelected()).toList();
        DatabaseConfig.instance().setSelectedTable(selectedTable);

        Stage stage = new Stage();
        StageUtil.initStage(stage, "Property", "/fxml/property.fxml", "property");
        Context.getStage("tableList").close();
    }

    @FXML
    private void filterAction() {
        List<Table> list = tables;
        String filterTableNameText = filterTableName.getText();
        if (StringUtils.isNotBlank(filterTableNameText)) {
            list = tables.stream().filter(t -> t.getTableName().contains(filterTableNameText.toUpperCase())).toList();
        }
        ObservableList<Table> teamMembers = FXCollections.observableArrayList(list);
        tableList.setItems(teamMembers);
    }
}
