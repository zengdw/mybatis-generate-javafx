package com.zengdw.mybatis.domain;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import lombok.Data;

/**
 * @author zengd
 * @version 1.0
 * @date 2023/2/28 14:33
 */
@Data
public class Table {
    private CheckBox selected = new CheckBox();
    private String tableName;
    private String remark;

    public Table(boolean selected, String tableName, String remark) {
        this.selected.setSelected(selected);
        this.tableName = tableName;
        this.remark = remark;
    }

    public ObservableValue<CheckBox> getCheckBox() {
        return new ObservableValue<>() {
            @Override
            public void addListener(ChangeListener<? super CheckBox> changeListener) {

            }

            @Override
            public void removeListener(ChangeListener<? super CheckBox> changeListener) {

            }

            @Override
            public CheckBox getValue() {
                return selected;
            }

            @Override
            public void addListener(InvalidationListener invalidationListener) {

            }

            @Override
            public void removeListener(InvalidationListener invalidationListener) {

            }
        };
    }
}
