package wm.data.models.city

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class City(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<City>(Citys)
    var name by Citys.name
    var location by Citys.location
}
