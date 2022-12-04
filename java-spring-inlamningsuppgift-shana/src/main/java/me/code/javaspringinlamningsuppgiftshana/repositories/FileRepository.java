package me.code.javaspringinlamningsuppgiftshana.repositories;

import me.code.javaspringinlamningsuppgiftshana.data.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * FileRepository is used for managing the data
 * there is a custom method to find file with filename.
 */
@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    Optional<File> findByName(String name);

    Optional<List<File>> findAllByName(String name);
}
