package wm.routes

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import wm.data.DAOInstances
import wm.functions.multipartDataToFeast
import wm.models.Comment
import wm.models.User
import wm.storage.CommentsStorage
import wm.templates.*
import java.nio.file.Path
import kotlin.io.path.writeText

fun Route.fiestasPatronalesRouting() {
    val commentstorage = CommentsStorage()
    commentstorage.loadComments()
    val dao = DAOInstances()
    val feastDAO = dao.feastDAO
    val userDAO = dao.userDAO
    var currentUser: User? = userDAO.getUser("admin","admin")

    route("/fiestaspatronales") {

        get {
            call.respondRedirect("fiestaspatronales/home")
        }
        get("/") {
            call.respondRedirect("home")
        }
        get("login") {
            call.respondHtmlTemplate(LoginTemplate()) {}
        }

        post("checkLogin") {
            val textForm = call.receiveText()
            val params = getParams(textForm)
            val nickname = params["nickname"]
            val password = params["password"]

            if (userDAO.checkUser(nickname, password)) {
                currentUser = userDAO.getUser(nickname, password)
                call.respondRedirect("home")
            } else {
                call.respondHtml {
                    body { h1 { +"Nombre y/o contraseña incorrecta para el usuario: $nickname..." } }
                }
            }
        }
        post("newUser") {
            val params = getParams(call.receiveText())
            val nickname = params["nickname"]!!
            val password = params["passwor"]!!


            if (userDAO.checkUser(nickname, password)) {
                call.respondHtml {
                    body { h1 { + "El usuario: $nickname ya esta registrado..." } }
                }
            } else {
                userDAO.newUser(nickname, password)
                currentUser = userDAO.getUser(nickname, password)
                call.respondRedirect("home")
            }
        }
        post("addFeast") {
            val feastData = multipartDataToFeast(call.receiveMultipart())
            feastDAO.addFeast(feastData)
            call.respondRedirect("home")
        }
        post("comentario") {
            val params = getParams(call.receiveText())
            val email = params["email"]?:"Anonimo"
            val text = params["comment"]!!
            commentstorage.saveComment(email, text)
            call.respondRedirect("home")
        }

        // Insert Templates:
        get("{id}") {
            if (currentUser==null) call.respondRedirect("login")
            val id = call.parameters["id"]
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "detail"
                this.tableId = id ?: ""
            }
        }
        get("home") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "home"
            }
        }
        get("searcher") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "searcher"
            }
        }
        get("newFeast") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "newFeast"
            }
        }
        get("nextRoute") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "nextRoute"
            }
        }
        get("contact") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "contact"
            }
        }
        get("api") {
            if (currentUser==null) call.respondRedirect("login")
            call.respondHtmlTemplate(LayoutTemplate(dao)) {
                this.content = "api"
            }
        }
    }
}
fun getParams(textForm: String): MutableMap<String, String> {
    val params = mutableMapOf<String,String>()
    var name: String
    var value: String
    textForm.reader().forEachLine {
        name = it.split('=')[0]
        value = it.split('=')[1]
        params[name] = value
    }
    return params
}
/**
 * Para proteger tu cuenta, a partir del 30 de mayo del 2022, Google dejará de admitir aplicaciones
 * y dispositivos de terceros que te pidan que inicies sesión en tu cuenta de Google usando solo tu
 * nombre de usuario y contraseña.
 */
/**
 * SimpleEmail().apply {
        hostName = "smtp.googlemail.com"
        setSmtpPort(465)
        setAuthenticator(DefaultAuthenticator("migueelamaya@gmail.com", ""))
        isSSLOnConnect = true
        setFrom("migueelamaya@gmail.com")
        subject = "Comentario web FiestasPatronales"
        setMsg(params["comment"])
        addTo(params["email"])
        send()
    }
 */