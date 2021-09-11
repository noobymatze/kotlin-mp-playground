import io.noobymatze.kokoon.Request
import io.noobymatze.kokoon.Result
import io.noobymatze.kokoon.send
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.*
import react.dom.div

val HelloComponent = fc<Props> {
    val (state, setState) = useState("Hello ")
    val url = "/api"

    useEffect(listOf(url)) {
        send(url, Request.Hello("Matthias")).then {
            when (it) {
                is Result.Success -> setState(it.value.text)
            }
        }
    }

    div {
        +"$state"
    }
}

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            HelloComponent()
        }
    }
}
