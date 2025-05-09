package com.shreyansh.BillingSoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyansh.BillingSoftware.io.CategoryRequest;
import com.shreyansh.BillingSoftware.io.CategoryResponse;
import com.shreyansh.BillingSoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString,
                                        @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper =  new ObjectMapper();
        CategoryRequest categoryRequest = null;
        System.out.println("Incoming JSON string: " + categoryString);
        try{
            categoryRequest = objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.add(categoryRequest,file);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error occurred while mapping the object "+ e.getMessage());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while mapping the object " + e.getMessage());
        }
    }

    @GetMapping("/categories/getAll")
    public List<CategoryResponse> fetchCategory(){
        return categoryService.getAllCategories();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/categories/delete/{categoryId}")
    public void deleteCategory(@PathVariable String categoryId){
        categoryService.delete(categoryId);
    }

}
