package models;
public class Estudiante extends Usuario {
    private String carrera;
    private String numeroEstudiante;
    private double promedio;

    public Estudiante(String username, String password, String carrera, String numeroEstudiante, double promedio) {
        super(username, password, numeroEstudiante, numeroEstudiante, numeroEstudiante);
        this.carrera = carrera;
        this.numeroEstudiante = numeroEstudiante;
        this.promedio = promedio;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNumeroEstudiante() {
        return numeroEstudiante;
    }

    public void setNumeroEstudiante(String numeroEstudiante) {
        this.numeroEstudiante = numeroEstudiante;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "carrera='" + carrera + '\'' +
                ", numeroEstudiante='" + numeroEstudiante + '\'' +
                ", promedio=" + promedio +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}