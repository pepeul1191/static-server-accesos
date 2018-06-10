package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Sistema;

public class SistemaHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Sistema> rptaList = Sistema.findAll();
      for (Sistema sistema : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", sistema.get("id"));
        obj.put("nombre", sistema.get("nombre"));
        obj.put("version", sistema.get("version"));
        obj.put("repositorio", sistema.get("repositorio"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los sistemas", e.toString()};
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

  public static Route guardar = (Request request, Response response) -> {
    String rpta = "";
    List<JSONObject> listJSONNuevos = new ArrayList<JSONObject>();
    boolean error = false;
    String execption = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("data"));
      JSONArray nuevos = data.getJSONArray("nuevos");
      JSONArray editados = data.getJSONArray("editados");
      JSONArray eliminados = data.getJSONArray("eliminados");
      db.open();
      db.getDb().openTransaction();
      if(nuevos.length() > 0){
        for (int i = 0; i < nuevos.length(); i++) {
          JSONObject sistema = nuevos.getJSONObject(i);
          String antiguoId = sistema.getString("id");
          String nombre = sistema.getString("nombre");
          String version = sistema.getString("version");
          String respositorio = sistema.getString("respositorio");
          Sistema n = new Sistema();
          n.set("nombre", nombre);
          n.set("version", version);
          n.set("respositorio", respositorio);
          n.saveIt();
          int nuevoId = (int) n.get("id"); 
          JSONObject temp = new JSONObject();
          temp.put("temporal", antiguoId);
          temp.put("nuevo_id", nuevoId);
          listJSONNuevos.add(temp);
        }
      }
      if(editados.length() > 0){
        for (int i = 0; i < editados.length(); i++) {
          JSONObject sistema = editados.getJSONObject(i);
          int id = sistema.getInt("id");
          String nombre = sistema.getString("nombre");
          String version = sistema.getString("version");
          String respositorio = sistema.getString("respositorio");
          Sistema e = Sistema.findFirst("id = ?", id);
          if(e != null){
            e.set("nombre", nombre);
            e.set("version", version);
            e.set("respositorio", respositorio);
            e.saveIt();
          }
        }
      }
      if(eliminados.length() > 0){
        for (Object eliminado : eliminados) {
          int eleminadoId = (Integer)eliminado;
          Sistema d = Sistema.findFirst("id = ?", eleminadoId);
          if(d != null){
            d.delete();
          }
        }
      }
      db.getDb().commitTransaction();
    }catch (Exception e) {
      error = true;
      //e.printStackTrace();
      execption = e.toString();
    } finally {
      db.close();
    }
    if(error){
      String[] cuerpoMensaje = {"Se ha producido un error en  guardar los sistemas", execption};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "error");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }else{
      String[] cuerpoMensaje = {"Se ha registrado los cambios en los sistemas", listJSONNuevos.toString()};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }
    return rpta;
  };
}