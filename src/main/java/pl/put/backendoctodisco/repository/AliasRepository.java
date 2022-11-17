package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Alias;

import java.util.List;

@Repository
public interface AliasRepository extends JpaRepository<Alias, Integer> {

    List<Alias> findByWordId(Long word_id);

    List<Alias> findAliasesByWordId(Long word_id);


}
