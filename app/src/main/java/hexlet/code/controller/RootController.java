package hexlet.code.controller;

import hexlet.code.pages.manager.MainPage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void getRootPage(Context ctx) {
        String url = ctx.formParam("url");
        MainPage page = new MainPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("main.jte", Collections.singletonMap("page", page));
    }
}
