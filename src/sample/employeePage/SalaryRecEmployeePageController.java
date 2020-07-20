package sample.employeePage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import sample.Main;
import sample.PayslipServer;
import sample.serverOperation;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SalaryRecEmployeePageController implements Initializable {

    @FXML
    private ComboBox<String> date_combo;
    @FXML
    private Label lbl_familyHelp;
    @FXML
    private Label lbl_childrenHelp;
    @FXML
    private Label lbl_houseHelp;
    @FXML
    private Label lbl_transfer;
    @FXML
    private Label lbl_totalPayments;
    @FXML
    private Label lbl_mission;
    @FXML
    private Label lbl_jobSpecial;
    @FXML
    private Label lbl_grocery;
    @FXML
    private Label lbl_lunch;
    @FXML
    private Label lbl_jobDifficulty;
    @FXML
    private Label lbl_city;
    @FXML
    private Label lbl_personName;
    @FXML
    private Label lbl_sum;
    @FXML
    private Label lbl_tax;
    @FXML
    private Label lbl_insurance;
    @FXML
    private Label lbl_collaborate;
    @FXML
    private Label lbl_totalSub;
    @FXML
    private Label lbl_debt;

    PayslipServer ps;
    serverOperation op;

    String personId = Main.getAuthenticatedUser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            op = new serverOperation();
            op.createConnection();
            ps = new PayslipServer();
            ps.createConnection();

            //initilaze comboBox
            ResultSet combo_result = ps.getComboPayslip(personId);
            while (combo_result.next()){
                date_combo.getItems().addAll(
                    combo_result.getString("issueDate")
                );
            }

            lbl_city.setText(op.getTeacherCity(personId));
            lbl_personName.setText(op.getTeacherName(personId));

            date_combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try{
                        loadInfo();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadInfo() throws Exception{
        ResultSet payslip = ps.getPersonPayslip(personId, date_combo.getValue());
        payslip.next();

        String houseHelp = payslip.getString("houseHelp");
        String transferHelp = payslip.getString("transferHelp");
        String groceryHelp = payslip.getString("groceryHelp");
        String missionHelp = payslip.getString("missionHelp");
        String lunchHelp = payslip.getString("lunchHelp");
        String childrenHelp = payslip.getString("childrenHelp");

        //String totalPayment = payslip.getString("");

        String actionId = payslip.getString("actionId");

        ResultSet action = ps.getActionByPayslip(actionId);


        String jobSpecial = action.getString(2);
        String familyHelp = action.getString(3);
        String jobDifficultyAllowance = action.getString(4);

        lbl_houseHelp.setText(houseHelp);
        lbl_transfer.setText(transferHelp);
        lbl_grocery.setText(groceryHelp);
        lbl_mission.setText(missionHelp);
        lbl_lunch.setText(lunchHelp);
        lbl_childrenHelp.setText(childrenHelp);
        lbl_jobSpecial.setText(jobSpecial);
        lbl_familyHelp.setText(familyHelp);
        lbl_jobDifficulty.setText(jobDifficultyAllowance);
       // lbl_totalPayments.setText(totalPayment);

        String tax = payslip.getString("tax");
        String insurance = payslip.getString("insurance");
        String collaborate = payslip.getString("collaborate");
        lbl_tax.setText(tax);
        lbl_insurance.setText(insurance);
        lbl_collaborate.setText(collaborate);

        int totalpayment = Integer.parseInt(houseHelp)+Integer.parseInt(transferHelp)+Integer.parseInt(groceryHelp)
                +Integer.parseInt(missionHelp)+Integer.parseInt(lunchHelp)+Integer.parseInt(childrenHelp)+Integer.parseInt(jobSpecial)
                +Integer.parseInt(familyHelp) +Integer.parseInt(jobDifficultyAllowance);

        lbl_totalPayments.setText(Integer.toString(totalpayment));

        int totalSub = Integer.parseInt(tax)+Integer.parseInt(insurance)+Integer.parseInt(collaborate);

        lbl_totalSub.setText(Integer.toString(totalSub) );

        int total = totalpayment - totalSub ;

        lbl_sum.setText(Integer.toString(total));
    }

}
