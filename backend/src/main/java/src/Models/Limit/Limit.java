package src.Models.Limit;

public class Limit {

    private LimitType limitType;

    private Double limit;

    public Limit(LimitType limitType, Double limit) {
        this.limitType = limitType;
        this.limit = limit;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(LimitType limitType) {
        this.limitType = limitType;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
}
