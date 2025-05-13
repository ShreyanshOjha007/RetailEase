package com.shreyansh.BillingSoftware.repository;

import com.shreyansh.BillingSoftware.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemEntityRepo extends JpaRepository<OrderItemEntity,Long> {
}
