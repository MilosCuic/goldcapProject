package com.goldcap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Role can be blank")
    @NotNull(message = "Role name cant be null")
    private String name;

    @ManyToMany(mappedBy = "roles" , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GoldcapUser> users;

    public Role(){}

    public Role(String name){
        this.name = name;
    }

//    public void addUser(GoldcapUser user){
//        if (!users.contains(user)) {
//            users.add(user);
//            user.addRoke(this);
//        }
//    }


    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Role)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Role c = (Role) o;

        // Compare the data members and return accordingly
        return name.equals(c.name);
    }
}
