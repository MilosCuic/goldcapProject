package com.goldcap.util;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

@Component
public class CalculatePricePer100k {

    private final double  minGoldRequired = 100000.00;

    public Double setPrice(double goldAmount , double price){
        if (goldAmount > minGoldRequired && price > 0){
            return price / (goldAmount / 100000.00);
        }else {
            throw  new NumberFormatException("Gold amount must be at least 100.000");
        }
    }
}
