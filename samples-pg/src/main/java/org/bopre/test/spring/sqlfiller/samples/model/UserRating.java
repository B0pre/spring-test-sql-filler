package org.bopre.test.spring.sqlfiller.samples.model;

import javax.persistence.*;

@Table(name = "user_rating")
@Entity
public class UserRating {

    public UserRating() {
    }

    public UserRating(Integer id, RegisteredUser user, Double rating) {
        this.id = id;
        this.user = user;
        this.rating = rating;
    }

    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RegisteredUser user;

    @Column(name = "rating")
    private Double rating;

    public Integer getId() {
        return id;
    }

    public RegisteredUser getUser() {
        return user;
    }

    public Double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "UserRating{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }

}
