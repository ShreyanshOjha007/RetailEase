package com.shreyansh.BillingSoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyansh.BillingSoftware.io.ItemRequest;
import com.shreyansh.BillingSoftware.io.ItemResponse;
import com.shreyansh.BillingSoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/admin/item/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@RequestPart("item") String itemString , @RequestPart("file")
                                MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest itemRequest = null;
        try{
            itemRequest = objectMapper.readValue(itemString, ItemRequest.class);
            return itemService.add(itemRequest,file);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error occurred while mapping the object "+ e.getMessage());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occurred while mapping the object " + e.getMessage());
        }
    }

    @GetMapping("/item/readAll")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ItemResponse> fetchItems(){
        return itemService.fetchItems();
    }

    @DeleteMapping("/admin/item/delete/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String itemId){
        try{
            itemService.deleteItem(itemId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"item not found with itemID" + itemId);
        }
    }
}
