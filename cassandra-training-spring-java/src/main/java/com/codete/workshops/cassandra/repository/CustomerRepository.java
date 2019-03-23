package com.codete.workshops.cassandra.repository;

import com.codete.workshops.cassandra.model.Customer;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CassandraRepository<Customer, UUID> {

}
