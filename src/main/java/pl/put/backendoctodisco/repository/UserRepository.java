package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


}
