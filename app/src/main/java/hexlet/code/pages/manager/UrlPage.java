package hexlet.code.pages.manager;

import hexlet.code.model.UrlCheck;
import hexlet.code.pages.BasePage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Long id;
    private String name;
    private String createdAt;
    private List<UrlCheck> checks;
}
