package hexlet.code.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Objects;

import static hexlet.code.model.Url.convertTimestampToDate;

/**
 * checks table.
 */
@ToString
@Getter
public final class UrlCheck {

    @Setter
    private Long id;
    private int statusCode;
    private String title;
    private String h1;
    private String description;
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

    /**
     * @return date in string format.
     */
    public @NotNull String getStringCreatedAt() {
        return convertTimestampToDate(createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlCheck urlCheck = (UrlCheck) o;
        return statusCode == urlCheck.statusCode
                && Objects.equals(id, urlCheck.id)
                && Objects.equals(title, urlCheck.title)
                && Objects.equals(h1, urlCheck.h1)
                && Objects.equals(description, urlCheck.description)
                && Objects.equals(urlId, urlCheck.urlId)
                && Objects.equals(createdAt, urlCheck.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusCode, title, h1, description, urlId, createdAt);
    }
}
