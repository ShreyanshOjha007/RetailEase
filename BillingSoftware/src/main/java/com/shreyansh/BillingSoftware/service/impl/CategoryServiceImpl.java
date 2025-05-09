package com.shreyansh.BillingSoftware.service.impl;

import com.shreyansh.BillingSoftware.entity.CategoryEntity;
import com.shreyansh.BillingSoftware.exception.CategoryNotFoundException;
import com.shreyansh.BillingSoftware.io.CategoryRequest;
import com.shreyansh.BillingSoftware.io.CategoryResponse;
import com.shreyansh.BillingSoftware.repository.CategoryRepo;
import com.shreyansh.BillingSoftware.repository.ItemRepo;
import com.shreyansh.BillingSoftware.service.CategoryService;
import com.shreyansh.BillingSoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    private final FileUploadService fileUploadService;

    private final ItemRepo itemRepo;

    @Override
    public CategoryResponse add(CategoryRequest categoryRequest, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(categoryRequest);
        newCategory.setImgUrl(imgUrl);
        try{
            newCategory = categoryRepo.save(newCategory);
        }catch (DataIntegrityViolationException ex){
            System.out.println("Category name or ID already exists.");
        }
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepo.findAll()
                .stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId){
        CategoryEntity existingCategory = categoryRepo.findByCategoryId(categoryId)
                .orElseThrow(()-> new CategoryNotFoundException("Category with "+categoryId+" does not exists"));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepo.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer itemCount = itemRepo.countByCategoryId(newCategory.getId());
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imgUrl(newCategory.getImgUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .items(itemCount)
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest categoryRequest) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .bgColor(categoryRequest.getBgColor())
                .build();
    }
}
