package com.example.toikprojekt2022.repository;

import com.example.toikprojekt2022.model.CartItem;
import org.hibernate.engine.jdbc.spi.ConnectionObserverAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends PagingAndSortingRepository<CartItem, UUID> {
    @Query("SELECT ci FROM CartItem ci INNER join User u ON u.userId = ci.cartOwnerId  WHERE u.login = ?1")
    Page<CartItem> findAllByCartOwnerLogin(String login, Pageable pageable);

    @Query("select ci from CartItem ci inner join User u on u.userId = ci.cartOwnerId where u.login = ?1 and ci.cartItemId = ?2")
    Optional<CartItem> findByOwnerLoginAndCartItemId(String ownerLogin, UUID cartItemId);

    @Query("select ci from CartItem ci inner join User u on u.userId = ci.cartOwnerId where u.login = ?1 and ci.dishId = ?2")
    Optional<CartItem> findByOwnerLoginAndDishId(String ownerLogin, UUID dishId);

    @Modifying
    @Transactional
    @Query("delete from CartItem ci WHERE ci.cartItemId = ?1")
    void deleteByCartItemId(UUID id);
}
