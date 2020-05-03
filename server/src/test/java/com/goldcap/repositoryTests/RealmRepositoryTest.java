package com.goldcap.repositoryTests;

import com.goldcap.model.Order;
import com.goldcap.model.Realm;
import com.goldcap.repository.RealmRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//If you want to run the tests against the actually registered database,
//you can annotate the test with @AutoConfigureTestDatabase(replace=Replace.NONE),
//which will use the registered DataSource instead of an in-memory data source.
public class RealmRepositoryTest {

    @Autowired
    private RealmRepository realmRepository;

    @Test
    public void testSaveRealm() {

        Realm realmToSave = new Realm();

        realmToSave.setName("testName");
        realmToSave.setType("testType");
        realmToSave.setLanguage("en");
        realmToSave.setTimeZone("-05:00");

        realmRepository.save(realmToSave);

        // when
        Realm foundByName = realmRepository.findByName(realmToSave.getName());
        Realm foundByType = realmRepository.findByName(realmToSave.getName());
        Realm findByNonExistingName = realmRepository.findByName("SomeFakeName");

        // then
        assertNotNull(foundByName);
        assertNotNull(foundByType);
        assertNotNull(foundByName);

        assertNull(findByNonExistingName);

        assertEquals(realmToSave.getId(), foundByName.getId());
        assertEquals(realmToSave.getId(), foundByType.getId());
        assertEquals(realmToSave.getName(), foundByType.getName());
        assertEquals(realmToSave.getType(), foundByName.getType());
        assertEquals(realmToSave.getLanguage(), foundByName.getLanguage());
        assertEquals(realmToSave.getTimeZone(), foundByName.getTimeZone());
        
    }
    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueNameSave() {

            try {
                Realm realmWithUniqueName = new Realm();
                realmWithUniqueName.setName("Unique");
                realmRepository.save(realmWithUniqueName);

                Realm realmWithSameName = new Realm();
                realmWithSameName.setName("Unique");
                realmRepository.save(realmWithSameName);

                fail("Exception not thrown");
            } catch (ConstraintViolationException e) {
                assertEquals("Operation Not Supported", e.getMessage());
            }

    }

    @Test
    public void testDeleteRealm() {
        Realm realm = new Realm();
        realm.setName("realmToDelete");
        realm.setType("PvP");

        realmRepository.save(realm);
        Realm toDelete = realmRepository.findByName("realmToDelete");
        realmRepository.delete(toDelete);
        assertNull(realmRepository.findByName("realmToDelete"));

    }

    @Test
    public void findAllRealms() {
        Realm realm = new Realm();
        realm.setName("Realm1");
        Realm realm2 = new Realm();
        realm2.setName("Realm2");

        Integer size = realmRepository.findAll().size();
        realmRepository.save(realm);
        realmRepository.save(realm2);
        assertNotNull(realmRepository.findAll());
        assertEquals( size + 2 , realmRepository.findAll().size());
    }


}