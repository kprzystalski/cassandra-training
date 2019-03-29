package com.codete.workshops.cassandra.repository;

import com.codete.workshops.cassandra.model.VisitorAction;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitorActionRepository extends CassandraRepository<VisitorAction, UUID> {

}
