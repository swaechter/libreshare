package ch.swaechter.libreshare.web.components.index;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class IndexController {

    @Get("/")
    public String sayHello() {
        return "Welcome to Libreshare!";
    }
}
