package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {
    @RequestMapping(method = RequestMethod.GET)
    public String websocket() {
        return "websocket";
    }
}
