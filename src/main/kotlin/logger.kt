import java.*

class Logger {
    private val RESET = "\u001B[0m"
    private val RED = "\u001B[31m"
    private val GREEN = "\u001B[32m"
    private val YELLOW = "\u001B[33m"
    private val BLUE = "\u001B[34m"
    private val CYAN = "\u001B[36m"
    private val PURPLE = "\u001B[35m"

    fun log(message: String) {
        println(message)
    }

    fun logHeader(message: String) {
        println("${PURPLE}$message$RESET")
    }

    fun logError(message: String) {
        println("${RED}Error: $message$RESET")
    }

    fun logSuccess(message: String) {
        println("${GREEN}Success: $message$RESET")
    }

    fun logWarning(message: String) {
        println("${YELLOW}Warning: $message$RESET")
    }

    fun logInfo(message: String) {
        println("${CYAN}Info: $message$RESET")
    }

    fun logDebug(message: String) {
        println("${PURPLE}Debug: $message$RESET")
    }
}