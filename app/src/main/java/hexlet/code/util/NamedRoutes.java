package hexlet.code.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * storage of named routes.
 */
public class NamedRoutes {
    @Contract(pure = true)
    public static @NotNull String rootPath() {
        return "/";
    }
    @Contract(pure = true)
    public static @NotNull String urlsPath() {
        return "/urls";
    }
    @Contract(pure = true)
    public static @NotNull String urlPath(String id) {
        return "/urls/" + id;
    }
    public static @NotNull String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }
    @Contract(pure = true)
    public static @NotNull String checkUrl(String id) {
        return "/urls/" + id + "/checks";
    }
    public static @NotNull String checkUrl(Long id) {
        return checkUrl(String.valueOf(id));
    }
}
