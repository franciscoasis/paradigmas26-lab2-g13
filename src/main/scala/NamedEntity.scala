
abstract class NamedEntity(val text: String) {
  def entityType: String
  def describe: String = s"[$entityType] $text"
  
  // ejercicio 3: este metodo sirve para que cada entidad sepa decir si ella misma aparece en un texto
  // esto es encapsular: el mismo objeto es el responsable de encontrarse en el post
  def isPresentIn(content: String): Boolean = {
  // comparar con el texto en minusculas para que la búsqueda sea case insensitive
  // isPresentIn recibe el contenido en minuscula desde afuera por eficiencia (para no hacerlo 1000 veces aca)
    content.contains(this.text.toLowerCase)  // "this" es la entidad
    }
}

class Person(text: String) extends NamedEntity(text) {
  def entityType: String = "Person"
}

class Organization(text: String) extends NamedEntity(text) {
  def entityType: String = "Organization"
}

class Place(text: String) extends NamedEntity(text) {
  def entityType: String = "Place"
}

class Technology(text: String) extends NamedEntity(text) {
  def entityType: String = "Technology"
}

class University(text: String) extends Organization(text) {
  override def entityType: String = "University"
}

class ProgrammingLanguage(text: String) extends Technology(text) {
  override def entityType: String = "ProgrammingLanguage"
}

