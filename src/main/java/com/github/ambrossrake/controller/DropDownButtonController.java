package com.github.ambrossrake.controller;

import com.github.ambrossrake.model.Drawable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

//FIXME closing dropdown w/ empty list causes exception
public class DropDownButtonController {

    private final Logger LOG = LoggerFactory.getLogger(DropDownButtonController.class);
    public Button dropDownButton;
    private boolean isToggled = false;
    private Pane view;
    private ObservableList<Drawable> dropDownItems = FXCollections.observableList(new ArrayList<>());

    public DropDownButtonController() {
        dropDownItems.addListener((ListChangeListener<Drawable>) c -> {
            while(c.next()){
                if(c.wasAdded() || c.wasRemoved()){
                    LOG.debug("Dropdown List Updated");
                    openDropDown();
                    break;
                }
            }
        });
    }

    @FXML
    public void initialize(){
        dropDownButton.setRotate(270);
        dropDownButton.setUserData(this);
    }

    public void toggleDropDown() {
        if(view == null){
            LOG.warn("View has not been set, cannot display dropdown items");
            return;
        }
        isToggled = !isToggled;

        if(isToggled){
            openDropDown();
        }else{
            closeDropDown();
        }
    }

    private void closeDropDown(){
        dropDownButton.setRotate(270);
        view.getChildren().clear();
    }

    private void openDropDown(){
        dropDownButton.setRotate(0);
        drawDropDownItems();
    }

    private void drawDropDownItems(){
        view.getChildren().clear();
        for (Drawable item: dropDownItems) {
            view.getChildren().add(item.draw());
        }
    }

    public void setView(Pane pane){
        this.view = pane;
    }

    public void setDropDownItems(List<Drawable> items){
        dropDownItems = FXCollections.observableList(items);
    }

    public ObservableList<Drawable> getDropDownItems() {
        return dropDownItems;
    }
}
