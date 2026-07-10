package com.example.inventoryapp.Service;

import com.example.inventoryapp.DTO.InventoryEntryBatchRequest;

public interface InventoryWriteStrategy {
    void submit(InventoryEntryBatchRequest request);
}
