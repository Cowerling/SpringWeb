package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.data.SpillteRepository;

/**
 * Created by dell on 2017-1-11.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private SpillteRepository spillteRepository;

    @Autowired
    public SpittleController(SpillteRepository spillteRepository) {
        this.spillteRepository = spillteRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String spittles(Model model) {
        model.addAttribute(spillteRepository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }
}
