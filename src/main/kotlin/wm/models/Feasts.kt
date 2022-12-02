package wm.models

import org.jetbrains.exposed.dao.id.IntIdTable

object Feasts: IntIdTable() {
    val name = varchar("name",50).uniqueIndex()
    val dates = varchar("dates",50)
    val likes = integer("likes")
    val city = reference("city", Citys.name)
    val town = reference("town", Towns.name)
    val image = varchar("image",8000)
    val description = varchar("description",500)
}