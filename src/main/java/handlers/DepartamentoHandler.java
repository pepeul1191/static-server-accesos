package handlers;

import spark.Request;
import spark.Response;
import spark.Route;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Departamento;

public class DepartamentoHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Departamento> rptaList = Departamento.findAll();
      for (Departamento departamento : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", departamento.get("id"));
        obj.put("nombre", departamento.get("nombre"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar la búsqueda", e.toString()};
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
    JSONObject data = new JSONObject(request.queryParams("data"));
    JSONArray nuevos = data.getJSONArray("nuevos");
    JSONArray editados = data.getJSONArray("editados");
    JSONArray eliminados = data.getJSONArray("eliminados");
    List<JSONObject> listJSONNuevos = new ArrayList<JSONObject>();
    boolean error = false;
    String execption = "";
    Database db = new Database();
    try {
      db.open();
      db.getDb().openTransaction();
      if(nuevos.length() > 0){
        for (int i = 0; i < nuevos.length(); i++) {
          JSONObject departamento = nuevos.getJSONObject(i);
          String antiguoId = departamento.getString("id");
          String nombre = departamento.getString("nombre");
          Departamento n = new Departamento();
          n.set("nombre", nombre);
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
          JSONObject departamento = editados.getJSONObject(i);
          int id = departamento.getInt("id");
          String nombre = departamento.getString("nombre");
          //Employee e = Employee.findFirst("first_name = ?", "John");
          Departamento e = Departamento.findFirst("id = ?", id);
          e.set("nombre", nombre);
          e.saveIt();
        }
      }
      if(eliminados.length() > 0){
        for (Object eliminado : eliminados) {
          int eleminadoId = (Integer)eliminado;
          Departamento d = Departamento.findFirst("id = ?", eleminadoId);
          d.delete();
        }
      }
      db.getDb().commitTransaction();
    }catch (Exception e) {
      error = true;
      execption = e.toString();
    } finally {
      db.close();
    }
    if(error){
      String[] cuerpoMensaje = {"Se ha producido un error en  guardar los departamento", execption};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "error");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }else{
      String[] cuerpoMensaje = {"Se ha registrado los cambios en los departamentos", listJSONNuevos.toString()};
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }
    return rpta;
  };
}