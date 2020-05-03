package com.goldcap.service.impl;

import com.goldcap.converter.SaveOrderDTOtoOrder;
import com.goldcap.converter.OrderToSaveOrderDTO;
import com.goldcap.exception.EntityIdException;
import com.goldcap.model.Order;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.OrderRepository;
import com.goldcap.repository.RealmRepository;
import com.goldcap.service.OrderService;
import com.goldcap.web.dto.SaveOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GoldcapUserRepository goldcapUserRepository;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private SaveOrderDTOtoOrder toOrder;
    @Autowired
    private OrderToSaveOrderDTO toOrderDTO;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Order saveOrder(SaveOrderDTO saveOrderDTO) {

        // TODO setovati useru order i realmu order ako bude potrebe
//        if (goldcapUserRepository.getById(saveOrderDTO.getGoldcapUserId()) == null) {
//            throw new UserNotFoundException("There is no user with id: " + saveOrderDTO.getGoldcapUserId());
//        }
//        if (realmRepository.getById(saveOrderDTO.getRealmId()) == null) {
//            throw new RealmNotFoundException("There is no realm with id: " + saveOrderDTO.getRealmId());
//        }

        Order order = toOrder.convert(saveOrderDTO);

        assert order != null;
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        try{
            return orderRepository.getOne(id);
        }catch (Exception e){
            logger.error(e.toString());
            throw new EntityIdException("Order with id: " + id  + " does not exist");
        }
    }


    @Override
    public Order getByUUID(String uuid) {
        try {
            return orderRepository.findByUUID(uuid);
        }catch (Exception e){
            logger.error(e.toString());
            throw new EntityIdException("Order with uuid: " + uuid  + " does not exist");
        }

    }

    @Override
    public Page<Order> searchOrders(String buyerName, String realmName, Double goldAmount, Double price, String username , int pageNum , int pageSize , String field , String direction) {

        if(buyerName != null) {
            buyerName = '%' + buyerName + '%';
        }

        if(realmName != null) {
            realmName = '%' + realmName + '%';
        }

        Pageable paging = PageRequest.of(pageNum , pageSize);

        if (direction.equalsIgnoreCase("DESC") && field != null){
            paging = PageRequest.of(pageNum , pageSize, Sort.by(field).descending());
        }
        if (direction.equalsIgnoreCase("ASC") && field != null){
            paging = PageRequest.of(pageNum , pageSize, Sort.by(field).ascending());
        }


        return orderRepository.search( buyerName, realmName, goldAmount , price , username , paging);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        try {
            orderRepository.deleteById(id);
        }catch (Exception e){
            logger.error(e.toString());
            throw new EntityIdException("Order with id: " + id  + " does not exist");
        }
    }
}
