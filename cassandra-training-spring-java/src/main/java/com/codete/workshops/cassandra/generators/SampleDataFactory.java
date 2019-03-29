package com.codete.workshops.cassandra.generators;

import com.codete.workshops.cassandra.model.*;
import com.codete.workshops.cassandra.model.types.EventTarget;
import com.codete.workshops.cassandra.model.types.EventType;
import com.codete.workshops.cassandra.model.types.Gender;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codete.workshops.cassandra.generators.DataGenerator.SCHEMALESS_MAX_COUNT;
import static com.codete.workshops.cassandra.generators.DataGenerator.SCHEMALESS_MIN_COUNT;

public class SampleDataFactory {

    public static VisitorAction createVisitorAction(boolean withSchemaLessFields) {
        ActionEvent event = ActionEvent.builder()
                .eventType(EventType.values()[RandomUtils.nextInt(0, EventType.values().length - 1)])
                .eventTarget(EventTarget.values()[RandomUtils.nextInt(0, EventTarget.values().length - 1)])
                .build();

        if (withSchemaLessFields) {
            Map<String, String> map = IntStream
                    .range(0, RandomUtils.nextInt(SCHEMALESS_MIN_COUNT, SCHEMALESS_MAX_COUNT)).boxed()
                    .collect(Collectors.toMap(i -> RandomStringUtils.random(2), i -> RandomStringUtils.random(15)));
            event.setAdditional(map);
        }

        return VisitorAction.builder()
                .id(UUID.randomUUID())
                .visitorId(UUID.randomUUID())
                .event(event)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Visitor createVisitor(boolean withSchemaLessFields) {
        Visitor visitor = Visitor.builder()
                .id(UUID.randomUUID())
                .age(RandomUtils.nextInt(1, 90))
                .existingCustomerId(UUID.randomUUID())
                .gender(Gender.values()[RandomUtils.nextInt(0, Gender.values().length - 1)])
                .ip(RandomStringUtils.random(10))
                .location(RandomStringUtils.random(10))
                .profession(RandomStringUtils.random(10))
                .timestamp(LocalDateTime.now())
                .build();

        if (withSchemaLessFields) {
            Map<String, String> map = IntStream
                    .range(0, RandomUtils.nextInt(SCHEMALESS_MIN_COUNT, SCHEMALESS_MAX_COUNT)).boxed()
                    .collect(Collectors.toMap(i -> RandomStringUtils.random(2), i -> RandomStringUtils.random(15)));
            visitor.setAdditional(map);
        }

        return visitor;
    }

    public static Order createOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .productID(UUID.randomUUID())
                .shopId(UUID.randomUUID())
                .quantity(RandomUtils.nextInt())
                .unitPrice(BigDecimal.valueOf(RandomUtils.nextDouble()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static Shop createShop(boolean withSchemaLessFields) {
        Shop shop = Shop.builder()
                .id(UUID.randomUUID())
                .name(RandomStringUtils.random(10))
                .address(RandomStringUtils.random(25))
                .email(RandomStringUtils.random(15))
                .build();

        if (withSchemaLessFields) {
            Map<UUID, Integer> map = IntStream
                    .range(0, RandomUtils.nextInt(SCHEMALESS_MIN_COUNT, SCHEMALESS_MAX_COUNT)).boxed()
                    .collect(Collectors.toMap(i -> UUID.randomUUID(), i -> RandomUtils.nextInt()));
            shop.setAvailableProductsQuantity(map);
        }

        return shop;
    }

    public static Product createProduct() {
        return Product.builder()
                .id(UUID.randomUUID())
                .barcode(RandomStringUtils.randomAlphabetic(10))
                .name(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

    public static Customer createCustomer() {
        return Customer.builder()
                .id(UUID.randomUUID())
                .fullName(RandomStringUtils.randomAlphabetic(10))
                .email(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

}
