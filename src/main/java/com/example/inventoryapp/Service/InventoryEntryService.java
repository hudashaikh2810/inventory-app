package com.example.inventoryapp.Service;

import com.example.inventoryapp.DTO.InventoryEntryBatchRequest;
import com.example.inventoryapp.DTO.InventoryEntryItemRequest;
import com.example.inventoryapp.DTO.InventoryEntryRow;
import com.example.inventoryapp.DTO.PagedResponse;
import com.example.inventoryapp.Entity.InventoryEntry;
import com.example.inventoryapp.Repository.InventoryEntryJDBCRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEntryService {

    private final InventoryEntryJDBCRepository inventoryReportJdbcRepository;
    private final InventoryWriteStrategy inventoryWriteStrategy;


    private static final int MAX_PAGE_SIZE = 6;
    private static final int DEFAULT_PAGE_SIZE = 6;

    /**
     * Fetches a paginated view of a store's active inventory items for a given business date,
     * combining item master data with that day's counted quantities (0 where no entry exists yet).
     */
    public PagedResponse<InventoryEntryRow> getStoreInventoryPage(
            Long storeId,
            LocalDate businessDate,
            int page,
            int size
    ) {
        validateRequest(storeId, businessDate, page, size);
        int safeSize = Math.min(size <= 0 ? DEFAULT_PAGE_SIZE : size, MAX_PAGE_SIZE);
        int offset = page * safeSize;

        log.info("Fetching inventory page - storeId={}, businessDate={}, page={}, size={}",
                storeId, businessDate, page, safeSize);

        long startTime = System.currentTimeMillis();

        List<InventoryEntryRow> rows = inventoryReportJdbcRepository.findStoreInventoryPage(
                storeId, businessDate, safeSize, offset
        );
        long totalElements = inventoryReportJdbcRepository.countStoreInventory(storeId);

        long elapsedMs = System.currentTimeMillis() - startTime;
        log.info("Fetched {} rows (total={}) for storeId={}, businessDate={} in {}ms",
                rows.size(), totalElements, storeId, businessDate, elapsedMs);

        if (rows.isEmpty() && offset > 0 && offset >= totalElements) {
            log.warn("Requested page={} is beyond available data - storeId={}, totalElements={}",
                    page, storeId, totalElements);
        }

        return PagedResponse.of(rows, page, safeSize, totalElements);
    }

    private void validateRequest(Long storeId, LocalDate businessDate, int page, int size) {
        if (storeId == null || storeId <= 0) {
            log.error("Invalid storeId received: {}", storeId);
            throw new IllegalArgumentException("storeId must be a positive number");
        }
        if (businessDate == null) {
            log.error("Missing businessDate for storeId={}", storeId);
            throw new IllegalArgumentException("businessDate is required");
        }
        if (businessDate.isAfter(LocalDate.now())) {
            log.error("Future businessDate requested: storeId={}, businessDate={}", storeId, businessDate);
            throw new IllegalArgumentException("businessDate cannot be in the future");
        }
        if (page < 0) {
            log.error("Invalid page requested: {}", page);
            throw new IllegalArgumentException("page cannot be negative");
        }
    }
    public void submitInventoryBatch(InventoryEntryBatchRequest request) {
        List<InventoryEntryItemRequest> list=request.getItems();
        Comparator<InventoryEntryItemRequest> comparator=Comparator.comparing(InventoryEntryItemRequest::getItemId);

        list.sort(comparator);
request.setItems(list);
        inventoryWriteStrategy.submit(request);
    }
}
