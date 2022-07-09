package src.Services;

import src.DataSource.DataBase;
import src.Models.*;
import src.Models.Limit.Limit;
import src.Models.Limit.LimitType;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class AdminServiceImpl implements AdminService{

    private final DataBase pseudoDB;

    public AdminServiceImpl(DataBase dataBase) {
        this.pseudoDB = Objects.requireNonNull(dataBase);
    }

    public void setDailyAllowance( Double dailyAllowance)  {

        pseudoDB.setDailyAllowance(dailyAllowance);

    }

    public void setCarMileageRate( Double carMileageRate)  {

        pseudoDB.setCarMileageRate(carMileageRate);

    }

    public void addReceipt(Receipt receipts)  {

        var p = pseudoDB.getReceipts();
        p.add(receipts);
    }

    public void deleteReceipts(String name) throws IllegalAccessException {
        if (!pseudoDB.getReceipts().contains(Receipt.createInstance(name))) throw new IllegalAccessException();
        pseudoDB.getReceipts().remove(Receipt.createInstance(name));
    }

    public void setRecipeTypeLimit(String receiptName, Double limit) throws  InvalidObjectException {
        var el = pseudoDB.getReceipts().stream().filter(receipt -> receiptName.equals(receipt.getReceiptName())).findFirst();
        if (el.isEmpty()) throw new InvalidObjectException("Given receipt does not exist");
        el.get().setLimit(limit);
    }

    public void setOtherLimit(LimitType type, Double limit) throws IllegalAccessException {


        if (pseudoDB.getLimits().stream().anyMatch(el -> el.getLimitType().equals(type)))
            throw new IllegalAccessException();
        pseudoDB.getLimits().add(new Limit(type, limit));
    }

    public void deleteOtherLimit( LimitType type) {

        pseudoDB.getLimits().remove(pseudoDB.getLimits().stream().filter(el -> el.getLimitType().equals(type)).collect(Collectors.toList()).get(0));
    }

    public Double getDailyAllowence() {
        return pseudoDB.getDailyAllowance();
    }

    public List<Receipt> getAllRecipes() {
        return pseudoDB.getReceipts();
    }


    public Double getDailyMillage() {
        return pseudoDB.getCarMileageRate();
    }

    public List<Limit> getOtherLimits() {
        return pseudoDB.getLimits();
    }


    public List<String> getLimitTypes() {
        return pseudoDB.getLimitTypes();
    }
}
