package com.example.inventoryapp.Service;

import com.example.inventoryapp.DTO.InventoryEntryBatchRequest;
import com.example.inventoryapp.DTO.InventoryEntryItemRequest;
import com.example.inventoryapp.Repository.InventoryEntryWriteJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Component("syncInventoryWriteStrategy")
@RequiredArgsConstructor
@Slf4j
public class SyncInventoryWriteStrategy implements InventoryWriteStrategy {

    private final InventoryEntryWriteJdbcRepository writeRepository;

    @Override
    @Transactional
    public void submit(InventoryEntryBatchRequest request) {

        log.info("[SYNC] Submitting batch - storeId={}, itemCount={}",
                request.getStoreId(), request.getItems().size());

        long startTime = System.currentTimeMillis();
        writeRepository.batchUpsert(
                request.getStoreId(), request.getBusinessDate(),
                request.getCreatedBy(),request.getItems()
        );

        log.info("[SYNC] Batch committed - storeId={}, timeTakenMs={}",
                request.getStoreId(), System.currentTimeMillis() - startTime);
    }
}
