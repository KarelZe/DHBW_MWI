package de.dhbw.karlsruhe.controller;

import javafx.application.Application;
import de.dhbw.karlsruhe.helper.ConverterHelper;
import de.dhbw.karlsruhe.helper.LogoutHelper;
import de.dhbw.karlsruhe.model.AktuelleSpieldaten;
import de.dhbw.karlsruhe.model.*;
import de.dhbw.karlsruhe.model.jpa.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Optional;
import java.util.List;
import javafx.scene.control.ButtonType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;

public class InvestmentUebersichtController implements ControlledScreen {

    private ScreenController screenController;
    private List<Unternehmen> unternehmenListe;
    //private UnternehmenRepository unternehmenRepo;

    @FXML
    private TableView<Object> tvInvestmentUebersicht;

    @Override
    public void setScreenParent(ScreenController screenPage) {
        this.screenController = screenPage;
    }

    @FXML
    public void initialize(){
        System.out.println("Init");

        tvInvestmentUebersicht.setEditable(true);

        unternehmenListe = UnternehmenRepository.getInstanz().findAll();
        for (int i = 0; i < unternehmenListe.size(); i++) {
            tvInvestmentUebersicht.getColumns().addAll(new TableColumn(unternehmenListe.get(i).getName()));
        }

    }

}