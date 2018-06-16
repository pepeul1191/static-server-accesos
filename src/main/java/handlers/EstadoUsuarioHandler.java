package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.EstadoUsuario;

public class EstadoUsuarioHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<EstadoUsuario> rptaList = EstadoUsuario.findAll();
      for (EstadoUsuario sistema : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", sistema.get("id"));
        obj.put("nombre", sistema.get("nombre"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los estados de usuario", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", error);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      db.close();
    }
    return rpta;
  };
}