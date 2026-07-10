package com.example.inventoryapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventoryentry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntry {

    @EmbeddedId
    private InventoryEntryId id;

    /**
     * FK -> store_inventory(store_id, item_id)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(
                    name = "store_id",
                    referencedColumnName = "store_id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "item_id",
                    referencedColumnName = "item_id",
                    insertable = false,
                    updatable = false
            )
    })
    private StoreInventory storeInventory;

    /**
     * FK -> businessday(store_id, business_date)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(
                    name = "store_id",
                    referencedColumnName = "store_id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(
                    name = "business_date",
                    referencedColumnName = "business_date",
                    insertable = false,
                    updatable = false
            )
    })
    private BusinessDay businessDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "qty_cases")
    private Integer qtyCases;

    @Column(name = "qty_slaves")
    private Integer qtySlaves;

    @Column(name = "qty_singles")
    private Integer qtySingles;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
