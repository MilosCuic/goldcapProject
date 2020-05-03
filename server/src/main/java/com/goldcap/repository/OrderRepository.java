package com.goldcap.repository;

import com.goldcap.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUUID(String UUID);

    @Modifying
    @Transactional
    @Query(value="delete from Order o where o.id = :id")
    void deleteById(@RequestParam Long id);

    @Query("SELECT o FROM Order o WHERE "
            + "(:buyerName IS NULL or o.buyerName like :buyerName ) AND "
            + "(:realmName IS NULL OR o.realm.name like :realmName) AND "
            + "(:goldAmount IS NULL OR o.goldAmount >= :goldAmount) AND"
            + "(:username IS NULL OR o.goldcapUser.username like :username) AND"
            + "(:price IS NULL OR o.price >= :price)"

    )
    Page<Order> search(
            @Param("buyerName") String buyerName,
            @Param("realmName") String realmName,
            @Param("goldAmount") Double goldAmount,
            @Param("price") Double price,
            @Param("username") String username,
            Pageable pageRequest);
}
