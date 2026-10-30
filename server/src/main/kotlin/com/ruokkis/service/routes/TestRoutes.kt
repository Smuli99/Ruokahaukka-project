package com.ruokkis.service.routes

import com.ruokkis.service.database.AppDatabase
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.java.UUIDTable
import org.jetbrains.exposed.v1.dao.java.UUIDEntity
import org.jetbrains.exposed.v1.dao.java.UUIDEntityClass
import java.util.UUID

object Dogs : UUIDTable() {
    val name = varchar("name", 50).index()
}

class Dog(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Dog>(Dogs)

    var name: String by Dogs.name
}

@Serializable
data class DogDto(
    val uuid: String,
    val name: String
) {
    companion object {
        fun fromEntity(dog: Dog): DogDto = DogDto(
            uuid = dog.id.value.toString(),
            name = dog.name
        )
    }
}

@Serializable
data class NewDogDto(
    val name: String
)

fun Route.testRoutes(db: AppDatabase) {

    get("/test") {
        val dogs = db.dbQuery {
            Dog.all().toList().map { DogDto.fromEntity(it) }
        }
        call.respond(dogs)
    }

    post("/test") {
        val dogDto = call.receive<NewDogDto>()

        val newDog = db.dbQuery {
            Dog.new {
                name = dogDto.name
            }
        }

        call.respond(HttpStatusCode.Created, DogDto.fromEntity(newDog))
    }
}