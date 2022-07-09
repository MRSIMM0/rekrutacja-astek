package src.Utils;


import src.Models.Limit.LimitType;
import src.Models.Receipt;
import src.Services.AdminService;



import java.util.List;


public class Calculator {


    private final AdminService service;

    private final Double personalCarMillage;

    private final int days;

    private final List<Receipt> receipts;

    public static class Builder {

        private Double personalCarMillage;

        private int days;

        private AdminService adminService;

        private List<Receipt> receipts;

        public Builder personalCarMillage(Double personalCarMillage) {
            this.personalCarMillage = personalCarMillage;
            return this;
        }

        public Builder days(int days) {
            this.days = days;
            return this;
        }

        public Builder service(AdminService service) {
            this.adminService = service;
            return this;
        }


        public Builder receipts(List<Receipt> receipts) {
            this.receipts = receipts;
            return this;
        }

        public Calculator build() {
            return new Calculator(this);
        }

    }


    public Calculator(Builder builder) {
        this.personalCarMillage = builder.personalCarMillage;
        this.days = builder.days;
        this.receipts = builder.receipts;
        this.service =builder.adminService;
    }

    public double calculate() {

        var limit = service.getOtherLimits();

        Double sum = 0d;

        if (this.receipts != null) {
            sum += this.receipts.stream().map(el -> {
                if (el.getLimit() != null) {
                    if (el.getLimit() < el.getAmount()) {
                        return el.getLimit();
                    }
                }
                return el.getAmount();
            }).mapToDouble(Double::valueOf).sum();
        }
        sum += service.getDailyAllowence() * this.days;

        Double kilometerLimit = -1d;


        if (limit != null) {
            var limitOptional = limit.stream().filter(el -> el.getLimitType().equals(LimitType.NUMBER_OF_KM)).findFirst();
            if (limitOptional.isPresent()) {
                kilometerLimit = limitOptional.get().getLimit();
            }
        }

        if (kilometerLimit < 0 || personalCarMillage < kilometerLimit) {
            kilometerLimit = personalCarMillage;
        }


        if (kilometerLimit != null) {
            sum += service.getDailyMillage() * kilometerLimit;
        }
        return sum;
    }
}
