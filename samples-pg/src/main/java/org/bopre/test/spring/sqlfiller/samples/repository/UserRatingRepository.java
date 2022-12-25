package org.bopre.test.spring.sqlfiller.samples.repository;

import org.bopre.test.spring.sqlfiller.samples.model.RegisteredUser;
import org.bopre.test.spring.sqlfiller.samples.model.UserRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRatingRepository extends CrudRepository<UserRating, Integer> {

    @Query("SELECT UR FROM UserRating UR WHERE UR.user=:user")
    UserRating forUser(@Param("user") RegisteredUser user);

    @Query("SELECT UR FROM UserRating UR ORDER BY UR.id")
    List<UserRating> getAll();

}
