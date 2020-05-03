package com.goldcap.converter;

import com.goldcap.model.Order;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.OrderRepository;
import com.goldcap.repository.RealmRepository;
import com.goldcap.util.CalculatePricePer100k;
import com.goldcap.web.dto.SaveOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SaveOrderDTOtoOrder implements Converter<SaveOrderDTO, Order> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GoldcapUserRepository userRepository;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private CalculatePricePer100k calculatePricePer100k;

    @Override
    public Order convert(SaveOrderDTO dto) {

        Order order = null;

        if (dto.getId() !=null) {
            order = orderRepository.getOne(dto.getId());
        }else {
            order = new Order();
        }

        if (dto.getUUID() != null) {
            order.setUUID(dto.getUUID());
        }else{
            order.setUUID(UUID.randomUUID().toString());
        }

        order.setBuyerName(dto.getBuyerName());
        order.setRealm(realmRepository.getOne(dto.getRealmId()));
        order.setGoldcapUser(userRepository.getOne(dto.getGoldcapUserId()));
        order.setGoldAmount(dto.getGoldAmount());
        order.setPrice(dto.getPrice());
        order.setDateRequested(dto.getDateRequested());
        order.setDateSubmitted(dto.getDateSubmitted());
        order.setPricePer100k(calculatePricePer100k.setPrice(dto.getGoldAmount() , dto.getPrice()));

        return order;
    }
}
