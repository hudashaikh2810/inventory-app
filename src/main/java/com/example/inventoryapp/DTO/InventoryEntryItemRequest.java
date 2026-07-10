package com.example.inventoryapp.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryEntryItemRequest {

    @NotNull
    private Long itemId;

    @NotNull
    @Min(0)
    private Integer qtyCases;

    @NotNull
    @Min(0)
    private Integer qtySlaves;

    @NotNull
    @Min(0)
    private Integer qtySingles;
}
