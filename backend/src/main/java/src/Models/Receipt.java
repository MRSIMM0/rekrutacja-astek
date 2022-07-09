package src.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {

    private static final List<Receipt> receipt = new ArrayList<>();

    private final String receiptName;

    private Double amount = 0d;

    private Double limit = null;


    private Receipt(String receiptName) {
        this.receiptName = receiptName;

    }

    private Receipt(String receiptName, Double limit, Double amount) {
        this.receiptName = receiptName;
        this.limit = limit;
        this.amount = amount;
    }

    public static Receipt createInstance(String receiptName) {

        if (receipt.size() == 0 || receipt.stream().noneMatch(el -> el.receiptName.equals(receiptName))) {
            receipt.add(new Receipt(receiptName));
        }
        return receipt.stream().filter(el -> el.receiptName.equals(receiptName)).collect(Collectors.toList()).get(0);
    }

    public Receipt copy(Double amount) {
        return new Receipt(this.receiptName, this.limit, amount);
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptName='" + receiptName + '\'' +
                ", amount=" + amount +
                ", limit=" + limit +
                '}';
    }


    public String getReceiptName() {
        return receiptName;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getLimit() {
        return limit;
    }
}
