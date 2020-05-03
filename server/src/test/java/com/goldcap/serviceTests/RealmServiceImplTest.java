//package com.goldcap.serviceTests;
//
//import com.goldcap.model.Realm;
//import com.goldcap.repository.RealmRepository;
//import com.goldcap.service.impl.RealmServiceImpl;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class RealmServiceImplTest {
//
//    @InjectMocks
//    private RealmServiceImpl realmService;
//
//    @Mock
//    private RealmRepository realmRepository;
//
//    @BeforeEach
//    public void setup(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    public List<Realm> initData() {
//        List<Realm> realms = new ArrayList<>();
//
//        Realm realm1 = new Realm();
//        realm1.setId(1L);
//        realm1.setName("TestName");
//        realm1.setType("PvP");
//
//        Realm realm2 = new Realm();
//        realm2.setId(2L);
//        realm2.setName("TestName22");
//        realm2.setType("PvE");
//
//        realms.add(realm1);
//        realms.add(realm2);
//
//        return realms;
//    }
//
//    @Test
//    public void testSaveRealm() {
//
//        Realm persisted = initData().get(0);
//
//
//        when(realmRepository.findByName(anyString())).thenReturn(persisted);
//
//        Realm foundRealm = realmService.findByName(persisted.getName());
//
//        assertNotNull(foundRealm);
//        assertEquals("TestName" , foundRealm.getName());
//
//
//    }
//
//    @Test
//    public void findAll() {
//        List<Realm> realms = initData();
//        when(realmRepository.findAll()).thenReturn(realms);
//        List<Realm> foundRealms = realmService.findAll();
//
//       verify(realmRepository, times(1))
//                .findAll();
//
//        assertNotNull(foundRealms);
//        assertEquals(2 , foundRealms.size());
//        assertEquals("TestName22" , foundRealms.get(1).getName());
//    }
//
//    @Test
//    public void findByNonExistingId() {
//
//        Long id = 1112345L;
//        when(realmRepository.getOne(id)).thenThrow(new RealmNotFoundException("Realm with id: " + id + " does not exists."));
//
//        assertThrows(RealmNotFoundException.class, () -> {
//            realmService.getById(id);
//        });
//    }
//
//    @Test
//    public void findByNonExistingName() {
//        String name = "non existing name";
//        when(realmRepository.findByName(name)).thenThrow(new RealmNotFoundException("Realm with name: " + name + " does not exists."));
//
//        assertThrows(RealmNotFoundException.class, () -> {
//            realmService.findByName(name);
//        });
//
//        try {
//            Realm realm = realmService.findByName(name);
//        }catch (RealmNotFoundException e){
//            assertEquals(e.getMessage() , "Realm with name: " + name + " does not exists.");
//        }
//
//    }
//}
