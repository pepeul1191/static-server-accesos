var SistemaPermisoView = ModalView.extend({
  initialize: function(options){
    this.targetMensaje = options["targetMensaje"];
    // herencia de atributos, móetodos y eventos
    ModalView.prototype.initialize.apply(this, [options])
    this.inheritEvents(ModalView);
    // delegación de eventos
    this.delegateEvents();
    this.tablaPermiso = new TableView(dataTablaModulo);
  },
  events: {
    // se está usando asignacion dinamica de eventos en el constructor
  },
});