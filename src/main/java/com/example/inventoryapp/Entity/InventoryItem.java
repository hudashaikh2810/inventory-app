package com.example.inventoryapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "inventoryitem",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "inventoryitem_sku_key",
                        columnNames = "sku"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "inventory_item_seq"
    )
    @SequenceGenerator(
            name = "inventory_item_seq",
            sequenceName = "inventoryitem_item_id_seq",
            allocationSize = 1
    )
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "sku", nullable = false, length = 100, unique = true)
    private String sku;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "uom_case_size")
    private Integer uomCaseSize;

    @Column(name = "uom_slave_size")
    private Integer uomSlaveSize;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
