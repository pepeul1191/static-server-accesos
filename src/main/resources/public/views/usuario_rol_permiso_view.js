var UsuarioRolPermisoView = ModalView.extend({
  usuarioId: null,
  initialize: function(options){
    this.targetMensaje = options["targetMensaje"];
    // herencia de atributos, móetodos y eventos
    ModalView.prototype.initialize.apply(this, [options])
    this.inheritEvents(ModalView);
    // delegación de eventos
    this.delegateEvents();
    //this.tablaLog = new TableView(dataTablaLog);
  },
  events: {
    // se está usando asignacion dinamica de eventos en el constructor
    // tabla modulos
  },
  //combo
  cbmSistemas:function(){
    var usuario_id = this.get("usuario_id");
    var rpta = [];
    $.ajax({
      type: "GET",
      url: BASE_URL + "usuario/sistema/" + usuario_id,
      data: "",
      async: false,
      success: function(data){
        var sistemas = JSON.parse(data);
        for(var i = 0; i < sistemas.length; i++){
          var t = {
            id: sistemas[i].id,
            nombre: sistemas[i].nombre,
          };
          rpta.push(t);
        }
      },
      error: function(data){
        console.error("Error en listar los sistemas del usuario", data);
      }
    });
    return rpta;
  },
});