package com.goldcap.repositoryTests;

import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Order;
import com.goldcap.model.Realm;
import com.goldcap.repository.OrderRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Order initData() {
        GoldcapUser goldcapUser = new GoldcapUser();
        goldcapUser.setFirstName("Milos");
        goldcapUser.setEmail("Test@gmail.com");
        goldcapUser.setUsername("username");
        goldcapUser.setPassword("asdqwe123");

        Realm realm = new Realm();
        realm.setName("Test realm");

        Order order = new Order();
        order.setGoldcapUser(goldcapUser);
        order.setRealm(realm);
        order.setPrice(30.00);
        order.setBuyerName("Totally unique buyer name");
        order.setGoldAmount(200000.00);
        order.setDateRequested(new Date());

        return order;
    }

    @Test
    public void testSave(){

        Order persistedOrder = initData();
        String randomUUID = UUID.randomUUID().toString();
        persistedOrder.setUUID(randomUUID);

        orderRepository.save(persistedOrder);


        Order savedOrder = orderRepository.findByUUID(randomUUID);
        Optional<Order> nonExistingOrder = orderRepository.findById(5555L);

        assertNotNull(savedOrder);
        assertEquals(Optional.empty() , nonExistingOrder);
        assertEquals("Milos" , savedOrder.getGoldcapUser().getFirstName());
        assertEquals("Test realm" , savedOrder.getRealm().getName());

    }

    // cant save 2 orders with same UUID
    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueUUIDSave() {
        try {
            String randomUUID = UUID.randomUUID().toString();
            Order persistedOrder = initData();
            persistedOrder.setUUID(randomUUID);

            orderRepository.save(persistedOrder);

            Order persistedOrder2 = initData();
            persistedOrder2.setUUID(randomUUID);

            orderRepository.save(persistedOrder2);

            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("Operation Not Supported", e.getMessage());
        }

    }

    @Test
    public void testDeleteOrder() {
        String randomUUID = UUID.randomUUID().toString();
        Order persistedOrder = initData();
        persistedOrder.setUUID(randomUUID);

        orderRepository.save(persistedOrder);

        Order toDelete = orderRepository.findByUUID(randomUUID);
        assertNotNull(toDelete);
        orderRepository.delete(toDelete);
        assertNull(orderRepository.findByUUID(randomUUID));

    }

    @Test
    public void findAllOrders() {
        String randomUUID1 = UUID.randomUUID().toString();
        String randomUUID2 = UUID.randomUUID().toString();

        GoldcapUser goldcapUser2 = new GoldcapUser();
        goldcapUser2.setFirstName("Milos");
        goldcapUser2.setUsername("Milos92");
        goldcapUser2.setEmail("nesto@gmail.com");
        goldcapUser2.setPassword("asdqwe123");

        Realm realm = new Realm();
        realm.setName("New Realm 2");

        Order order1 = initData();
        order1.setUUID(randomUUID1);

        Order order2 = initData();
        order2.setUUID(randomUUID2);

        order2.setRealm(realm);
        order2.setGoldcapUser(goldcapUser2);

        assertNotEquals(randomUUID1 , randomUUID2);
        assertEquals( 0 , orderRepository.findAll().size());


        orderRepository.save(order1);
        orderRepository.save(order2);
        assertNotNull(orderRepository.findAll());
        assertEquals( 2 , orderRepository.findAll().size());
    }

    @Test
    public void testPojoValidationFail() {

        //Properties that require validation buyerName , price , UUID , goldAmount
        Order order = new Order();

        //to-do add realm and user to validation
        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(4 , violations.size());

        Order order2 = new Order();
        order2.setUUID(UUID.randomUUID().toString());
        order2.setBuyerName("Milos");
        order2.setPrice(0.0);
        order2.setGoldAmount(85123.24);

        Set<ConstraintViolation<Order>> violations2 = validator.validate(order2);
        assertEquals(2 , violations2.size());

    }

    @Test
    public void testPojoValidationSuccess(){
        Order order = new Order();
        order.setUUID(UUID.randomUUID().toString());
        order.setBuyerName("Milos");
        order.setPrice(30.00);
        order.setGoldAmount(200000.00);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testOrderPricePer100k(){
        Order order = new Order();
        String uuid = UUID.randomUUID().toString();
        order.setUUID(uuid);
        order.setBuyerName("Milos");
        order.setPrice(30.00);
        order.setGoldAmount(200000.00);
        Double result = 15.00;

        orderRepository.save(order);

        Order sameOrder = orderRepository.findByUUID(uuid);
        assertEquals(result, sameOrder.getPricePer100k());

    }
}
