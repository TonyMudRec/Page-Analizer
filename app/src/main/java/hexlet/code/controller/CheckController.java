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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.sql.Timestamp;


public class CheckController {

    public static void addCheck(Context ctx) throws SQLException {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);
        Url url = UrlRepository.find(id);

        if (url == null) {
            throw new NotFoundResponse();
        }

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
            CheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "alert-success");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Не удалось проверить страницу");
            ctx.sessionAttribute("flash-type", "danger");
        }
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
