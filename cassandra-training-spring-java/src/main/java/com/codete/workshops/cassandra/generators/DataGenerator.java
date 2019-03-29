package com.codete.workshops.cassandra.generators;

import com.codete.workshops.cassandra.repository.*;
import com.datastax.driver.core.Cluster;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DataGenerator {

    public static final int SCHEMALESS_MIN_COUNT = 5;
    public static final int SCHEMALESS_MAX_COUNT = 20;

    public static final int MAX_NUMBER_OF_ELEMENTS_TO_INSERT = 10;

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final VisitorActionRepository visitorActionRepository;
    private final VisitorRepository visitorRepository;
    private final Cluster cluster;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Scheduled(fixedRate = 2000)
    public void run() {
        generateOnlyStructuredData();
        generateWithUnstructured();
    }

    private void generateWithUnstructured() {
        generate(true);
    }


    private void generateOnlyStructuredData() {
        generate(false);
    }

    private void generate(boolean withSchemaLessFields) {
        insertRandomNumberOfElements(SampleDataFactory::createProduct, productRepository);
        insertRandomNumberOfElements(SampleDataFactory::createOrder, orderRepository);
        insertRandomNumberOfElements(SampleDataFactory::createCustomer, customerRepository);
        insertRandomNumberOfElements(() -> SampleDataFactory.createShop(withSchemaLessFields), shopRepository);
        insertRandomNumberOfElements(() -> SampleDataFactory.createVisitorAction(withSchemaLessFields), visitorActionRepository);
        insertRandomNumberOfElements(() -> SampleDataFactory.createVisitor(withSchemaLessFields), visitorRepository);
    }

    private <T, K extends CassandraRepository<T, UUID>> void insertRandomNumberOfElements(Supplier<T> supplier, K consumer) {
        IntStream.range(0, RandomUtils.nextInt(0, MAX_NUMBER_OF_ELEMENTS_TO_INSERT))
                .forEach(i -> consumer.save(supplier.get()));
    }

}
