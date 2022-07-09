package src.Models.DTO;

import src.Models.Limit.Limit;
import src.Models.Receipt;

import java.util.List;

public class AdminFiledDTO {

    public String receipt;

    public Double allowenceRade;

    public Double dailyMillage;

    public List<Receipt> availableReceipts;

    public List<String> limitTypes;

    public List<Limit> limits;

    @Override
    public String toString() {
        return "AdminFieldsDTO{" +
                "allowenceRade=" + allowenceRade +
                ", dailyMillage=" + dailyMillage +
                ", availableReceipts=" + availableReceipts +
                '}';
    }

    public AdminFiledDTO(String receipt, Double allowenceRade, Double dailyMillage, List<Receipt> availableReceipts, List<String> limitTypes, List<Limit> limits) {
        this.receipt = receipt;
        this.allowenceRade = allowenceRade;
        this.dailyMillage = dailyMillage;
        this.availableReceipts = availableReceipts;
        this.limitTypes = limitTypes;
        this.limits = limits;
    }
}
