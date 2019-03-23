package com.codete.workshops.cassandra.repository;

import com.codete.workshops.cassandra.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends CassandraRepository<Product, UUID> {

    @Query("select * from book where title = ?0 and publisher=?1")
    Iterable<Product> findByTitleAndPublisher(String title, String publisher);

}
