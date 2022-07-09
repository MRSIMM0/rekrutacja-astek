package src.DataSource;


import src.Models.Limit.Limit;
import src.Models.Limit.LimitType;
import src.Models.Receipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Database implements DataBase {



    private Double dailyAllowance = 15d;

    private Double carMileageRate = 0.3;

    private List<Receipt> receipts = new ArrayList<> (Arrays.asList(Receipt.createInstance("taxi"), Receipt.createInstance("hotel"), Receipt.createInstance("air ticket"), Receipt.createInstance("train")));

    private List<Limit> limits = new ArrayList<>(List.of(new Limit(LimitType.TOTAL, 2000d)));

    private List<String> limitType = Arrays.stream(LimitType.values()).map(Enum::name).collect(Collectors.toList());

    public Double getDailyAllowance() {
        return dailyAllowance;
    }

    public Double getCarMileageRate() {
        return carMileageRate;
    }


    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public void setDailyAllowance(Double dailyAllowance) {
        this.dailyAllowance = dailyAllowance;
    }

    public void setCarMileageRate(Double carMileageRate) {
        this.carMileageRate = carMileageRate;
    }

    @Override
    public List<Limit> getLimits() {
        return limits;
    }

    @Override
    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

    public List<String> getLimitTypes() {
        return limitType;
    }

    public void setLimitTypes(List<String> limitType) {
        this.limitType = limitType;
    }
}
