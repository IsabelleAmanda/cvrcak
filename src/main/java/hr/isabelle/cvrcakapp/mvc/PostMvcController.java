package hr.isabelle.cvrcakapp.mvc;

import hr.isabelle.cvrcakapp.model.Post;
import hr.isabelle.cvrcakapp.model.User;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/cvrcak/mvc")
public class PostMvcController {
    /*private String token;
    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/post/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Post> getAllPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if(currentUser != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<List<Post>> entity = new HttpEntity<>(requestJson,headers);

            return restTemplate.getForObject("http://localhost:8080/post/all", List.class);
        }
    }*/

}
