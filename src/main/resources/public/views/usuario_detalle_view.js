var UsuarioDetalleView = ModalView.extend({
  usuarioId: null,
  initialize: function(options){
    this.targetMensaje = options["targetMensaje"];
    // herencia de atributos, móetodos y eventos
    ModalView.prototype.initialize.apply(this, [options])
    this.inheritEvents(ModalView);
    // delegación de eventos
    this.delegateEvents();
    this.estadoUsuariosSelect = new EstadoUsuariosCollection({
      targetMensaje: "defaultTargetMensajes",
    });
    this.model = new Usuario();
  },
  llenarSelect: function(){
    var responseData = [];
    var viewInstance = this;
    var models = []; // para evitar que el primero sea nulo
    $.ajax({
      type: "GET",
      url: BASE_URL + "estado_usuario/listar",
      data: {csrfmiddlewaretoken: CSRF},
      async: false,
      success: function(data){
        responseData = JSON.parse(data);
      },
      error: function(error){
        $("#" + viewInstance.targetMensaje).removeClass("color-success");
        $("#" + viewInstance.targetMensaje).removeClass("color-warning");
        $("#" + viewInstance.targetMensaje).addClass("color-danger");
        $("#" + viewInstance.targetMensaje).html("Error en listar los tipos de estaciones");
        $("html, body").animate({ scrollTop: $("#" + viewInstance.targetMensaje).offset().top }, 1000);
        console.log(error);
      }
    });
    if(responseData.length > 0){
      for(var i = 0; i < responseData.length; i++){
        var modelo = new EstadoUsuario(responseData[i]);
        this.estadoUsuariosSelect.models.push(modelo);
      }
    }
  },
  obtenerUsuarioCorreo: function(){
    var viewInstance = this;
    $.ajax({
      type: "GET",
      url: BASE_URL + "usuario/obtener_usuario_correo/" + viewInstance.get("usuario_id"),
      data: {csrfmiddlewaretoken: CSRF},
      async: false,
      success: function(data){
        viewInstance.model = JSON.parse(data);
      },
      error: function(error){
        $("#" + viewInstance.targetMensaje).removeClass("color-success");
        $("#" + viewInstance.targetMensaje).removeClass("color-warning");
        $("#" + viewInstance.targetMensaje).addClass("color-danger");
        $("#" + viewInstance.targetMensaje).html("Error en listar los tipos de estaciones");
        $("html, body").animate({ scrollTop: $("#" + viewInstance.targetMensaje).offset().top }, 1000);
        console.log(error);
      }
    });
  },
  events: {
    // se está usando asignacion dinamica de eventos en el constructor
    // tabla modulos
  },
});