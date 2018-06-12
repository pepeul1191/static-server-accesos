package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Item;

public class ItemHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    int subtituloId = Integer.parseInt(request.params(":subtitulo_id"));
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Item> rptaList = Item.find("subtitulo_id = ?", subtituloId);
      for (Item subtitulo : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", subtitulo.get("id"));
        obj.put("nombre", subtitulo.get("nombre"));
        obj.put("url", subtitulo.get("url"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los items", e.toString()};
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
      int subtituloId = data.getJSONObject("extra").getInt("subtitulo_id");
      db.open();
      db.getDb().openTransaction();
      if(nuevos.length() > 0){
        for (int i = 0; i < nuevos.length(); i++) {
          JSONObject subtitulo = nuevos.getJSONObject(i);
          String antiguoId = subtitulo.getString("id");
          String nombre = subtitulo.getString("nombre");
          String url = subtitulo.getString("url");
          Item n = new Item();
          n.set("nombre", nombre);
          n.set("url", url);
          n.set("subtitulo_id", subtituloId);
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
          JSONObject subtitulo = editados.getJSONObject(i);
          int id = subtitulo.getInt("id");
          String nombre = subtitulo.getString("nombre");
          String url = subtitulo.getString("url");
          Item e = Item.findFirst("id = ?", id);
          if(e != null){
            e.set("nombre", nombre);
            e.set("url", url);
            e.saveIt();
          }
        }
      }
      if(eliminados.length() > 0){
        for (Object eliminado : eliminados) {
          String eleminadoId = (String)eliminado;
          Item d = Item.findFirst("id = ?", eleminadoId);
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
      String[] cuerpoMensaje = {"Se ha producido un error en  guardar los items del subtítulo", execption};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "error");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      response.status(500);
      rpta = rptaMensaje.toString();
    }else{
      JSONArray cuerpoMensaje =  new JSONArray();
      cuerpoMensaje.put("Se ha registrado los cambios en los items del subtítulo");
      cuerpoMensaje.put(listJSONNuevos);
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }
    return rpta;
  };
}