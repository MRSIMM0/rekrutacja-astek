package src.Models.DTO;

public class ReceiptDTO {

    private String receiptName;

    private Double amount;

    private Double limit;

    public ReceiptDTO(String receiptName, Double amount, Double limit) {
        this.receiptName = receiptName;
        this.amount = amount;
        this.limit = limit;
    }

    public String getReceiptName() {
        return receiptName;
    }

    public void setReceiptName(String receiptName) {
        this.receiptName = receiptName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
}
