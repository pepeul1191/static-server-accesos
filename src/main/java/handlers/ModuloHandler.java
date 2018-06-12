package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Modulo;

public class ModuloHandler{
  public static Route listar = (Request request, Response response) -> {
    String rpta = "";
    int sistemaId = Integer.parseInt(request.params(":sistema_id"));
    Database db = new Database();
    try {
      List<JSONObject> rptaTemp = new ArrayList<JSONObject>();
      db.open();
      List<Modulo> rptaList = Modulo.find("sistema_id = ?", sistemaId);
      for (Modulo modulo : rptaList) {
        JSONObject obj = new JSONObject();
        obj.put("id", modulo.get("id"));
        obj.put("nombre", modulo.get("nombre"));
        obj.put("url", modulo.get("url"));
        obj.put("icono", modulo.get("icono"));
        rptaTemp.add(obj);
      }
      rpta = rptaTemp.toString();
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en  listar los módulos", e.toString()};
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