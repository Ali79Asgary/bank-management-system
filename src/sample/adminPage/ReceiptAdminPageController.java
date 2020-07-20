package sample.adminPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.PayslipServer;
import sample.serverOperation;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ReceiptAdminPageController implements Initializable {

    @FXML
    private TextField txtMinimumSalary;

    @FXML
    private TextField txtReceiptDate;

    @FXML
    void payslipAction(ActionEvent event) throws Exception {
        String newDate = txtReceiptDate.getText();
        PayslipServer ps = new PayslipServer();
        ps.createConnection();
        ps.createPayslipAll(newDate);
        ps.closeConnection();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String dialog = "فیش حقوقی برای همه کار مندان صادر شد";
        alert.setContentText(dialog);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
