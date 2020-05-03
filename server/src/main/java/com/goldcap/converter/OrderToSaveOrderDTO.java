package com.goldcap.converter;

import com.goldcap.model.Order;
import com.goldcap.web.dto.SaveOrderDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderToSaveOrderDTO implements Converter<Order , SaveOrderDTO> {

    @Override
    public SaveOrderDTO convert(Order source) {
        if(source == null){
            return null;
        }

        SaveOrderDTO dto = new SaveOrderDTO();

        dto.setId(source.getId());
        dto.setBuyerName(source.getBuyerName());
        dto.setRealmId(source.getRealm().getId());
        dto.setGoldcapUserId(source.getGoldcapUser().getId());
        dto.setGoldAmount(source.getGoldAmount());
        dto.setPrice(source.getPrice());
        dto.setDateRequested(source.getDateRequested());
        dto.setDateSubmitted(source.getDateSubmitted());

        return dto;
    }

    public List<SaveOrderDTO> convert(List<Order> source) {

        List<SaveOrderDTO> saveOrderDTOS = new ArrayList<>();

        for(Order order: source) {
            saveOrderDTOS.add(convert(order));
        }

        return saveOrderDTOS;

    }
}
