package com.example.inventoryapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreInventory {

    @EmbeddedId
    private StoreInventoryId id;

    @MapsId("storeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @MapsId("itemId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private InventoryItem item;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
