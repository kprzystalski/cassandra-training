package com.codete.workshops.cassandra.repository;

import com.codete.workshops.cassandra.model.Shop;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopRepository extends CassandraRepository<Shop, UUID> {

}
