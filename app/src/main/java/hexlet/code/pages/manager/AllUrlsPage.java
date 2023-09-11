package hexlet.code.pages.manager;

import hexlet.code.model.Url;
import hexlet.code.pages.BasePage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AllUrlsPage extends BasePage {
    private List<Url> urls;
}
