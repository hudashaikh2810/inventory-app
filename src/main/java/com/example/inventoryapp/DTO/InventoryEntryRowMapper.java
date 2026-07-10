package com.example.inventoryapp.DTO;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryEntryRowMapper implements RowMapper<InventoryEntryRow> {

    @Override
    public InventoryEntryRow mapRow(ResultSet rs, int rowNum) throws SQLException {
        return InventoryEntryRow.builder()
                .itemId(rs.getLong("item_id"))
                .itemName(rs.getString("item_name"))
                .itemDescription(rs.getString("item_description"))
                .qtyCases(rs.getInt("qty_cases"))
                .qtySlaves(rs.getInt("qty_slaves"))
                .qtySingles(rs.getInt("qty_singles"))
                .build();
    }
}
