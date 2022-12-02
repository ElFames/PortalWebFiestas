package wm.templates

import io.ktor.server.html.*
import kotlinx.html.*
import wm.data.FeastDAO

class LayoutTemplate(val feastDAO: FeastDAO) : Template<HTML> {
    lateinit var content: String

    override fun HTML.apply() {
        head {
            link(rel = "icon", href = "/static/logo.png", type="image/png")
            link(rel = "stylesheet", href = "/static/style.css", type = "text/css")
        }
        body {
            header {
                a("/fiestaspatronales/home") {
                    img {
                        src = "/static/logo.png"
                        alt = "logo"
                    }
                }
            }

            main {
                div("menu") {
                    nav {
                        ul {
                            li {
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/home"
                                    +"Inicio-Resumen"
                                }
                            }
                        }
                        ul {
                            li{
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/searcher"
                                    +"Buscador de fiestas"
                                }
                            }
                        }
                        ul {
                            li{
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/popular"
                                    +"Fiestas populares"
                                }
                            }
                        }
                        ul {
                            li{
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/nextRoute"
                                    +"Proximas Rutas"
                                }
                            }
                        }
                        ul {
                            li{
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/contact"
                                    +"Contactanos"
                                }
                            }
                        }
                        ul {
                            li{
                                a(classes="enlace") {
                                    href = "/fiestaspatronales/api"
                                    +"Endpoint Api"
                                }
                            }
                        }
                    }
                }
                div ("maincontent"){
                    when (content) {
                        "home" -> this.insert(HomeTemplate(feastDAO), TemplatePlaceholder())
                        "searcher" -> this.insert(SearcherTemplate(feastDAO), TemplatePlaceholder())
                        "popular" -> this.insert(PopularTemplate(feastDAO), TemplatePlaceholder())
                        "nextRoute" -> this.insert(NextRouteTemplate(feastDAO), TemplatePlaceholder())
                        "contact" -> this.insert(ContactTemplate(feastDAO), TemplatePlaceholder())
                        "api" -> this.insert(ApiTemplate(feastDAO), TemplatePlaceholder())
                    }
                }
            }
        }
    }
}