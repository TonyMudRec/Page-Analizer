package hexlet.code.pages;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasePage {
    private String flash;
    private String flashType;
    private int listSize;
    private int currentPage;
}
