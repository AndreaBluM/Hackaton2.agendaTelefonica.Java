public class Contacto extends Persona {
    private String telefono;
    private String email;


}

@Override
public String toString(){
    return nombre + " " + apellido + " - " + email + " - " + telefono;
}
