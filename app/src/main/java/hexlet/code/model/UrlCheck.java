package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static hexlet.code.model.Url.convertTimestampToDate;

@Getter
@ToString
public class UrlCheck {
    @Setter
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
    @Setter
    private Long urlId;
    private Timestamp createdAt;

    public UrlCheck(int statusCode, String title, String h1, String description, Long urlId, Timestamp createdAt) {
        this.statusCode = statusCode;
        this.title = title;
        this.h1 = h1;
        this.description = description;
        this.urlId = urlId;
        this.createdAt = createdAt;
    }

    public String getStringCreatedAt() {
        return convertTimestampToDate(createdAt);
    }
}
