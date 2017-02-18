package floramowebg

class LugarEspecie {

    Lugar lugar
    Especie especie

    static constraints = {
    }

    @Override
    String toString() {
        return "${especie.nombre} [${lugar.nombre}]"
    }
}
