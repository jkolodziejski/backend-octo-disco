package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.FlashcardListInfo;

@Repository
public interface FlashcardListRepository extends JpaRepository<FlashcardListInfo, Integer> {

}
