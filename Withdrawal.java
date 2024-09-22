
package custDB;

import java.util.Date;

public class Withdrawal {
    private String custId;
    private Date withdrawalDate;
    private double withdrawnAmount;
    private double penalty;
    private double finalBalanceAfterWithdrawal;

    // Constructor
    public Withdrawal(String custId, Date withdrawalDate, double withdrawnAmount, double penalty, double finalBalanceAfterWithdrawal) {
        this.custId = custId;
        this.withdrawalDate = withdrawalDate;
        this.withdrawnAmount = withdrawnAmount;
        this.penalty = penalty;
        this.finalBalanceAfterWithdrawal = finalBalanceAfterWithdrawal;
    }

    // Getters and Setters
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public double getWithdrawnAmount() {
        return withdrawnAmount;
    }

    public void setWithdrawnAmount(double withdrawnAmount) {
        this.withdrawnAmount = withdrawnAmount;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getFinalBalanceAfterWithdrawal() {
        return finalBalanceAfterWithdrawal;
    }

    public void setFinalBalanceAfterWithdrawal(double finalBalanceAfterWithdrawal) {
        this.finalBalanceAfterWithdrawal = finalBalanceAfterWithdrawal;
    }
}
