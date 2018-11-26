package it.laskaridis.recommendations;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final Set<Recommendation> adultRecommendations = Sets.newHashSet(
            new Recommendation("French Connection", "thriller"),
            new Recommendation("The Shining", "thriller"),
            new Recommendation("Eyes Wide Shut", "thriller")
    );
    private final Set<Recommendation> kidsRecommendations = Sets.newHashSet(
            new Recommendation("Puff The Magic Dragon", "kids"),
            new Recommendation("Lion King", "kids"),
            new Recommendation("IT", "thriller")
    );
    private final Set<Recommendation> familyRecommendations = Sets.newHashSet(
            new Recommendation("Indiana Jones And The Last Crusade", "action"),
            new Recommendation("The Abyss", "thriller"),
            new Recommendation("Jurassic Park", "action")
    );

    @Autowired
    private UserRepository users;

    @HystrixCommand(fallbackMethod = "defaultRecommendations", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    @RequestMapping("/{username}")
    public Set<Recommendation> index(@PathVariable String username) {
        User user = users.find(username);
        if (user == null)
            throw new UserNotFoundException(username);
        if (user.getAge() > 18) {
            return adultRecommendations;
        } else {
            return kidsRecommendations;
        }
    }

    public Set<Recommendation> defaultRecommendations(String username) {
        return familyRecommendations;
    }
}
