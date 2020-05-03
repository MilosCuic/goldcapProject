package com.goldcap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@Proxy(lazy=false)
public class GoldcapUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Email
    @NotNull(message = "e-mail cant be null")
    @NotEmpty(message = "e-mail field is required")
    @Column(nullable = false , unique = true)
    private String email;
    @NotNull(message = "username cant be null")
    @NotEmpty(message = "username field is required")
    @Column(nullable = false , unique = true)
    private String username;
    @Column(nullable = false)
    @NotNull(message = "password cant be null")
    @NotEmpty
    @Size(min = 6 , max = 30)
    private String password;


    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "goldcapUser" , orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order){
        order.setGoldcapUser(this);
        this.orders.add(order);
    }

    /*
    *
    UserDetails interface methods
    *
    *
    * */

    //TODO add roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority role1 = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority role2 = new SimpleGrantedAuthority("ROLE_ADMIN");

        authorities.add(role1);
        authorities.add(role2);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
