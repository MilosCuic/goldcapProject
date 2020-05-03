package com.goldcap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Proxy(lazy=false)
public class Realm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    @NotBlank(message = "Please enter realm name")
    @NotNull(message = "Realm name cant be null")
    private String name;
    @Column
    private String type;
    @Column
    private String timeZone;
    @Column
    private String language;
    @Column
    private String status;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , mappedBy = "realm" , orphanRemoval = false)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();



}
