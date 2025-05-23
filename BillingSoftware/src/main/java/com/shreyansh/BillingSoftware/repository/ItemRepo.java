package com.shreyansh.BillingSoftware.repository;

import com.shreyansh.BillingSoftware.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<ItemEntity,Long> {

    Optional<ItemEntity> findByItemId(String itemId);

    Integer countByCategoryId(Long id);
}
