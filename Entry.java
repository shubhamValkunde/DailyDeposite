package custDB;

import java.util.Date;

public class Entry{
  String custId;
  Date date;
  double amount;
  double balance;

    public Entry(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Entry() {
    }
  
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
 
    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Entry(String custId, Date date, double amount, double balance) {
        this.custId = custId;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }
    
    public Entry(String custId, Date date, double amount) {
        this.custId = custId;
        this.date = date;
        this.amount = amount;
    }
    

    
}