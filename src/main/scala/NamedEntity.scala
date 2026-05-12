import scala.util.matching.Regex

abstract class NamedEntity(val text: String) {
  def entityType: String
  def describe: String = s"[$entityType] $text"
  
  // ejercicio 3: este metodo sirve para que cada entidad sepa decir si ella misma aparece en un texto (encapsulamiento)
  def isPresentIn(content: String): Boolean = {
    // pasar el nombre de la entidad a minúsculas y separar por espacios
    val entityWords = this.text.toLowerCase.split(" ")
    
    // uso 'forall' porque todas las palabras de la entidad deben estar en el texto. Si falta un nombre o apellido, por ej, no se detecta la entidad
    entityWords.forall { word =>
      // Regex.quote: Protege caracteres raros como el + de C++. Sin esto, Regex pensaria que el + es un comando especial
      val escapedWord = Regex.quote(word.toLowerCase)
      
      // (?i) -> No importa mayusculas ni minusculas
      // (?<![\p{L}0-9]) mira ATRAS: que no haya una letra o numero pegado
      // (?![\p{L}0-9]) mira ADELANTE: que no haya una letra o numero pegado
      // esto evita que Java se detecte adentro de JavaScript, por ej
      // \\p{L} cualquier letra (soporta tildes y eñes).
      val pattern = s"(?i)(?<![\\p{L}0-9])$escapedWord(?![\\p{L}0-9])".r  //.r transforma en objeto tipo regex
      
      // verificar si la palabra aparece al menos una vez siguiendo esa regla
      pattern.findFirstIn(content.toLowerCase).isDefined
    }
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

