package models;

/**
 * Clase Estudiante que extiende Usuario para datos específicos académicos.
 * MIGRADO: Mantiene toda la funcionalidad original para compatibilidad web.
 */
public class Estudiante extends Usuario {
    private String carrera;
    private String numeroEstudiante;
    private double promedio;

    // Constructor por defecto para compatibilidad con formularios web
    public Estudiante() {
        super();
    }

    public Estudiante(String nombre, String apellido, String email, String username, String password, 
                     String carrera, String numeroEstudiante, double promedio) {
        super(nombre, apellido, email, username, password);
        this.carrera = carrera;
        this.numeroEstudiante = numeroEstudiante;
        this.promedio = promedio;
    }

    // Constructor compatible con la versión original
    public Estudiante(String username, String password, String carrera, String numeroEstudiante, double promedio) {
        super(username, password, numeroEstudiante, numeroEstudiante, numeroEstudiante);
        this.carrera = carrera;
        this.numeroEstudiante = numeroEstudiante;
        this.promedio = promedio;
    }

    // Getters y Setters - SIN CAMBIOS de la versión original
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

    /**
     * Validación específica para estudiantes
     */
    @Override
    public boolean isValid() {
        return super.isValid() && 
               carrera != null && !carrera.trim().isEmpty() &&
               numeroEstudiante != null && !numeroEstudiante.trim().isEmpty() &&
               promedio >= 0.0 && promedio <= 5.0; // Asumiendo escala 0-5
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
