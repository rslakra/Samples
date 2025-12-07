package com.rslakra.springbootjsp.repository;

import com.rslakra.springbootjsp.repository.model.BookDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookDO, String> {

    Optional<BookDO> findByIsbn(String isbn);
}
