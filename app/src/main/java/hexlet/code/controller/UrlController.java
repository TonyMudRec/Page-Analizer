package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.pages.manager.AllUrlsPage;
import hexlet.code.pages.manager.UrlPage;
import hexlet.code.repository.CheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;


public class UrlController {
    private static final int PER = 5;

    public static void addUrl(Context ctx) throws SQLException {
        URL url;

        try {
            url = new URL(ctx.formParam("url").trim());
        } catch (MalformedURLException e) {
            addFlash(ctx, "alert-danger", "Некорректный URL");
            ctx.redirect(NamedRoutes.rootPath());
            return;
        }

        String protocol = url.getProtocol() == null ? "http" : url.getProtocol();
        String host = url.getHost();
        String port = url.getPort() == -1 ? "" : ":" + url.getPort();
        String name = protocol + "://" + host + port;

        if (UrlRepository.find(name) == null) {
            long now = System.currentTimeMillis();
            Timestamp currentTime = new Timestamp(now);
            Url newUrl = new Url(name, currentTime);
            UrlRepository.save(newUrl);
            addFlash(ctx, "alert-success", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            addFlash(ctx, "alert-warning", "Страница уже существует");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    public static void addFlash(Context ctx, String flashType, String flash) {
        ctx.sessionAttribute("flash", flash);
        ctx.sessionAttribute("flashType", flashType);
    }

    public static void showUrls(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        List<Url> urls = UrlRepository.getEntities();
        int currentPage = ctx.queryParamAsClass("pageNumber", Integer.class).getOrDefault(1);
        int firstIndex = (currentPage - 1) * PER;
        int lastIndex = Math.min(currentPage * PER, urls.size());

        AllUrlsPage page = new AllUrlsPage(urls.subList(firstIndex, lastIndex));
        page.setListSize(urls.size());
        page.setCurrentPage(currentPage);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("urls.jte", Collections.singletonMap("page", page));
    }

    public static void showUrl(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        Long id = ctx.pathParamAsClass("id", Long.class).get();
        List<UrlCheck> checks = CheckRepository.find(id);
        Url url = UrlRepository.find(id);
        checks = checks.size() > PER ? checks.subList(checks.size() - PER, checks.size()) : checks;

        UrlPage page = new UrlPage(id, url.getName(), url.getStringCreatedAt(), checks);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("show.jte", Collections.singletonMap("page", page));
    }
}
