package floramowebg

class Especie {

    String nombre
    String nombre_norm
    Genero genero
    String idTropicos
    Color color1
    Color color2
    FormaVida formaVida1
    FormaVida formaVida2
    String descripcion_es
    String descripcion_en
    String distribucion_es
    String distribucion_en
    String thumbnail

    static constraints = {
        color2 nullable: true
        formaVida2 nullable: true
    }

    @Override
    String toString() {
        return nombre
    }

    String getNombreCientifico() {
        return "${genero.nombre} ${nombre}"
    }

    String getFamilia() {
        return genero.familia.nombre
    }
}
