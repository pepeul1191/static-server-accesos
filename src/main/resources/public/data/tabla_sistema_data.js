var dataTablaSistema = {
  el: "#formTableSistema",
  idTable: "tablaSistema",
  targetMensaje: "mensajeRptaSistema",
  mensajes: {
    errorListarAjax: "Error en listar los datos del servidor",
    errorGuardarAjax: "Error en guardar los datos en el servidor",
    success: "Se cargado guardo los cambios en los autores",
  },
  urlGuardar: BASE_URL + "sistema/guardar",
  urlListar: BASE_URL + "sistema/listar",
  fila: {
    id: { // llave de REST
      tipo: "td_id",
      estilos: "color: blue; display:none",
      edicion: false,
    },
    nombre: { // llave de REST
      tipo: "text",
      estilos: "width: 300px;",
      edicion: true,
    },
    version: { // llave de REST
      tipo: "text",
      estilos: "width: 80px;",
      edicion: true,
    },
    repositorio: { // llave de REST
      tipo: "text",
      estilos: "width: 400px;",
      edicion: true,
    },
    filaBotones: {
      estilos: "width: 80px; padding-left: 7px;"
    },
  },
  filaBotones: [
    {
      tipo: "i",
      claseOperacion: "quitar-fila",
      clase: "fa-times",
      estilos: "padding-left: 10px;",
    },
  ],
  collection: new SistemasCollection(),
  model: "Sistema",
};
