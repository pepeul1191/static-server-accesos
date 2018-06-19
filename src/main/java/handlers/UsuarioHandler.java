package handlers;

import spark.Request;
import spark.Response;
import spark.Route;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import configs.Database;
import models.Usuario;
import models.ViewUsuarioCorreoEstado;;

public class UsuarioHandler{
  public static Route validar = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      String usuario = request.queryParams("usuario");
      String contrasenia = request.queryParams("contrasenia");
      db.open();
      rpta = Usuario.count("usuario = ? AND contrasenia = ?", usuario) + "";
      if (rpta.equalsIgnoreCase("1")){
        //guardar acceso
        Usuario u = Usuario.findFirst("usuario = ? AND contrasenia = ?", usuario, contrasenia);
        if(u != null){
          int usuarioId = u.getInteger("id");
          
        }
      }
    }catch (Exception e) {
      String[] error = {"Se ha producido un error en validar al usuario", e.toString()};
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
      db.open();
      ViewUsuarioCorreoEstado usuario = ViewUsuarioCorreoEstado.findFirst("id = ?", usuarioId);
      JSONObject obj = new JSONObject();
      obj.put("id", usuario.get("id"));
      obj.put("usuario", usuario.get("usuario"));
      obj.put("correo", usuario.get("correo"));
      obj.put("estado_usuario_id", usuario.get("estado_usuario_id"));
      obj.put("estado_usuario_nombre", usuario.get("estado_usuario_nombre"));
      rpta = obj.toString();
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

  public static Route nombreRepetido = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("data"));
      String usuarioId = data.getString("id");
      String usuario = data.getString("usuario");
      db.open();
      rpta = "1";
      if (usuarioId.equalsIgnoreCase("E")){
        //SELECT COUNT"(*) AS cantidad FROM usuarios WHERE usuario = ?
        rpta = Usuario.count("usuario = ?", usuario) + "";
      }else{
        //SELECT COUNT(*) AS cantidad FROM usuarios WHERE usuario = ? AND id = ?
        long cantidad = Usuario.count("usuario = ? AND id = ?", usuario, usuarioId);
        if (cantidad == 1){
          rpta = "0";
        }else{
          //SELECT COUNT(*) AS cantidad FROM usuarios WHERE usuario = ?
          rpta = Usuario.count("usuario = ?", usuario) + "";
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] errorArray = {"Se ha producido un error en validar si el nombre es repetido", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", errorArray);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route contraseniaRepetida = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("data"));
      String usuarioId = data.getString("id");
      String contrasenia = data.getString("contrasenia");
      db.open();
      rpta = Usuario.count("contrasenia = ? AND id = ?", contrasenia, usuarioId) + "";
    }catch (Exception e) {
      e.printStackTrace();
      String[] errorArray = {"Se ha producido un error en validar si la contraseña del usuario", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", errorArray);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route correoRepetido = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("data"));
      String usuarioId = data.getString("id");
      String correo = data.getString("correo");
      db.open();
      rpta = "1";
      if (usuarioId.equalsIgnoreCase("E")){
        //SELECT COUNT(*) AS cantidad FROM usuarios WHERE correo = ?
        rpta = Usuario.count("correo = ?", correo) + "";
      }else{
        //SELECT COUNT(*) AS cantidad FROM usuarios WHERE correo = ? AND id = ?
        long cantidad = Usuario.count("correo = ? AND id = ?", correo, usuarioId);
        if (cantidad == 1){
          rpta = "0";
        }else{
          //SELECT COUNT(*) AS cantidad FROM usuarios WHERE correo = ?
          rpta = Usuario.count("correo = ?", correo) + "";
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      String[] errorArray = {"Se ha producido un error en validar si el correo es repetido", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", errorArray);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route guardarUsuarioCorreo = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("usuario"));
      String usuarioId = data.getString("id");
      String correo = data.getString("correo");
      String usuario = data.getString("usuario");
      String estado_usuario_id = data.getString("estado_usuario_id");
      db.open();
      Usuario e = Usuario.findFirst("id = ?", usuarioId);
      if(e != null){
        e.set("correo", correo);
        e.set("usuario", usuario);
        e.set("estado_usuario_id", estado_usuario_id);
        e.saveIt();
      }
      JSONArray cuerpoMensaje =  new JSONArray();
      cuerpoMensaje.put("Se ha registrado los cambios en los datos generales del usuario");
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }catch (Exception e) {
      e.printStackTrace();
      String[] errorArray = {"Se ha producido un error en actualizar el usaurio", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", errorArray);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };

  public static Route guardarContrasenia = (Request request, Response response) -> {
    String rpta = "";
    Database db = new Database();
    try {
      JSONObject data = new JSONObject(request.queryParams("contrasenia"));
      String usuarioId = data.getString("id");
      String contrasenia = data.getString("contrasenia");
      db.open();
      Usuario e = Usuario.findFirst("id = ?", usuarioId);
      if(e != null){
        e.set("contrasenia", contrasenia);
        e.saveIt();
      }
      JSONArray cuerpoMensaje =  new JSONArray();
      cuerpoMensaje.put("Se ha el cambio de contraseña del usuario");
      JSONObject rptaMensaje = new JSONObject();
      rptaMensaje.put("tipo_mensaje", "success");
      rptaMensaje.put("mensaje", cuerpoMensaje);
      rpta = rptaMensaje.toString();
    }catch (Exception e) {
      e.printStackTrace();
      String[] errorArray = {"Se ha producido un error en actualizar la contraseña del usaurio", e.toString()};
      JSONObject rptaTry = new JSONObject();
      rptaTry.put("tipo_mensaje", "error");
      rptaTry.put("mensaje", errorArray);
      rpta = rptaTry.toString();
      response.status(500);
    } finally {
      if(db.getDb().hasConnection()){
        db.close();
      }
    }
    return rpta;
  };
}