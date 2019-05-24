package dataclass

//相当于 JavaBean
data class Country(var id: Int, var name: String)

class ComponentX {
    operator fun component1(): String {
        return "你好我是"
    }

    operator fun component2(): Int {
        return 1
    }
}

fun main() {
    val country = Country(0, "中国")
    println(country)
    val (id, name) = country
    println(id)
    println(name)

    country.id = 1



    val (a, b) = ComponentX()
    println("$a ,$b")
}