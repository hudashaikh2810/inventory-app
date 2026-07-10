package com.example.inventoryapp.Repository;

import com.example.inventoryapp.DTO.InventoryEntryItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InventoryEntryWriteJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String UPSERT_SQL = """
            INSERT INTO inventoryentry
                (store_id, item_id, business_date, qty_cases, qty_slaves, qty_singles,
                 created_by, version, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, 0, now(), now())
            ON CONFLICT (store_id, item_id, business_date)
            DO UPDATE SET
                qty_cases  = EXCLUDED.qty_cases,
                qty_slaves = EXCLUDED.qty_slaves,
                qty_singles = EXCLUDED.qty_singles,
                created_by = EXCLUDED.created_by,
                updated_at = now()
            """;

    public int[][] batchUpsert(Long storeId, LocalDate businessDate, Long createdBy,
                             List<InventoryEntryItemRequest> items) {
        return jdbcTemplate.batchUpdate(UPSERT_SQL, items, items.size(),
                (ps, item) -> {
                    ps.setLong(1, storeId);
                    ps.setLong(2, item.getItemId());
                    ps.setObject(3, businessDate);
                    ps.setInt(4, item.getQtyCases());
                    ps.setInt(5, item.getQtySlaves());
                    ps.setInt(6, item.getQtySingles());
                    ps.setLong(7, createdBy);
                });
    }
}
