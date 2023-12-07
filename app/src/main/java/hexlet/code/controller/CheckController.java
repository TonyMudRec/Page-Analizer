package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * checks of urls handler.
 */
public class CheckController {

    /**
     * method adds a new url check record to the checks table.
     * @param ctx
     */
    public static void addCheck(@NotNull Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        Url url = null;
        try {
            url = UrlRepository.find(id);
        } catch (SQLException e) {
            e.getSQLState();
        }
        if (url == null) {
            throw new NotFoundResponse();
        }

        if (isSuccessfullyCheck(url, id)) {
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "alert-success");
        } else {
            ctx.sessionAttribute("flash", "Не удалось проверить страницу");
            ctx.sessionAttribute("flash-type", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }

    /**
     * @return true if the url check was successful.
     * @param id
     * @param url
     */
    public static boolean isSuccessfullyCheck(@NotNull Url url, long id) {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        try {
            var response = Unirest
                    .get(url.getName())
                    .asString();

            Document body = Jsoup.parse(response.getBody());

            var statusCode = response.getStatus();
            var title = body.title();
            var h1 = body.selectFirst("h1") != null ? body.selectFirst("h1").text() : null;
            var description = body.selectFirst("meta[name=description]") != null
                    ? body.selectFirst("meta[name=description]").attr("content") : null;

            UrlCheck check = new UrlCheck(statusCode, title, h1, description, id, currentTime);
            try {
                CheckRepository.save(check);
            } catch (SQLException e) {
                e.getSQLState();
            }

            return true;
        } catch (UnirestException e) {
            return false;
        }
    }
}
