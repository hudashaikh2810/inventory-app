package com.example.inventoryapp.Controller;

import com.example.inventoryapp.DTO.InventoryEntryBatchRequest;
import com.example.inventoryapp.DTO.InventoryEntryRow;
import com.example.inventoryapp.DTO.PagedResponse;
import com.example.inventoryapp.Service.InventoryEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/stores/{storeId}/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryEntryController {

    private final InventoryEntryService inventoryReportService;



    /**
     * Returns a paginated view of a store's active inventory items for a given business date,
     * combined with that day's counted quantities.
     */
    @GetMapping
    public ResponseEntity<PagedResponse<InventoryEntryRow>> getStoreInventory(
            @PathVariable Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate businessDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        log.info("GET inventory - storeId={}, businessDate={}, page={}, size={}",
                storeId, businessDate, page, size);

        PagedResponse<InventoryEntryRow> response =
                inventoryReportService.getStoreInventoryPage(storeId, businessDate, page, size);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> submitInventoryBatch(
            @PathVariable Long storeId,
            @Valid @RequestBody InventoryEntryBatchRequest request
    ) {
        if (!storeId.equals(request.getStoreId())) {
            log.warn("Path storeId={} does not match body storeId={}", storeId, request.getStoreId());
            throw new IllegalArgumentException("storeId in path must match storeId in body");
        }

        log.info("POST batch inventory write - storeId={}", storeId);
        inventoryReportService.submitInventoryBatch(request);
        return ResponseEntity.ok().build();
    }

}
