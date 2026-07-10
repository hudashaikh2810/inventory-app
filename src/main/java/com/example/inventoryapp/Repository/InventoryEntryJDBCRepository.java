package com.example.inventoryapp.Repository;

import com.example.inventoryapp.DTO.InventoryEntryRow;
import com.example.inventoryapp.DTO.InventoryEntryRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryEntryJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_STORE_INVENTORY_PAGE_SQL = """
            SELECT si.item_id,
                   ii.item_name,
                   ii.item_description,
                   COALESCE(ie.qty_cases, 0)  AS qty_cases,
                   COALESCE(ie.qty_slaves, 0) AS qty_slaves,
                   COALESCE(ie.qty_singles, 0) AS qty_singles
            FROM store_inventory si
            JOIN inventoryitem ii
                ON si.item_id = ii.item_id
            LEFT JOIN inventoryentry ie
                ON ie.store_id = si.store_id
                AND ie.item_id = si.item_id
                AND ie.business_date = ?
            WHERE si.store_id = ?
              AND si.is_active = TRUE
              AND ii.is_active = TRUE
              AND si.item_id > 0
            ORDER BY si.item_id
            LIMIT ? OFFSET ?
            """;

    private static final String COUNT_STORE_INVENTORY_SQL = """
            SELECT COUNT(*)
            FROM store_inventory si
            JOIN inventoryitem ii
                ON si.item_id = ii.item_id
            WHERE si.store_id = ?
              AND si.is_active = TRUE
              AND ii.is_active = TRUE
              AND si.item_id > 0
            """;

    public List<InventoryEntryRow> findStoreInventoryPage(
            Long storeId,
            LocalDate businessDate,
            int limit,
            int offset
    ) {
        return jdbcTemplate.query(
                FIND_STORE_INVENTORY_PAGE_SQL,
                new InventoryEntryRowMapper(),
                businessDate, storeId, limit, offset
        );
    }

    public long countStoreInventory(Long storeId) {
        Long count = jdbcTemplate.queryForObject(
                COUNT_STORE_INVENTORY_SQL,
                Long.class,
                storeId
        );
        return count != null ? count : 0L;
    }
}
