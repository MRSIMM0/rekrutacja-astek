package src.Services;

import src.Models.Limit.Limit;
import src.Models.Limit.LimitType;
import src.Models.Receipt;

import java.io.InvalidObjectException;
import java.util.List;


public interface AdminService {

     void setDailyAllowance(Double dailyAllowance);

     void setCarMileageRate(Double carMileageRate);

     void addReceipt(Receipt receipts);

     void deleteReceipts(String name) throws IllegalAccessException;

     void setRecipeTypeLimit(String receiptName, Double limit) throws InvalidObjectException;

     void setOtherLimit(LimitType type, Double limit) throws IllegalAccessException;

     void deleteOtherLimit(LimitType type);

     Double getDailyAllowence();

     List<Receipt> getAllRecipes();


     Double getDailyMillage();

     List<Limit> getOtherLimits();


     List<String> getLimitTypes();
}
