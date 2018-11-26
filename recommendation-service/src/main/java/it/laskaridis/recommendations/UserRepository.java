package it.laskaridis.recommendations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-service")
public interface UserRepository {

    @RequestMapping(method = RequestMethod.GET, value = "/api/users/{name}")
    User find(@PathVariable("name") String name);
}
