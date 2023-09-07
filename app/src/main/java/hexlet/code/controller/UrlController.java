package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import hexlet.code.model.Url;
import hexlet.code.pages.manager.AllUrlsPage;
import hexlet.code.pages.manager.UrlPage;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

public class UrlController {
    private static final int PER = 5;

    public static void addUrl(Context ctx) throws SQLException {
        URL url;
        try {
            url = new URL(ctx.formParam("url"));
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "alert-danger");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }
        String protocol = url.getProtocol();
        String host = url.getHost();
        String port = url.getPort() == -1 ? "" : ":" + url.getPort();
        String name = protocol + "://" + host + port;

        if (UrlRepository.find(name) == null) {
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
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        List<Url> urls = UrlRepository.getEntities();
        int pageCount = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int firstIndex = (pageCount - 1) * PER;

        if (urls.size() > PER) {
            urls = urls.subList(firstIndex, firstIndex + PER);
        }

        AllUrlsPage page = new AllUrlsPage(urls, pageCount);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("urls.jte", Collections.singletonMap("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        Long id = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        assert url != null;
        String createdAt  = dateFormat.format(url.getCreatedAt());
        UrlPage page = new UrlPage(id, url.getName(), createdAt);
        ctx.render("show.jte", Collections.singletonMap("page", page));
    }
}
