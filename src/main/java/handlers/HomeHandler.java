package handlers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.HashMap;
import java.util.Map;
import configs.App;
import helpers.HomeHelper;

public class HomeHandler {
  public static Route index = (Request request, Response response) -> {
    HomeHelper helper = new HomeHelper();
    Config constants = ConfigFactory.parseResources("config/application.conf");
    Map<String, Object> model = new HashMap<>();
    model.put("partial", "templates/home/index.vm");
    model.put("title", "Home");
    model.put("constants", constants);
    model.put("load_css", helper.indexCSS());
    model.put("load_js", helper.indexJS());
    return App.renderTemplate("templates/layouts/blank.vm", model);
  };
}

