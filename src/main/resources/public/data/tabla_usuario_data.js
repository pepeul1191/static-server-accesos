var dataTablaUsuario = {
  el: "#formTableUsuario",
  idTable: "tablaUsuario",
  targetMensaje: "mensajeRptaUsuario",
  mensajes: {
    errorListarAjax: "Error en listar los datos del servidor",
    errorGuardarAjax: "Error en guardar los datos en el servidor",
    success: "Se cargado guardo los cambios en los usuarios",
  },
  urlGuardar: BASE_URL + "usuario/guardar",
  urlListar: BASE_URL + "usuario/listar",
  fila: {
    id: { // llave de REST
      tipo: "td_id",
      estilos: "color: blue; display:none",
      edicion: false,
    },
    usuario: { // llave de REST
      tipo: "label",
      estilos: "width: 200px;",
      edicion: true,
    },
    correo: { // llave de REST
      tipo: "label",
      estilos: "width: 200px;",
      edicion: true,
    },
    filaBotones: {
      estilos: "width: 80px; padding-left: 7px;"
    },
  },
  tableKeys: ["id", "usuario", "correo"],
  filaBotones: [
    {
      tipo: "href",
      claseOperacion: "gestionar-menu",
      clase: "fa-chevron-right",
      estilos: "padding-left: 10px;",
      url: BASE_URL + 'accesos/#/usuario/menu/'/*+ usuario_id*/,
    },
    {
      tipo: "href",
      claseOperacion: "gesionar-permisos",
      clase: "fa-list",
      estilos: "padding-left: 10px;",
      url: BASE_URL + 'accesos/#/usuario/permiso/'/*+ usuario_id*/,
    },
    {
      tipo: "href",
      claseOperacion: "gestionar-roles",
      clase: "fa-id-card-o",
      estilos: "padding-left: 10px;",
      url: BASE_URL + 'accesos/#/usuario/rol/'/*+ usuario_id*/,
    },
    {
      tipo: "i",
      claseOperacion: "quitar-fila",
      clase: "fa-times",
      estilos: "padding-left: 7px;",
    },
  ],
  collection: new UsuariosCollection(),
  model: "Usuario",
};
