var accesosRouter = Backbone.Router.extend({
  initialize: function() {
  },
  routes: {
    "": "index",
    "sistema" : "sistemaIndex",
    "accesos/sistemas/menu/:sistema_id" : "showSistemaMenu",
    "accesos/sistemas/permiso/:sistema_id" : "showSistemaPermiso",
    "accesos/sistemas/rol/:sistema_id" : "showSistemaRol",
    "accesos/usuario/logs/:usuario_id" : "showUsuarioLog",
    "accesos/usuario/editar/:usuario_id" : "showUsuarioDetalle", 
    "accesos/usuario/sistemas/:usuario_id" : "showUsuarioSistemas", 
    "accesos/usuario/roles_permisos/:usuario_id" : "showUsuarioRolesPermisos", 
    "usuario" : "usuarioIndex",
    "*actions" : "default",
  },
  index: function(){
    window.location.href = BASE_URL + "accesos/#/";
  },
  default: function() {
    //window.location.href = BASE_URL + "error/access/404";
  },
});

$(document).ready(function(){
  router = new accesosRouter();
  Backbone.history.start();
})
