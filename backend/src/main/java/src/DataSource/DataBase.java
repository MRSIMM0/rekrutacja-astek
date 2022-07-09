package src.DataSource;

import src.Models.Limit.Limit;
import src.Models.Receipt;

import java.util.List;

public interface DataBase {

     Double getDailyAllowance();

     Double getCarMileageRate() ;


     List<Receipt> getReceipts();

     void setReceipts(List<Receipt> receipts) ;

     void setDailyAllowance(Double dailyAllowance) ;

     void setCarMileageRate(Double carMileageRate);

     List<Limit> getLimits();

     void setLimits(List<Limit> receipts) ;

     List<String> getLimitTypes();

     void setLimitTypes(List<String> limitTypes);

}
