package org.bopre.test.spring.sqlfiller.samples.repository;

import org.bopre.test.spring.sqlfiller.samples.model.RegisteredUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<RegisteredUser, Integer> {

    @Query("SELECT U FROM RegisteredUser U ORDER BY U.id")
    List<RegisteredUser> getAll();

}
