package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Rol;

public class RolHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    int sistemaId = Integer.parseInt(request.params(":sistema_id"));
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Rol> rptaList = Rol.find("sistema_id = ?", sistemaId);
      for (Rol rol : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", rol.get("id"));
        obj.put("nombre", rol.get("nombre"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los roles", e.toString()};
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
      int sistemaId = data.getJSONObject("extra").getInt("sistema_id");
      db.open();
      db.getDb().openTransaction();
      if(nuevos.length() > 0){
        for (int i = 0; i < nuevos.length(); i++) {
          JSONObject rol = nuevos.getJSONObject(i);
          String antiguoId = rol.getString("id");
          String nombre = rol.getString("nombre");
          Rol n = new Rol();
          n.set("nombre", nombre);
          n.set("sistema_id", sistemaId);
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
          JSONObject rol = editados.getJSONObject(i);
          int id = rol.getInt("id");
          String nombre = rol.getString("nombre");
          Rol e = Rol.findFirst("id = ?", id);
          if(e != null){
            e.set("nombre", nombre);
            e.saveIt();
          }
        }
      }
      if(eliminados.length() > 0){
        for (Object eliminado : eliminados) {
          String eleminadoId = (String)eliminado;
          Rol d = Rol.findFirst("id = ?", eleminadoId);
          if(d != null){
            d.delete();
          }
        }
      }
      db.getDb().commitTransaction();
    }catch (Exception e) {
      error = true;
      e.printStackTrace();
      execption = e.toString();
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    if(error){
      String[] cuerpoMensaje = {"Se ha producido un error en  guardar los roles del sitema", execption};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "error");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      response.status(500);
      rpta = rptaMensaje.toString();
    }else{
      JSONArray cuerpoMensaje =  new JSONArray();
      cuerpoMensaje.put("Se ha registrado los cambios en los roles del sitema");
      cuerpoMensaje.put(listJSONNuevos);
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }
    return rpta;
  };
}