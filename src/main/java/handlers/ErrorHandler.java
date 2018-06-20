package handlers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.HashMap;
import java.util.Map;
import configs.App;
import helpers.ErrorHelper;

public class ErrorHandler {
  public static Route index = (Request request, Response response) -> {
    ErrorHelper helper = new ErrorHelper();
    Config constants = ConfigFactory.parseResources("configs/application.conf");
    int numeroError = Integer.parseInt(request.params(":error"));
    String numero = "";
    String mensaje = "";
    String descripcion = "";
    switch (numeroError) {
      case 404: 
        numero = "404";
        mensaje = "Archivo no encontrado";
        descripcion = "La página que busca no se encuentra en el servidor";
        break;
      case 505: 
        numero = "505";
        mensaje = "Acceso restringido";
        descripcion = "Necesita estar logueado";
        break;
      default: 
      numero = "404";
      mensaje = "Archivo no encontrado";
      descripcion = "La página que busca no se encuentra en el servidor";
        break;
    }
    Map<String, Object> model = new HashMap<>();
    model.put("partial", "templates/error/index.vm");
    model.put("title", "Error");
    model.put("constants", constants);
    model.put("mensaje", mensaje);
    model.put("numero", numero);
    model.put("descripcion", descripcion);
    model.put("load_css", helper.indexCSS());
    model.put("load_js", helper.indexJS());
    return App.renderTemplate("templates/layouts/blank.vm", model);
  };
}
