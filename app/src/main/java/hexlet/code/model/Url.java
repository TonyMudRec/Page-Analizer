package hexlet.code.model;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class Url {
    private Long id;
    @ToString.Include
    private String name;
//    @jdk.jfr.Timestamp
    private Timestamp createdAt;

    public Url(String name) {
        this.name = name;
    }
}
