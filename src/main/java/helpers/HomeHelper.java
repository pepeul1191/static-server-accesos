package helpers;

public class HomeHelper extends ApplicationHelper{
  public String indexCSS() {
    switch(getConfValue("ambiente")) {
      case "desarrollo":
        return this.loadCSS(new String[] {
          "bower_components/bootstrap/dist/css/bootstrap.min",
          "bower_components/font-awesome/css/font-awesome.min",
          "bower_components/swp-backbone/assets/css/constants",
          "bower_components/swp-backbone/assets/css/dashboard",
          "bower_components/swp-backbone/assets/css/table",
          "css/constants",
          "css/styles"
        });
      case "produccion":
        return this.loadCSS(new String[] {
          "dist/home.min"
        });
      default:
        return this.loadCSS(new String[] {});
    }
  }  
  
  public String indexJS() {
    switch(getConfValue("ambiente")) {
      case "desarrollo":
        return this.loadJS(new String[] {
          "bower_components/jquery/dist/jquery.min",
          "bower_components/bootstrap/dist/js/bootstrap.min",
          "bower_components/underscore/underscore-min",
          "bower_components/backbone/backbone-min",
          "bower_components/swp-backbone/views/table",
          "bower_components/swp-backbone/views/modal",
          "js/home"
        });
      case "produccion":
        return this.loadJS(new String[] {
          "dist/home.min"
        });
      default:
        return this.loadJS(new String[] {});
    }
  }  
}
