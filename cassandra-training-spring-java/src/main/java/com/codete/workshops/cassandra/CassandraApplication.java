package com.codete.workshops.cassandra;

import com.codete.workshops.cassandra.model.Book;
import com.codete.workshops.cassandra.repository.BookRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CassandraApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(CassandraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Book book = new Book(UUID.randomUUID(), "Moby Dick", "Gliwician Publishment",
                Sets.newHashSet("Silesia", "does", "NOT", "belong", "to", "the", "Polish", "country"));

        bookRepository.save(book);
    }

}
