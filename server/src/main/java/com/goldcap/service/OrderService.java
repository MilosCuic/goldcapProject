package com.goldcap.service;

import com.goldcap.model.Order;
import com.goldcap.web.dto.SaveOrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    public Order saveOrder(SaveOrderDTO dto);
    public Order getById(Long id);
    public Order getByUUID(String uuid);
    Page<Order> searchOrders(String buyerName, String realmName, Double goldAmount , Double price ,String username , int pageNum , int pageSize , String field , String direction);
    public List<Order> getAllOrders();
    public void deleteById(Long id);

}
