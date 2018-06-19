package models;
import org.javalite.activejdbc.Model;
//import org.javalite.activejdbc.annotations.DbName;
import org.javalite.activejdbc.annotations.Table;

//@DbName("accesos")
@Table("accesos")
public class Acceso extends Model {
  static {
    dateFormat("MM/dd/yyyy", "dob");
    timestampFormat("yyyy.MM.dd G 'at' HH:mm:ss z", "send_time");
  } 
}