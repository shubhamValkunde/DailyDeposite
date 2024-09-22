/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custDB;


public class MonthlySummary {
    private String month;
    private int daysInMonth;
    private double firstHalfAmount;
    private double secondHalfAmount, xAmountFirstHalf,xamountSecondHalf,interset,monthlyX,totalYearXAmount;

    private double withdrawnAmount; // New field
    private double penalty;
    public void setTotalYearXAmount(double totalYearXAmount) {
        this.totalYearXAmount = totalYearXAmount;
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
    
    private double monthlyInterest;
    private double carryOver;

    public String getMonth() {
        return month;
    }

    public double getxAmountFirstHalf() {
        return xAmountFirstHalf;
    }

    public double getXamountSecondHalf() {
        return xamountSecondHalf;
    }

    public double getInterset() {
        return interset;
    }

    public double getMonthlyX() {
        return monthlyX;
    }

    public void setxAmountFirstHalf(double xAmountFirstHalf) {
        this.xAmountFirstHalf = xAmountFirstHalf;
    }

    public void setXamountSecondHalf(double xamountSecondHalf) {
        this.xamountSecondHalf = xamountSecondHalf;
    }

    public void setInterset(double interset) {
        this.interset = interset;
    }

    public double getTotalYearXAmount() {
        return totalYearXAmount;
    }

    public void setMonthlyX(double monthlyX) {
        this.monthlyX = monthlyX;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public double getFirstHalfAmount() {
        return firstHalfAmount;
    }

    public void setFirstHalfAmount(double firstHalfAmount) {
        this.firstHalfAmount = firstHalfAmount;
    }

    public double getSecondHalfAmount() {
        return secondHalfAmount;
    }

    public void setSecondHalfAmount(double secondHalfAmount) {
        this.secondHalfAmount = secondHalfAmount;
    }

    public double getMonthlyInterest() {
        return monthlyInterest;
    }

    public void setMonthlyInterest(double monthlyInterest) {
        this.monthlyInterest = monthlyInterest;
    }

    public double getCarryOver() {
        return carryOver;
    }

    public void setCarryOver(double carryOver) {
        this.carryOver = carryOver;
    }

    
}
