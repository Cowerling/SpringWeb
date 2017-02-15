package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spittr.Spittle;
import spittr.SpittleForm;
import spittr.data.SpittleRepository;
import spittr.web.exception.DuplicateSpittleException;
import spittr.web.exception.SpittleNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017-1-11.
 */
@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private static final String MAX_LONG_AS_STRING = Long.MAX_VALUE + "";

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(@Qualifier("default") SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    /*public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }*/

    @RequestMapping(method = RequestMethod.GET)
    /*public String spittles(Model model) {
        model.addAttribute("spittleList", spillteRepository.findSpittles(Long.MAX_VALUE, 20));
        return "spittles";
    }*/
    public List<Spittle> spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max, @RequestParam(value = "count", defaultValue = "20") int count) {
        return spittleRepository.findSpittles(max, count);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSpittle(SpittleForm form) throws DuplicateSpittleException {
        spittleRepository.save(new Spittle(form.getId(), form.getMessage(), new Date(), form.getLongitude(), form.getLatitude()));
        return "redirect:/spittles/";
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(@PathVariable long spittleId, Model model) throws SpittleNotFoundException {
        Spittle spittle = spittleRepository.findOne(spittleId);
        if (spittle == null)
            throw new SpittleNotFoundException();

        model.addAttribute(spittle);
        return "spittle";
    }

    /*@ExceptionHandler(DuplicateSpittleException.class)
    public String handleDuplicateSpittle() {
        return "error/duplicate";
    }*/
}
