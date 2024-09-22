package custDB;

import java.util.Date;

public class PrintPassBook {
    private String printId;
    private String jsonData;
    private Date createdAt;
    private String custId;

    // Constructor
    public PrintPassBook(String printId, String jsonData, Date createdAt, String custId) {
        this.printId = printId;
        this.jsonData = jsonData;
        this.createdAt = createdAt;
        this.custId = custId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    // Getters and Setters
    public String getPrintId() {
        return printId;
    }

    public void setPrintId(String printId) {
        this.printId = printId;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
