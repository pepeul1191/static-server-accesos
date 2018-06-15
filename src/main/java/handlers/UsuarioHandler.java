package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
//import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Usuario;

public class UsuarioHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Usuario> rptaList = Usuario.findAll();
      for (Usuario usuario : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", usuario.get("id"));
        obj.put("usuario", usuario.get("usuario"));
        obj.put("correo", usuario.get("correo"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los usuarios", e.toString()};
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