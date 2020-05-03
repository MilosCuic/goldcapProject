package com.goldcap.web.controller;

import com.goldcap.converter.OrderToOrderDTO;
import com.goldcap.model.Order;
import com.goldcap.model.Realm;
import com.goldcap.service.OrderService;
import com.goldcap.service.impl.MapValidationErrorService;
import com.goldcap.web.dto.OrderDTO;
import com.goldcap.web.dto.SaveOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderToOrderDTO orderToOrderDTO;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@Valid @RequestBody SaveOrderDTO saveOrderDTO , BindingResult result){

        if (saveOrderDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<?> errorMap = mapValidationErrorService.validateResult(result);
        if (errorMap != null) {
            return errorMap;
        }

        Order order = orderService.saveOrder(saveOrderDTO);

        return new ResponseEntity<>(
                orderToOrderDTO.convert(order),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){

        if (orderService.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderService.getById(id);

        return new ResponseEntity<>(
                orderToOrderDTO.convert(order),
                HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> editOrder(@PathVariable Long id , @RequestBody SaveOrderDTO saveOrderDTO){

        if (saveOrderDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(orderService.getById(id) == null || !id.equals(saveOrderDTO.getId())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Order order = orderService.saveOrder(saveOrderDTO);

        return new ResponseEntity<>(
                orderToOrderDTO.convert(order),
                HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    ResponseEntity<?> delete(@PathVariable Long id){

        Order toDelete = orderService.getById(id);

        if(toDelete == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        orderService.deleteById(id);
        return new ResponseEntity<>("Order with id: " + id + " successfully deleted!" , HttpStatus.OK);
    }

    @RequestMapping(value ="/{pageNum}/{pageSize}" , method=RequestMethod.GET)
    ResponseEntity<List<OrderDTO>> getOrders(
            @PathVariable(name = "pageNum") int pageNum,
            @PathVariable(name = "pageSize") int pageSize,
            @RequestParam(required=false) String buyerName,
            @RequestParam(required=false) String realmName,
            @RequestParam(required=false) Double goldAmount,
            @RequestParam(required=false) Double price,
            @RequestParam(required=false , defaultValue = "id") String field,
            @RequestParam(required=false , defaultValue = "ASC") String direction
            ){

        Page<Order> orderPage = null;

        //TODO staviti principal logovanog umesto username
        orderPage = orderService.searchOrders(buyerName, realmName, goldAmount  , price , null,  pageNum , pageSize , field , direction);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("totalPages", Integer.toString(orderPage.getTotalPages()) );
        responseHeaders.add("totalElements", Long.toString(orderPage.getTotalElements()));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(orderToOrderDTO.convert(orderPage.getContent()));
    }
}