// =====================================================================
// Ejercicios 3 y 5: Detección y conteo de entidades
// =====================================================================

/**
 * Responsable de detectar entidades nombradas en texto libre y
 * producir estadísticas sobre ellas.
 */
object Analyzer {

  /**
   * Detecta las entidades del diccionario que aparecen en el texto dado.
   *
   * @param text       texto a analizar (ej: título o cuerpo de un post)
   * @param dictionary lista de entidades conocidas (cargadas desde los diccionarios)
   * @return lista de entidades cuyo texto aparece en el texto analizado
   *
   * TODO (Ejercicio 3): Implementar este método.
   *
   *   Para cada entidad en el diccionario, verificar si su texto aparece en el
   *   texto del post. Retornar únicamente las entidades que aparecen.
   *
   *   Ejemplo:
   *     text       = "Scala fue creado en EPFL por Martin Odersky"
   *     dictionary = List(
   *                    ProgrammingLanguage("Scala"),
   *                    University("EPFL"),
   *                    Person("Martin Odersky"),
   *                    Person("Ada Lovelace")   ← no aparece en el texto
   *                  )
   *     resultado  = List(
   *                    ProgrammingLanguage("Scala"),
   *                    University("EPFL"),
   *                    Person("Martin Odersky")
   *                  )
   */
  def detectEntities(text: String, dictionary: List[NamedEntity]): List[NamedEntity] = {
    val cleanText = text.toLowerCase
    
    // buscar todos los candidatos (puede dar duplicados o entidades anidadas (como C y C++))
    val candidates = dictionary.filter(entity => entity.isPresentIn(cleanText))

    // me quedo con una entidad (self) si no existe otra (other) que sea mas larga y la contenga
    // para que si dice por ej C++ o C# no piense que es C
    candidates.filter { self =>
      !candidates.exists { other =>
        other.text.length > self.text.length && other.text.toLowerCase.contains(self.text.toLowerCase)
        }
      }.distinct // distinct asegura que si una entidad aparece varias veces en el mismo post, solo la devolvamos una vez
    }

  /**
   * Cuenta cuántas entidades de cada tipo fueron detectadas.
   *
   * @param entities lista de entidades detectadas
   * @return mapa de entityType → cantidad de apariciones
   *
   * TODO (Ejercicio 5): Implementar este método.
   *
   *   Ejemplo:
   *     entities = List(
   *                  Person("Alan Turing"),
   *                  ProgrammingLanguage("Scala"),
   *                  Person("Ada Lovelace"),
   *                  University("MIT")
   *                )
   *     resultado = Map(
   *                   "Person"              -> 2,
   *                   "ProgrammingLanguage" -> 1,
   *                   "University"          -> 1
   *                 )
   */
  def countByType(entities: List[NamedEntity]): Map[String, Int] = {

    val entitiesgroups = entities.groupBy(_.entityType).map{
      case(tipo, lista) => (tipo, lista.size)
    }

    //junto mis entindades en grupitos
    //entitiesgroups devuelve Map[String, List[NamedEntity]], despues del map con case (_,[_]) a cada lista le aplico .size
    //despues del map se devuelve Map[Str,Int]
    entitiesgroups
  }
}
