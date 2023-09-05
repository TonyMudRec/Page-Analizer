package hexlet.code.controller;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import hexlet.code.model.Url;
import hexlet.code.pages.manager.AllUrlsPage;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

public class UrlController {
    private static final int PER = 5;
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

    public static void showUrls(Context ctx) throws SQLException {
        List<Url> urls;
        int pageCount = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int firstIndex = (pageCount - 1) * PER;

        if(UrlRepository.getEntities().size() > PER) {
            urls = UrlRepository.getEntities().subList(firstIndex, firstIndex + PER);
        } else {
            urls = UrlRepository.getEntities();
        }

        AllUrlsPage page = new AllUrlsPage(urls, pageCount);
        ctx.render("urls.jte", Collections.singletonMap("page", page));
    }
}
