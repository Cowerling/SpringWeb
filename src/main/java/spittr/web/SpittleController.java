package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.Spittle;
import spittr.SpittleForm;
import spittr.data.SpittleRepository;
import spittr.web.error.Error;
import spittr.web.exception.DuplicateSpittleException;
import spittr.web.exception.SpittleNotFoundException;
import spittr.web.exception.SpittleNotFoundRuntimeException;

import java.net.URI;
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

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Spittle> _spittles(@RequestParam(value = "max", defaultValue = MAX_LONG_AS_STRING) long max, @RequestParam(value = "count", defaultValue = "20") int count) {
        return spittles(max, count);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveSpittle(SpittleForm form) throws DuplicateSpittleException {
        spittleRepository.save(new Spittle(form.getId(), form.getMessage(), new Date(), form.getLongitude(), form.getLatitude()));
        return "redirect:/spittles/";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Spittle> _saveSpittle(@RequestBody Spittle spittle, UriComponentsBuilder uriComponentsBuilder) throws DuplicateSpittleException {
        HttpHeaders headers = new HttpHeaders();
        URI locationUri = uriComponentsBuilder.path("/spittles/").path(spittle.getId().toString()).build().toUri();
        headers.setLocation(locationUri);

        return new ResponseEntity<Spittle>(spittle, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.GET)
    public String spittle(@PathVariable long spittleId, Model model) throws SpittleNotFoundException {
        Spittle spittle = spittleRepository.findOne(spittleId);
        if (spittle == null)
            throw new SpittleNotFoundException();

        model.addAttribute(spittle);
        return "spittle";
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.PUT)
    public Spittle spittle(@PathVariable long spittleId, Spittle spittle) throws DuplicateSpittleException {
        return spittle;
    }

    @RequestMapping(value = "/{spittleId}", method = RequestMethod.DELETE)
    public void spittle(@PathVariable long spittleId) {
        spittleRepository.remove(spittleId);
    }

    @RequestMapping(value = "/spittle/{spittleId}", method = RequestMethod.GET)
    public @ResponseBody Spittle spittleById(@PathVariable long spittleId) {
        Spittle spittle = spittleRepository.findOne(spittleId);
        if (spittle == null) {
            throw new SpittleNotFoundRuntimeException(spittleId);
        }
        return spittle;
    }

    @ExceptionHandler(SpittleNotFoundRuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody Error spittleNotFound(SpittleNotFoundRuntimeException e) {
        long spittleId = e.getSpittleId();
        return new Error(4, "Spittle [" + spittleId + "] not found");
    }

    /*@ExceptionHandler(DuplicateSpittleException.class)
    public String handleDuplicateSpittle() {
        return "error/duplicate";
    }*/
}
