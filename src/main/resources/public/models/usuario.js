var Usuario = Backbone.Model.extend({
  defaults: {
    usuario: '',
    contrasenia: '',
    correo: '',
    estado_usuario_id: 'E',
  },
  initialize: function() {
  },
});
