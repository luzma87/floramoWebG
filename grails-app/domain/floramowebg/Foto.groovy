package floramowebg

class Foto {

    String path
    Especie especie

    static constraints = {
    }

    @Override
    String toString() {
        return path
    }
}
