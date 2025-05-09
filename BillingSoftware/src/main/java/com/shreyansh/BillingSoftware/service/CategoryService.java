package com.shreyansh.BillingSoftware.service;

import com.shreyansh.BillingSoftware.io.CategoryRequest;
import com.shreyansh.BillingSoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse add(CategoryRequest categoryRequest, MultipartFile file);

    List<CategoryResponse> getAllCategories();

    void delete(String categoryId);
}
