package com.example.inventoryapp.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntryRow {
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Integer qtyCases;
    private Integer qtySlaves;
    private Integer qtySingles;
}
