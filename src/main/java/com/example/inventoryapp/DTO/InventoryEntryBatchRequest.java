package com.example.inventoryapp.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntryBatchRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private LocalDate businessDate;

    @NotNull
    private Long createdBy;

    @NotEmpty
    @Size(max = 20, message = "Batch cannot exceed 10 items")
    @Valid
    private List<InventoryEntryItemRequest> items;
}
