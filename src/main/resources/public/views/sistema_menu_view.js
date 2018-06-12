var SistemaMenuView = ModalView.extend({
  sistemaId: null,
  initialize: function(options){
    this.targetMensaje = options["targetMensaje"];
    // herencia de atributos, móetodos y eventos
    ModalView.prototype.initialize.apply(this, [options])
    this.inheritEvents(ModalView);
    // delegación de eventos
    this.delegateEvents();
    this.tablaModulo = new TableView(dataTablaModulo);
    //this.tablaSubtitulo = new TablaSubtituloView(dataTablaSubtitulo);
    //this.tablaItem = new TableView(dataTablaItem);
  },
  events: {
    // se está usando asignacion dinamica de eventos en el constructor
    "click #tablaModulo > tfoot > tr > td > button.agregar-fila": "agregarFilaModulo",
		"click #tablaModulo > tfoot > tr > td > button.guardar-tabla": "guardarTablaModulo",
		"keyup #tablaModulo > tbody > tr > td > input.text": "inputTextEscribirModulo",
		"click #tablaModulo > tbody > tr > td > i.quitar-fila": "quitarFilaModulo",
  },
  //evnetos tabla de modulos
  inputTextEscribirModulo: function(event){
    this.tablaModulo.inputTextEscribir(event);
  },
  quitarFilaModulo: function(event){
    this.tablaModulo.quitarFila(event);
  },
  guardarTablaModulo: function(event){
    this.tablaModulo.extraData = {sistema_id: this.sistemaId};
    this.tablaModulo.guardarTabla(event);
  },
  agregarFilaModulo: function(event){
    this.tablaModulo.agregarFila(event);
  },
});