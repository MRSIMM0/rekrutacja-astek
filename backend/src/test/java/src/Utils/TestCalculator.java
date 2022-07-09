package src.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.DataSource.DataBase;
import src.Models.Limit.LimitType;
import src.Models.Receipt;
import src.Services.AdminServiceImpl;
import src.Utils.Calculator;
import src.DataSource.Database;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculator {



    DataBase testDb;
    AdminServiceImpl service;

    @BeforeEach
    void init(){
        testDb =  new Database();
        service = new AdminServiceImpl(testDb);
    }

    @Test
    void calculate() {
        Calculator calculator =new Calculator.Builder().personalCarMillage(12d).days(3).service(service).build();
        assertEquals(12*service.getDailyMillage()+service.getDailyAllowence()*3,calculator.calculate());
    }

    @Test
    void calculateWithLimit() throws IllegalAccessException {

        service.setOtherLimit(LimitType.NUMBER_OF_KM,20d);

        Calculator calculator =new Calculator.Builder().personalCarMillage(40d).days(3).service(service).build();
        Calculator calculator1 = new Calculator.Builder().personalCarMillage(10d).days(3).service(service).build();

        assertEquals(20*service.getDailyMillage()+3*service.getDailyAllowence(),calculator.calculate());
        assertEquals(10*service.getDailyMillage()+3*service.getDailyAllowence(),calculator1.calculate());
    }

    @Test
    void calculateWithRecopies(){
        Receipt r = Receipt.createInstance("Test");
        r.setLimit(null);
        var r2 = r.copy(20d);
        var r3 = r.copy(40d);

        List<Receipt>  rec = List.of(r2,r3);

        Calculator calculator =new Calculator.Builder().receipts(rec).service(service).build();

        assertEquals(60,calculator.calculate());
    }
}