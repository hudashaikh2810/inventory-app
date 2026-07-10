package com.example.inventoryapp.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StoreInventoryId implements Serializable {

    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "item_id")
    private Integer itemId;
}
