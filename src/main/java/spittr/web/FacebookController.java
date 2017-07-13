package spittr.web;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import spittr.Spittle;
import spittr.SpittleForm;
import spittr.web.exception.NotModifiedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017-7-11.
 */
@RestController
@RequestMapping("/facebook")
public class FacebookController {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String, Object> fetchFacebookProfile(@PathVariable Long id) throws NotModifiedException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Spittle> responseEntity = restTemplate.getForEntity("http://localhost:8080/spittr/spittles/spittle/{id}", Spittle.class, id);
        HttpHeaders headers = responseEntity.getHeaders();

        if (responseEntity.getStatusCode() == HttpStatus.NOT_MODIFIED) {
            throw new NotModifiedException();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lastModified", headers.getLastModified());
        map.put("value", headers.get("Options"));
        map.put("first", headers.getFirst("code"));
        map.put("spittle", responseEntity.getBody());
        return map;
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public void updateSpittle(Spittle spittle) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put("http://localhost:8080/spittr/spittles/{spittleId}", spittle, spittle.getId());
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Spittle deleteSpittle(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        //Spittle spittle = restTemplate.getForObject("http://localhost:8080/spittr/spittles/spittle/{id}", Spittle.class, id);
        restTemplate.delete("http://localhost:8080/spittr/spittles/{id}", id);
        return new Spittle();
    }

    @RequestMapping("/post/{spittleId}")
    public String postSpittleForObject(@PathVariable long spittleId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForLocation("http://localhost:8080/spittr/spittles", new SpittleForm(spittleId, "ERX")).toString();
    }

    @RequestMapping("/exchange/{spittleId}")
    public Spittle exchange(@PathVariable long spittleId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Accept", "application/json");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        ResponseEntity<Spittle> responseEntity = restTemplate.exchange("http://localhost:8080/spittr/spittles/spittle/{spittleID}", HttpMethod.GET, requestEntity, Spittle.class, spittleId);
        return responseEntity.getBody();
    }
}
