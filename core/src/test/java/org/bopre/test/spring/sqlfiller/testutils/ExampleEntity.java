package org.bopre.test.spring.sqlfiller.testutils;


import java.util.Objects;

public class ExampleEntity {
    public static final String CREATE_EXAMPLE_TABLE = "CREATE TABLE IF NOT EXISTS example (id bigint auto_increment, name text, rating double precision default 0.0)";
    public static final String DROP_EXAMPLE_TABLE = "DROP TABLE IF EXISTS example";
    private final int id;
    private final String name;
    private final double rating;

    public ExampleEntity(int id, String name, double rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public ExampleEntity(int id, String name) {
        this(id, name, 0.0);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExampleEntity that = (ExampleEntity) o;
        return id == that.id && Double.compare(that.rating, rating) == 0 && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating);
    }

    @Override
    public String toString() {
        return "ExampleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
