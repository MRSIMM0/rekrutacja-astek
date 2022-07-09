package src.Services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.DataSource.Database;
import src.Models.Limit.LimitType;
import src.Models.Receipt;


import java.io.InvalidObjectException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminServiceImplTest {

    Database DB;
    AdminServiceImpl service;


    @BeforeEach
    void init() {
        DB = new Database();
        service = new AdminServiceImpl(DB);
    }

    @Test
    void setDailyAllowance() {
        service.setDailyAllowance(3.2d);
        assertEquals(3.2d,  DB.getDailyAllowance());
    }

    @Test
    void setCarMileageRate()  {
        service.setCarMileageRate(3.8d);
        assertEquals(3.8d, DB.getCarMileageRate());
    }

    @Test
    void addReceipt()  {

        Receipt receipt = Receipt.createInstance("Test");

        service.addReceipt(receipt);

        assertEquals(DB.getReceipts().size(),5);
    }

    @Test
    void deleteReceipts() throws IllegalAccessException {
        service.deleteReceipts("taxi");
        assertEquals(DB.getReceipts().size(),3);
    }

    @Test
    void setLimit() throws InvalidObjectException {

        Receipt receipt = Receipt.createInstance("Test");

        service.addReceipt(receipt);

        service.setRecipeTypeLimit("Test",3d);

        assertEquals(receipt.getLimit(),3d);
    }

    @Test
    void setOtherLimit() throws IllegalAccessException {
        service.setOtherLimit(LimitType.NUMBER_OF_KM,20d);
        assertEquals(2,DB.getLimits().size());
    }

    @Test
    void deleteOtherLimit() {
        service.deleteOtherLimit(LimitType.TOTAL);
        assertEquals(0,DB.getLimits().size());
    }

    @Test
    void getAllRecipes(){

        assertEquals(4,  service.getAllRecipes().size());
    }

    @Test
    void getLimitTypes(){
        assertEquals(2,service.getLimitTypes().size());

    }


}