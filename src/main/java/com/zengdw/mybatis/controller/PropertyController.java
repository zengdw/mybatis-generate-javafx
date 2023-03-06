package com.zengdw.mybatis.controller;

import com.zengdw.mybatis.config.Context;
import com.zengdw.mybatis.config.GeneratorProperties;
import com.zengdw.mybatis.generator.MyShellCallback;
import freemarker.template.Configuration;
import freemarker.template.Template;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.ShellCallback;
import org.mybatis.generator.config.xml.ConfigurationParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/3/2 14:05
 */
@SuppressWarnings("DataFlowIssue")
@Slf4j
public class PropertyController implements Initializable {
    @FXML
    private ScrollPane scroll;
    @FXML
    private TextField projectPath;
    private final Configuration configuration;
    private final Service<Void> service = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    generate();
                    return null;
                }
            };
        }
    };

    public PropertyController() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_32);
        configuration.setAutoFlush(true);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/templates").getPath()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectPath.onActionProperty().addListener((observableValue, h1, h2) -> selectDirectory());
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("project directory select");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) projectPath.getScene().getWindow();
        File file = dc.showDialog(stage);
        projectPath.setText(null != file ? file.getAbsolutePath() : projectPath.getText());
        GeneratorProperties.of().setProjectPath(projectPath.getText());
    }

    @FXML
    private void cancel() {
        Context.getStage("tableList").show();
        Context.getStage("property").close();
    }

    @FXML
    private void modelPathChange(KeyEvent event) {
        GeneratorProperties.of().setModelPath(((TextField) event.getSource()).getText());
    }

    @FXML
    private void mapperPath(KeyEvent event) {
        GeneratorProperties.of().setMapperPath(((TextField) event.getSource()).getText());
    }

    @FXML
    private void mapperXmlPath(KeyEvent event) {
        GeneratorProperties.of().setMapperXmlPath(((TextField) event.getSource()).getText());
    }

    @FXML
    private void modelSuperClassPath(KeyEvent event) {
        GeneratorProperties.of().setModelSuperClassPath(((TextField) event.getSource()).getText());
    }

    @FXML
    private void needLombok(MouseEvent event) {
        GeneratorProperties.of().setNeedLombok(((CheckBox) event.getSource()).isSelected());
    }

    @FXML
    private void needMybatisPlus(MouseEvent event) {
        GeneratorProperties.of().setNeedMybatisPlus(((CheckBox) event.getSource()).isSelected());
    }

    @FXML
    private void mergeFailed(MouseEvent event) {
        GeneratorProperties.of().setMergeFailed(((CheckBox) event.getSource()).isSelected());
    }

    @FXML
    private void generateType(MouseEvent event) {
        GeneratorProperties.of().setGenerateType(((CheckBox) event.getSource()).isSelected());
    }

    @FXML
    private void tableNamePre(KeyEvent event) {
        GeneratorProperties.of().setTableNamePre(((TextField) event.getSource()).getText());
    }

    @FXML
    private void tableNameSuf(KeyEvent event) {
        GeneratorProperties.of().setTableNameSuf(((TextField) event.getSource()).getText());
    }

    @FXML
    public void submit() {
        VBox vBox = (VBox) scroll.lookup("#vbox");
        vBox.getChildren().remove(0, vBox.getChildren().size());
        vBox.setPrefHeight(120);
        service.start();
    }

    private void generate() throws Exception {
        Template template = configuration.getTemplate("mybatis-generator.ftl");
        String generatorPath = System.getProperty("user.dir") + File.separator + "mybatis-generator.xml";
        File file = new File(generatorPath);
        GeneratorProperties properties = GeneratorProperties.of();
        try (FileWriter fileWriter = new FileWriter(generatorPath)) {
            template.process(properties, fileWriter);

            List<String> warnings = new ArrayList<>();
            ConfigurationParser cp = new ConfigurationParser(warnings);
            org.mybatis.generator.config.Configuration config = cp.parseConfiguration(file);
            ShellCallback callback = new MyShellCallback(properties);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(new ProgressCallback() {
                @Override
                public void startTask(String taskName) {
                    log.debug("===>{}", taskName);
                    print(taskName);
                }

                @Override
                public void done() {
                    warnings.forEach(s -> {
                        log.warn("===>{}", s);
                        print(s);
                    });
                    log.debug("===>生成完成");
                    print("生成完成");
                }
            });
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }
    private void print(String msg) {
        Text tf = new Text(msg);
        tf.setStyle("-fx-fill: red;-fx-wrap-text: 595;");
        Platform.runLater(() -> {
            VBox vbox = (VBox) scroll.lookup("#vbox");
            vbox.getChildren().add(tf);
            scroll.vvalueProperty().setValue(1);
            Context.getStage("property").sizeToScene();
        });
    }
}
