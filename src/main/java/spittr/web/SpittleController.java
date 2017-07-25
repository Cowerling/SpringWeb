package spittr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.Notification;
import spittr.Spittle;
import spittr.SpittleForm;
import spittr.data.SpittleRepository;
import spittr.feed.SpittleFeedService;
import spittr.mail.SpitterEmailService;
import spittr.web.error.Error;
import spittr.web.exception.DuplicateSpittleException;
import spittr.web.exception.SpittleNotFoundException;
import spittr.web.exception.SpittleNotFoundRuntimeException;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by dell on 2017-1-11.
 */
@Controller
@RequestMapping("/spittles")
@ManagedResource(objectName = "spittr:name=SpittleController")
public class SpittleController {
    private static final String MAX_LONG_AS_STRING = Long.MAX_VALUE + "";
    private static final int DEFAULT_SPITTLES_PER_PAGE = 25;
    private int spittlesPerPage = DEFAULT_SPITTLES_PER_PAGE;

    private SpittleRepository spittleRepository;
    private SpittleFeedService spittleFeedService;
    private SpitterEmailService spitterEmailService;

    @Autowired
    private MBeanServerConnection mbeanServerConnection;

    @Autowired
    public SpittleController(@Qualifier("default") SpittleRepository spittleRepository, SpittleFeedService spittleFeedService, SpitterEmailService spitterEmailService) {
        this.spittleRepository = spittleRepository;
        this.spittleFeedService = spittleFeedService;
        this.spitterEmailService = spitterEmailService;
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
        Spittle spittle = spittleRepository.save(new Spittle(form.getId(), form.getMessage(), new Date(), form.getLongitude(), form.getLatitude()));
        spittleFeedService.broadcastSpittle(spittle);
        spitterEmailService.sendSimpleSpittleEmail("surpermama@126.com", spittle);
        return "redirect:/spittles/";
    }

    @MessageMapping("/spittle")
    @SendToUser("/queue/notifications")
    public Notification handleSpittle(Principal principal, SpittleForm form) throws DuplicateSpittleException {
        return new Notification("Saved Spittle");
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

    @ManagedAttribute
    public int getSpittlesPerPage() {
        return spittlesPerPage;
    }

    @ManagedAttribute
    public void setSpittlesPerPage(int spittlesPerPage) {
        this.spittlesPerPage = spittlesPerPage;
    }

    @RequestMapping("/jmx")
    public @ResponseBody Object jmx() throws IOException {
        return mbeanServerConnection.getMBeanCount();
    }
}
