package hexlet.code.pages.manager;

import hexlet.code.pages.BasePage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Long id;
    private String name;
    private String createdAt;
}
