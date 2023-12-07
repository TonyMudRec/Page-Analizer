package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.jetbrains.annotations.NotNull;

/**
 * url handler.
 */
public class UrlController {

    /**
     * number of output records per page.
     */
    private static final int PER = 5;

    /**
     * creates new url and add to url table.
     * @param ctx
     */
    public static void addUrl(@NotNull Context ctx) {
        URL url = null;

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

        boolean isUrlExist = false;
        try {
            isUrlExist = UrlRepository.find(name) != null;
        } catch (SQLException e) {
            e.getSQLState();
        }

        if (isUrlExist) {
            long now = System.currentTimeMillis();
            Timestamp currentTime = new Timestamp(now);
            Url newUrl = new Url(name, currentTime);
            try {
                UrlRepository.save(newUrl);
            } catch (SQLException e) {
                e.getSQLState();
            }

            addFlash(ctx, "alert-success", "Страница успешно добавлена");
            ctx.redirect(NamedRoutes.urlsPath());
        } else {
            addFlash(ctx, "alert-warning", "Страница уже существует");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

    /**
     * flash messages handler.
     * @param ctx
     * @param flash
     * @param flashType
     */
    public static void addFlash(@NotNull Context ctx, String flashType, String flash) {
        ctx.sessionAttribute("flash", flash);
        ctx.sessionAttribute("flashType", flashType);
    }

    /**
     * shows table of urls.
     * @param ctx
     */
    public static void showUrls(@NotNull Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        List<Url> urls = new ArrayList<>();

        try {
            urls = UrlRepository.getEntities();
        } catch (SQLException e) {
            e.getSQLState();
        }

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

    /**
     * shows url page.
     * @param ctx
     */
    public static void showUrl(@NotNull Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");

        Long id = ctx.pathParamAsClass("id", Long.class).get();
        List<UrlCheck> checks = new ArrayList<>();
        Url url = null;

        try {
            checks = CheckRepository.find(id);
            url = UrlRepository.find(id);
        } catch (SQLException e) {
            e.getSQLState();
        }

        checks = checks.size() > PER ? checks.subList(checks.size() - PER, checks.size()) : checks;

        UrlPage page = new UrlPage(id,
                url != null ? url.getName() : null,
                url != null ? url.getStringCreatedAt() : null,
                checks);
        page.setFlash(flash);
        page.setFlashType(flashType);
        ctx.render("show.jte", Collections.singletonMap("page", page));
    }
}
