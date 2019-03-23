package com.codete.workshops.cassandra.repository;

import com.codete.workshops.cassandra.model.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CassandraRepository<Order, UUID> {

}
