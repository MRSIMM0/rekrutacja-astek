package src.Models.DTO;

public class LimitDTO {

    private String limitType;

    private Double limit;

    public LimitDTO(String limitType, Double limit) {
        this.limitType = limitType;
        this.limit = limit;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
}
