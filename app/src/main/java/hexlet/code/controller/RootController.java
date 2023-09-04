package hexlet.code.controller;

import hexlet.code.pages.manager.MainPage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void getRootPage(Context ctx) {
        String url = ctx.formParamAsClass("url", String.class).getOrDefault("example.com");
        MainPage page = new MainPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("main.jte", Collections.singletonMap("page", page));
    }
}
