package com.example.inventoryapp.Configuration;

import com.example.inventoryapp.Service.InventoryWriteStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class InventoryWriteStrategyConfig {

    @Bean
    @Primary
    public InventoryWriteStrategy activeInventoryWriteStrategy(
            @Value("${inventory.write.strategy:sync}") String strategyName,
            @Qualifier("syncInventoryWriteStrategy") InventoryWriteStrategy sync

    ) {
        return switch (strategyName) {

            default -> sync;
        };
    }
}
