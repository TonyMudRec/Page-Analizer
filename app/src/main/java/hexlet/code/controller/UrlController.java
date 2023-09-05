package hexlet.code.controller;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

public class UrlController {
    public static void addUrl(Context ctx) throws MalformedURLException, SQLException {
        java.net.URL url = new URL(ctx.formParam("url"));

        if (url.getProtocol() == null || url.getHost() == null) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "alert-danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        String protocol = url.getProtocol();
        String host = url.getHost();
        String port = url.getPort() == 0 ? "" : ":" + url.getPort();

        if (UrlRepository.find(host).isEmpty()) {
            String name = protocol + "://" + host + port;
            long now = System.currentTimeMillis();
            Timestamp currentTime = new Timestamp(now);
            Url newUrl = new Url(name, currentTime);
            UrlRepository.save(newUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flashType", "alert-success");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "alert-warning");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void showUrls(Context ctx) {

    }
}
