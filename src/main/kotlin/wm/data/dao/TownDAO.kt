package wm.data.dao

import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.transaction
import wm.ui.models.JsonTown
import wm.data.models.town.Town
import wm.data.models.town.Towns

class TownDAO(private val cityDAO: CityDAO) {
    val defaultTowns = mutableListOf("Cerdanyola", "Sitges", "Sagunt", "Aiora", "Carmona", "Osuna",)

    fun checkTown(townName: String) =
        transaction {
            Town.find { Towns.name eq townName }.firstOrNull()
        } != null
    fun getTown(townName: String): Town? {
        return transaction {
            Town.find { Towns.name eq townName}.firstOrNull()
        }
    }
    fun getTownById(townId: Int): Town? {
        return transaction {
            Town.find { Towns.id eq townId }.firstOrNull()
        }
    }
    fun getAllTowns(): SizedIterable<Town> {
        return transaction {
            Town.all()
        }
    }
    fun getJsonTown(town: Town): JsonTown {
        return transaction {
            JsonTown(town.id.value,town.name, cityDAO.getJsonCity(town.city))
        }

    }
    fun getJsonsTowns(): MutableList<JsonTown> {
        val jsonTownList = mutableListOf<JsonTown>()
        transaction {
            getAllTowns().forEach {
                jsonTownList.add(
                    JsonTown(
                        it.id.value,
                        it.name,
                        cityDAO.getJsonCity(it.city)
                    )
                )
            }
        }
        return jsonTownList
    }
    fun addTown(name: String, location: String? = null, cityName: String) {
        val city = cityDAO.getCityByName(cityName) ?: return
        if (!checkTown(name)) {
            transaction {
                Town.new {
                    this.name = name
                    this.location = location
                    this.city = city
                }
            }
        }
    }
    fun getTownsByCityName(cityName: String) =
        transaction {
            Town.find { Towns.cityId.eq(cityDAO.getCityByName(cityName)!!.id)}
        }
}