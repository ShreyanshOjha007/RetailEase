package com.shreyansh.BillingSoftware.service.impl;

import com.shreyansh.BillingSoftware.entity.CategoryEntity;
import com.shreyansh.BillingSoftware.entity.ItemEntity;
import com.shreyansh.BillingSoftware.exception.CategoryNotFoundException;
import com.shreyansh.BillingSoftware.io.ItemRequest;
import com.shreyansh.BillingSoftware.io.ItemResponse;
import com.shreyansh.BillingSoftware.repository.CategoryRepo;
import com.shreyansh.BillingSoftware.repository.ItemRepo;
import com.shreyansh.BillingSoftware.service.FileUploadService;
import com.shreyansh.BillingSoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;

    private final CategoryRepo categoryRepo;

    private final FileUploadService fileUploadService;

    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity existingCategory = categoryRepo.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("User not found " + request.getCategoryId()));
        newItem.setCategory(existingCategory);
        newItem.setImgUrl(imgUrl);
        itemRepo.save(newItem);
        return convertToResponse(newItem);

    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepo.findAll().stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepo.findByItemId(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found with item id" + itemId));
        boolean isFileDeleted = fileUploadService.deleteFile(existingItem.getImgUrl());
        if(isFileDeleted){
            itemRepo.delete(existingItem);
        }else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete the image");
        }
    }

    private ItemEntity convertToEntity(ItemRequest itemRequest){
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .build();
    }


    private ItemResponse convertToResponse(ItemEntity itemEntity) {
        return ItemResponse.builder()
                .name(itemEntity.getName())
                .categoryId(itemEntity.getCategory().getCategoryId())
                .categoryName(itemEntity.getCategory().getName())
                .price(itemEntity.getPrice())
                .itemId(itemEntity.getItemId())
                .createdAt(itemEntity.getCreatedAt())
                .imgUrl(itemEntity.getImgUrl())
                .updatedAt(itemEntity.getUpdatedAt())
                .description(itemEntity.getDescription())
                .build();
    }


}
