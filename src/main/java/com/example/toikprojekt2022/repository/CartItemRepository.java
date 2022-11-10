package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.CartItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, UUID> {
    @Query("SELECT ci FROM CartItem ci INNER join User u ON u.userId = ci.cartOwnerId  WHERE u.login = ?1")
    Iterable<CartItem> findAllByCartOwnerLogin(String login);
    @Modifying
    @Transactional
    @Query("delete from CartItem ci WHERE ci.cartItemId = ?1")
    void deleteByCartItemId(UUID id);
}
