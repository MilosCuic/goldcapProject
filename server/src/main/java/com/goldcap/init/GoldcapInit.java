package com.goldcap.init;

import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Role;
import com.goldcap.repository.GoldcapUserRepository;
import com.goldcap.repository.OrderRepository;
import com.goldcap.repository.RoleRepository;
import com.goldcap.service.impl.RealmServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static com.goldcap.util.Constants.ROLE_ADMIN;
import static com.goldcap.util.Constants.ROLE_SUPER_ADMIN;

@Component
@PropertySource("classpath:application.properties")
public class GoldcapInit {

    private static final Logger logger = LoggerFactory.getLogger(GoldcapInit.class);

    @Autowired
    private GoldcapUserRepository userRepository;
    @Autowired
    private RealmServiceImpl realmService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${admin.username}")
    String adminUserName;
    @Value("${admin.password}")
    String adminPasword;
    @Value("${admin.email}")
    String adminEmail;
    @Value("${goldcap.roles}")
    List<String> roles;

    @PostConstruct
    public void initData() {

        for (String roleName : roles) {
            if (roleRepository.findByName(roleName) != null){
                logger.info(roleName + "already exists skipping...");
            }else {
                Role role = new Role(roleName);
                roleRepository.save(role);
                logger.info("Saving role...." + roleName);
            }
        }

        GoldcapUser admin = userRepository.findGoldcapUserByUsername("admin");
        Role superAdminRole = roleRepository.findByName(ROLE_SUPER_ADMIN);
        Role adminRole = roleRepository.findByName(ROLE_ADMIN);

        if ( admin == null){
            logger.info("Admin does not exists creating admin....");

            admin = new GoldcapUser();
            admin.setFirstName("Milos");
            admin.setLastName("Cuic");
            admin.setUsername(adminUserName);
            admin.setPassword(bCryptPasswordEncoder.encode(adminPasword));
            admin.setEmail(adminEmail);

            userRepository.save(admin);
            logger.info("Admin saved......");

            admin.setRoles(Arrays.asList(adminRole , superAdminRole));

            userRepository.save(admin);
            logger.info("assigning roles to  admin......");
        }else {
            logger.info("admin exists checking roles......");
            if (admin.getRoles().contains(superAdminRole)){
                logger.info("admin has required role");
            }else {
                admin.addRole(superAdminRole);
                logger.info("Assigning roles to  existing admin....");
            }
            if (admin.getRoles().contains(adminRole)){
                logger.info("admin has required role");
            }else {
                admin.addRole(adminRole);
                logger.info("Assigning roles to existing admin....");
            }
            userRepository.save(admin);
        }
    }
}
