package com.goldcap.repositoryTests;

import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Order;
import com.goldcap.model.Realm;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.validator.GoldcapUserValidator;
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
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class GoldcapUserTest {

    @Autowired
    private GoldcapUserRepository userRepository;

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private GoldcapUser initData() {
        GoldcapUser goldcapUser = new GoldcapUser();
        goldcapUser.setFirstName("Milos");
        goldcapUser.setUsername("Milos92");
        goldcapUser.setEmail("nesto@gmail.com");
        goldcapUser.setPassword("asdqwe123");
        return goldcapUser;
    }

    @Test
    public void saveUserTest(){
        GoldcapUser persisted = initData();

        userRepository.save(persisted);

        GoldcapUser savedUser = userRepository.findByEmail(persisted.getEmail());

        assertEquals("Milos" , savedUser.getFirstName());
        assertEquals("nesto@gmail.com" , savedUser.getEmail());
        assertNotNull(savedUser);

    }
    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueUserName() {

        try {
            GoldcapUser goldcapUser = new GoldcapUser();
            goldcapUser.setFirstName("Milos");
            goldcapUser.setUsername("Milos92");
            goldcapUser.setEmail("nesto@gmail.com");
            goldcapUser.setPassword("asdqwe123");
            userRepository.save(goldcapUser);

            GoldcapUser goldcapUser2 = new GoldcapUser();
            goldcapUser2.setFirstName("Milos");
            goldcapUser2.setUsername("Milos92");
            goldcapUser2.setEmail("nesto@gmail.com");
            goldcapUser2.setPassword("asdqwe123");
            userRepository.save(goldcapUser2);

            fail("Exception not thrown");
        } catch (ConstraintViolationException e) {
            assertEquals("Operation Not Supported", e.getMessage());
        }
    }

    @Test
    public void testDeleteUser() {

        GoldcapUser persistedUser = initData();


        userRepository.save(persistedUser);

        GoldcapUser toDelete = userRepository.findByEmail(persistedUser.getEmail());
        assertNotNull(toDelete);
        userRepository.delete(toDelete);
        assertNull(userRepository.findByEmail(persistedUser.getEmail()));

    }
    @Test
    public void findAllUsers() {

        GoldcapUser user = initData();

        GoldcapUser goldcapUser2 = new GoldcapUser();
        goldcapUser2.setFirstName("Milos");
        goldcapUser2.setUsername("Milos922");
        goldcapUser2.setEmail("nesto2@gmail.com");
        goldcapUser2.setPassword("asdqwe123");

        assertEquals( 2 , userRepository.findAll().size());


        userRepository.save(user);
        userRepository.save(goldcapUser2);
        assertNotNull(userRepository.findAll());
        assertEquals( 4 , userRepository.findAll().size());
    }

    @Test
    public void testPojoValidation() {

        //Properties that require validation username , password , email x2 for blank and null(6 total)
        GoldcapUser user = new GoldcapUser();

        //to-do add realm and user to validation
        Set<ConstraintViolation<GoldcapUser>> violations = validator.validate(user);

        System.out.println(violations);
        assertEquals(6 , violations.size());

        GoldcapUser validatedUser = initData();

        Set<ConstraintViolation<GoldcapUser>> violations2 = validator.validate(validatedUser);
        assertEquals(0 , violations2.size());

    }
}
