// =====================================================================
// Ejercicio 2: Cargar diccionarios de entidades
// =====================================================================

/**
 * Responsable de cargar colecciones de entidades nombradas desde archivos.
 *
 * Un diccionario es un archivo de texto plano donde cada línea contiene
 * el nombre de una entidad conocida del mismo tipo.
 *
 * Ejemplo — data/people.txt:
 *   Martin Odersky
 *   Alan Turing
 *   Ada Lovelace
 *
 * Ejemplo — data/languages.txt:
 *   Scala
 *   Python
 *   Haskell
 */
object Dictionary {

  /**
   * Lee un archivo de diccionario y crea una lista de entidades del tipo indicado.
   *
   * @param filePath   ruta al archivo de diccionario (ej: "data/people.txt")
   * @param entityType tipo de entidad: "Person", "University", "ProgrammingLanguage", etc.
   * @return lista de NamedEntity del tipo correspondiente
   *
   * TODO (Ejercicio 2): Implementar este método.
   *
   *   Pasos sugeridos:
   *     1. Leer las líneas del archivo
   *     2. Para cada línea, crear la instancia de la clase correcta
   *     3. Retornar la lista de entidades creadas
   *
   *   Para crear la clase correcta según el tipo se puede usar match:
   *
   */
  def loadFromFile(filePath: String, entityType: String): List[NamedEntity] = {
    import scala.io.Source

    val source = Source.fromFile(filePath)
    try {
      source.getLines()
        .drop(1)
        .filter(_.trim.nonEmpty)          // ignora líneas vacías
        .map { line =>
          entityType match {
            case "Person"              => new Person(line)
            case "Organization"        => new Organization(line)
            case "Place"               => new Place(line)
            case "Technology"          => new Technology(line)
            case "University"          => new University(line)
            case "ProgrammingLanguage" => new ProgrammingLanguage(line)
            case other => throw new IllegalArgumentException(
              s"Unknown entity type: $other"
            )
          }
        }
        .toList
    } finally {
      source.close()
    }
  }

  /**
   * Carga todos los diccionarios disponibles y combina sus entidades.
   *
   * @return lista con todas las entidades de todos los diccionarios
   *
   * TODO (Ejercicio 2): Implementar este método.
   *
   */
  def loadAll(): List[NamedEntity] = {
    // Lista de tuplas (ruta_archivo, tipo_entidad) para pasar por parametro a loadFromFile
    val dictionaries = List(
      ("data/people.txt", "Person"),
      ("data/universities.txt", "University"),
      ("data/languages.txt", "ProgrammingLanguage"),
      ("data/organizations.txt", "Organization"),
      ("data/places.txt", "Place")
    )
  
    dictionaries.flatMap { case (filePath, entityType) =>
      loadFromFile(filePath, entityType)
    }
  }
}
