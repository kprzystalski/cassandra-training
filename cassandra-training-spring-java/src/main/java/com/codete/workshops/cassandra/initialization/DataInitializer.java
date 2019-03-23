package com.codete.workshops.cassandra.initialization;

import com.codete.workshops.cassandra.model.Customer;
import com.codete.workshops.cassandra.model.Order;
import com.codete.workshops.cassandra.model.Product;
import com.codete.workshops.cassandra.model.Shop;
import com.codete.workshops.cassandra.repository.CustomerRepository;
import com.codete.workshops.cassandra.repository.OrderRepository;
import com.codete.workshops.cassandra.repository.ProductRepository;
import com.codete.workshops.cassandra.repository.ShopRepository;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final String CQL_SCRIPT_PATH = "classpath:schema.cql";

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final Cluster cluster;

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Autowired
    public DataInitializer(ProductRepository productRepository, OrderRepository orderRepository, ShopRepository shopRepository, CustomerRepository customerRepository, Cluster cluster) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
        this.cluster = cluster;
    }

    @Override
    public void run(String... args) {
        initializeCassandra();
        initializeData();
    }

    private void initializeCassandra() {
        try (Session session = cluster.connect(keyspaceName)) {
            File scriptFile = ResourceUtils.getFile(CQL_SCRIPT_PATH);
            Files.readAllLines(scriptFile.toPath()).stream()
                    .filter(command -> !command.isEmpty())
                    .forEach(session::execute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeData() {
        // Products
        Product milka = productRepository.save(new Product(UUID.randomUUID(), "3045140105502", "Milka Chocolate", BigDecimal.valueOf(4.49)));
        Product pepsi = productRepository.save(new Product(UUID.randomUUID(), "1354687964548", "Pepsi Cola", BigDecimal.valueOf(2.99)));
        Product oreo = productRepository.save(new Product(UUID.randomUUID(), "4578421587645", "Oreo Cookies", BigDecimal.valueOf(5.15)));
        Product drPepper = productRepository.save(new Product(UUID.randomUUID(), "24654689521", "Dr Pepper", BigDecimal.valueOf(3.12)));

        // Shops
        // Shops - Tesco, Krakow
        HashMap<UUID, Integer> tescoProducts = Maps.newHashMap();
        tescoProducts.put(milka.getId(), 1);
        tescoProducts.put(pepsi.getId(), 724);
        tescoProducts.put(oreo.getId(), 0);
        Shop tesco = shopRepository.save(new Shop(UUID.randomUUID(), "Tesco", "Kraków, Kapelanka", "tesco@kapelanka", 50.032518, 19.925596, tescoProducts));
        shopRepository.save(tesco);

        // Shops - Carrefour, Gliwice
        HashMap<UUID, Integer> carrefourProducts = Maps.newHashMap();
        carrefourProducts.put(milka.getId(), 144);
        carrefourProducts.put(pepsi.getId(), 0);
        carrefourProducts.put(oreo.getId(), 2);
        Shop carrefour = shopRepository.save(new Shop(UUID.randomUUID(), "Carrefour", "Gliwice, Lipowa", "carrefour@gliwice", 50.301669, 18.684511, carrefourProducts));
        shopRepository.save(carrefour);

        // Shops - Walmart, New York
        HashMap<UUID, Integer> walmartProducts = Maps.newHashMap();
        walmartProducts.put(milka.getId(), 143);
        walmartProducts.put(drPepper.getId(), 1240);
        Shop walmart = shopRepository.save(new Shop(UUID.randomUUID(), "Walmart", "New York, USA", "walmart@ny", 40.742806, -73.600192, walmartProducts));
        shopRepository.save(walmart);

        // Customers
        Customer john = customerRepository.save(new Customer(UUID.randomUUID(), "John Kowalsky"));
        Customer adam = customerRepository.save(new Customer(UUID.randomUUID(), "Adam Nowak"));

        // Orders
        orderRepository.save(new Order(UUID.randomUUID(), john.getId(), tesco.getId(), milka.getId(), 16, LocalDateTime.now()));
        orderRepository.save(new Order(UUID.randomUUID(), adam.getId(), carrefour.getId(), oreo.getId(), 2, LocalDateTime.of(2019, 3, 23, 20, 13)));
    }

}
