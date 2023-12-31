package wm.ui.templates

import io.ktor.server.html.*
import kotlinx.html.*

class LayoutTemplate : Template<HTML> {
    val content = Placeholder<FlowContent>()

    override fun HTML.apply() {
        head {
            link(rel = "icon", href = "/static/logo.png", type="image/png")
            link(rel = "stylesheet", href = "/static/style.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/homeStyle.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/newFeastStyle.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/contactStyle.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/searcherStyle.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/detailStyle.css", type = "text/css")
            link(rel = "stylesheet", href = "/static/loginStyle.css", type = "text/css")
            script {
                type = "text/javascript"
                src = "static/newFeast.js"
            }
        }
        body {
            header {
                a("/") {
                    img(classes="logo") {
                        src = "/static/logo.png"
                        alt = "logo"
                    }
                }
                nav {
                    ul("menu") {
                        li {
                            a {
                                href = "/home"
                                +"INICIO | "
                            }
                        }
                        li{
                            a {
                                href = "/searcher"
                                +"BUSCADOR | "
                            }
                        }
                        li{
                            a {
                                href = "/contact"
                                +"CONTACTO | "
                            }
                        }
                        li{
                            a {
                                href = "/newFeast"
                                +"APORTAR | "
                            }
                        }
                        li{
                            a {
                                href = "/login"
                                +"UNLOGIN | "
                            }
                        }
                        li{
                            a {
                                href = "/endpoints"
                                +"API"
                            }
                        }
                    }
                }
            }
            main {
                div ("maincontent"){
                    insert(content)
                }
            }
        }
    }
}