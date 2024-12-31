import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import Logger

data class Task(val id: Int, var title: String, var description: String)

val tasks = mutableListOf<Task>()
var nextId = 1
val logger = Logger()

fun printTodoHeader() {
    val purple = "\u001B[35m" // ANSI escape code for purple
    val reset = "\u001B[0m"   // ANSI escape code to reset color

    val header = """
_________ _______  ______   _______    _       _________ _______ _________
\__   __/(  ___  )(  __  \ (  ___  )  ( \      \__   __/(  ____ \\__   __/
   ) (   | (   ) || (  \  )| (   ) |  | (         ) (   | (    \/   ) (
   | |   | |   | || |   ) || |   | |  | |         | |   | (_____    | |
   | |   | |   | || |   | || |   | |  | |         | |   (_____  )   | |
   | |   | |   | || |   ) || |   | |  | |         | |         ) |   | |
   | |   | (___) || (__/  )| (___) |  | (____/\___) (___/\____) |   | |
   )_(   (_______)(______/ (_______)  (_______/\_______/\_______)   )_(
    """.trimIndent()

    println(purple + header + "" + reset)
}

fun main() {
    while (true) {
        printTodoHeader()
        println("1. Aufgabe Hinzufügen")
        println("2. Aufgaben ansehen")
        println("3. Aufgabe updaten")
        println("4. Aufgabe löschen")
        println("5. Verlassen")
        logger.logInfo("Wähle eine Option: ")

        when (readln().toIntOrNull()) {
            1 -> addTask(tasks, nextId++)
            2 -> viewTasks(tasks)
            3 -> updateTask(tasks)
            4 -> deleteTask(tasks)
            5 -> {
                println("Auf Wiedersehen!")
                break
            }
            else -> logger.logError("Unvalide Option. Probiere es nochmal.")
        }
    }
}

fun addTask(tasks: MutableList<Task>, nextId: Int) {
    println("Gebe den Titel und die Beschreibung der Aufgabe ein:")
    print("Titel: ")
    val title = readln()
    print("Beschreibung: ")
    val description = readln()
    tasks.add(Task(nextId, title, description))
    logger.logSuccess("Aufgabe erfolgreich hinzugefügt!")
}

fun viewTasks(tasks: List<Task>) {
    if (tasks.isEmpty()) {
        logger.logError("Keine Aufgaben verfügbar.")
    } else {
        for (task in tasks) {
            println("ID: ${task.id}, Titel: ${task.title}, Beschreibung: ${task.description}")
        }
    }
}

fun updateTask(tasks: MutableList<Task>) {
    print("Gib die Aufgaben ID an um die Aufgabe zu updaten: ")
    val id = readln().toIntOrNull()
    val task = tasks.find { it.id == id }
    if (task != null) {
        print("Neuer Titel: ")
        task.title = readln()
        print("Neue Beschreibung: ")
        task.description = readln()
        logger.logSuccess("Aufgabe erfolgreich aktualiesiert!")
    } else {
        logger.logError("Aufgabe wurde nicht gefunden.")
    }
}

fun deleteTask(tasks: MutableList<Task>) {
    print("Gib die Aufgaben ID an: ")
    val id = readln().toIntOrNull()
    if (tasks.removeIf { it.id == id }) {
        logger.logSuccess("Aufgabe erfolgreich entfernt!")
    } else {
        logger.logError("Aufgabe nicht gefunden.")
    }
}

fun saveLocal(tasks: MutableList<Task>) {
    val file = File("tasks.json")
    val json = Json.encodeToString(tasks)
    file.writeText(json)
    logger.logSuccess("Aufgaben erfolgreich gespeichert!")
}

fun loadLocal(tasks: MutableList<Task>) {
    val file = File("tasks.json")
    if (file.exists()) {
        val json = file.readText()
        tasks.addAll(Json.decodeFromString(json))
        logger.logSuccess("Aufgaben erfolgreich geladen!")
    } else {
        logger.logError("Keine Aufgaben gefunden.")
    }
}