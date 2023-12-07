package hexlet.code.util;

/**
 * storage of named routes.
 */
public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPath() {
        return "/urls";
    }
    public static String urlPath(String id) {
        return "/urls/" + id;
    }
    public static String urlPath(Long id) {
        return urlPath(String.valueOf(id));
    }
    public static String checkUrl(String id) {
        return "/urls/" + id + "/checks";
    }
    public static String checkUrl(Long id) {
        return checkUrl(String.valueOf(id));
    }
}
