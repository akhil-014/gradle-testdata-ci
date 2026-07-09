package com.ust.sdet.tests;

import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private static final OrderRepository repo = new OrderRepository();
    private static final OrderFactory factory = new OrderFactory(repo);

    @BeforeAll
    static void migrateDatabase() {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getProperty("db.url", "jdbc:mysql://localhost:3306/testorderdb"),
                        System.getProperty("db.user", "root"),
                        System.getProperty("db.password", "admin")
                )
                .load();

        flyway.migrate();
    }

    @BeforeEach
    void setup() {
        repo.reset();
    }

    @Test
    void createsOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .withQty(3)
                        .build()
        );

        assertEquals(1, repo.count());
    }

    @Test
    void countsOrders() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        assertEquals(1, repo.count());
    }
}