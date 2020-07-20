package sample;

public class Payslip {
    // salary fields

    String payslipId,
    houseHelp,
    groceryHelp,
    transferHelp,
    lunchHelp,
    missionHelp;

    Action personAction;

    // deduction fields

    String insurance,
    tax;

    public Payslip(String payslipId, String houseHelp, String groceryHelp, String transferHelp, String lunchHelp, String missionHelp, Action personAction, String insurance, String tax) {
        this.payslipId = payslipId;
        this.houseHelp = houseHelp;
        this.groceryHelp = groceryHelp;
        this.transferHelp = transferHelp;
        this.lunchHelp = lunchHelp;
        this.missionHelp = missionHelp;
        this.personAction = personAction;
        this.insurance = insurance;
        this.tax = tax;
    }

    public String getPayslipId() {
        return payslipId;
    }

    public void setPayslipId(String payslipId) {
        this.payslipId = payslipId;
    }

    public String getHouseHelp() {
        return houseHelp;
    }

    public void setHouseHelp(String houseHelp) {
        this.houseHelp = houseHelp;
    }

    public String getGroceryHelp() {
        return groceryHelp;
    }

    public void setGroceryHelp(String groceryHelp) {
        this.groceryHelp = groceryHelp;
    }

    public String getTransferHelp() {
        return transferHelp;
    }

    public void setTransferHelp(String transferHelp) {
        this.transferHelp = transferHelp;
    }

    public String getLunchHelp() {
        return lunchHelp;
    }

    public void setLunchHelp(String lunchHelp) {
        this.lunchHelp = lunchHelp;
    }

    public String getMissionHelp() {
        return missionHelp;
    }

    public void setMissionHelp(String missionHelp) {
        this.missionHelp = missionHelp;
    }

    public Action getPersonAction() {
        return personAction;
    }

    public void setPersonAction(Action personAction) {
        this.personAction = personAction;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }




}
