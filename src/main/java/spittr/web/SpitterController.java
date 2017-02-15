package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spittr.Spitter;
import spittr.data.SpitterRepository;
import spittr.web.exception.DuplicateSpittleException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 2017-1-13.
 */
@Controller
@RequestMapping("/spitter")
public class SpitterController {
    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(@Qualifier("default") SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute(new Spitter());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(@RequestPart("profilePicture") Part profilePicture, @Valid Spitter spitter, RedirectAttributes model, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "registerForm";
        }

        spitterRepository.save(spitter);
        profilePicture.write("/data/spittr/" + profilePicture.getSubmittedFileName());

        model.addAttribute("username", spitter.getUsername());
        model.addAttribute("spitterId", spitter.getId());
        model.addFlashAttribute(spitter);

        return "redirect:/spitter/{username}";
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        if (!model.containsAttribute("spitter")) {
            model.addAttribute(spitterRepository.findByUsername(username));
        }
        return "profile";
    }
}
