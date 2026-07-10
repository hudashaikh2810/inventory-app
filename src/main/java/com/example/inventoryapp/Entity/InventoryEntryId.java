package com.example.inventoryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InventoryEntryId implements Serializable {

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "business_date")
    private LocalDate businessDate;
}
