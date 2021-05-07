package Hilos;

/*
* ID_OPERACION
* 1 -> Espera solicitud para enviar la hora del servidor
* 2 -> Solicita la hora del servidor
* 3 -> Registrar usuario
* 4 -> Enviar libro
* */
public class Conexion {

    int idOperacion;
    int puerto;
    String direccion;
    boolean reiniciar;
    
    public Conexion(int idOperacion, int puerto, String direccion, boolean reiniciar) {
        this.idOperacion = idOperacion;
        this.puerto = puerto;
        this.direccion = direccion;
        this.reiniciar = reiniciar;
    }

    public boolean isReiniciar() {
        return reiniciar;
    }

    public void setReiniciar(boolean reiniciar) {
        this.reiniciar = reiniciar;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Conexion{");
        sb.append("idOperacion=").append(idOperacion);
        sb.append(", puerto=").append(puerto);
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
