package com.goldcap.converter;

import com.goldcap.model.Order;
import com.goldcap.web.dto.OrderDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderToOrderDTO implements Converter<Order , OrderDTO> {

    @Override
    public OrderDTO convert(Order source) {

        if (source == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();

        dto.setId(source.getId());
        dto.setUUID(source.getUUID());
        dto.setBuyerName(source.getBuyerName());
        dto.setRealmId(source.getRealm().getId());
        dto.setRealmName(source.getRealm().getName());
        dto.setGoldcapUserId(source.getGoldcapUser().getId());
        dto.setGoldcapUserUsername(source.getGoldcapUser().getUsername());
        dto.setGoldcapUserEmail(source.getGoldcapUser().getEmail());
        dto.setGoldAmount(source.getGoldAmount());
        dto.setPrice(source.getPrice());
        dto.setDateRequested(source.getDateRequested());
        dto.setDateSubmitted(source.getDateSubmitted());

        return dto;
    }

    public List<OrderDTO> convert(List<Order> orders) {

        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            orderDTOS.add(convert(order));
        }

        return orderDTOS;
    }
}


