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
import models.ViewUsuarioCorreoEstado;;

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

  public static Route usuarioCorreo = (Request request, Response response) -> {
    String rpta = "";
    int usuarioId = Integer.parseInt(request.params(":usuario_id"));
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<ViewUsuarioCorreoEstado> rptaList = ViewUsuarioCorreoEstado.find("id = ?", usuarioId);
      for (ViewUsuarioCorreoEstado usuario : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", usuario.get("id"));
        obj.put("usuario", usuario.get("usuario"));
        obj.put("correo", usuario.get("correo"));
        obj.put("estado_usuario_id", usuario.get("estado_usuario_id"));
        obj.put("estado_usuario_nombre", usuario.get("estado_usuario_nombre"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  obtener el usuario y correo", e.toString()};
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