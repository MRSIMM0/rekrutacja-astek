package src.Models.DTO;


import java.util.List;

public class ReimbursementRequestDTO {

    private List<ReceiptDTO> receipts;

    private int period;

    private double personalCarMillage;



    private double result;


    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "receipts=" + receipts +
                ", period=" + period +
                ", personalCarMillage=" + personalCarMillage +

                '}';
    }
    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public List<ReceiptDTO> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<ReceiptDTO> receipts) {
        this.receipts = receipts;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getPersonalCarMillage() {
        return personalCarMillage;
    }

    public void setPersonalCarMillage(double personalCarMillage) {
        this.personalCarMillage = personalCarMillage;
    }


}
