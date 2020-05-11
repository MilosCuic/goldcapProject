package com.goldcap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

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
    @Column(nullable = false , length = 255)
    @NotNull(message = "password cant be null")
    @NotEmpty
    private String password;
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "goldcapUser")
    @JsonIgnore
    private List<Order> orders;

    @ManyToMany(cascade = CascadeType.ALL  , fetch = FetchType.EAGER)
    private List<Role> roles;

    public void addRole(Role role){
        if(!roles.contains(role)){
            roles.add(role);
        }
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
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
