package com.shreyansh.BillingSoftware.repository;

import com.shreyansh.BillingSoftware.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByCategoryId(String categoryId);
}
