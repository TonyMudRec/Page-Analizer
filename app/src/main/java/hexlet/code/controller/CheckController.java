package hexlet.code.controller;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.sql.Timestamp;


public class CheckController {

    public static void addCheck(Context ctx) throws SQLException {
        long now = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(now);
        long id = Long.parseLong(ctx.pathParam("id"));
        Url url = UrlRepository.find(id);
        int statusCode = Unirest.get(url.getName()).asJson().getStatus();
        String response = Unirest.get(url.getName()).asString().getBody();

        Document doc = Jsoup.parse(response);
        String title = doc.title();
        String h1 = doc.getElementsByTag("h1").text();
        Elements meta = doc.select("meta[name=description]");
        String description = meta.attr("content");
        UrlCheck check = new UrlCheck(statusCode, title, h1, description, id, currentTime);
        CheckRepository.save(check);


        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flashType", "alert-success");
        ctx.redirect(NamedRoutes.urlPath(id));
    }
}
