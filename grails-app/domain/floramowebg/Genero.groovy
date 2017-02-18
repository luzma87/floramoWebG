package floramowebg

class Genero {

    String nombre
    String nombre_norm
    Familia familia

    static constraints = {
    }

    @Override
    String toString() {
        return nombre
    }
}
