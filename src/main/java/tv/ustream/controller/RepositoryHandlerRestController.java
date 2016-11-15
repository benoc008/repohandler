package tv.ustream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tv.ustream.domain.Repository;
import tv.ustream.logic.RepositoryHandler;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/")
public class RepositoryHandlerRestController {

    private RepositoryHandler repositoryHandler;

    @Autowired
    public RepositoryHandlerRestController(RepositoryHandler repositoryHandler) {
        this.repositoryHandler = repositoryHandler;
    }

    @RequestMapping(method=GET)
    public Repository get(@RequestParam(value = "name") String name){
        return repositoryHandler.getByName(name);
    }

    @RequestMapping(method=POST)
    public void post(@RequestBody Repository repository){
        repositoryHandler.add(repository);
    }

    @RequestMapping(value="by-count", method=GET)
    public List<Repository> getByCount(@RequestParam(value = "count") Long accessCount){
        return repositoryHandler.getByAccessCount(accessCount);
    }

    @RequestMapping(method=DELETE)
    public void delete(@RequestParam String name){
        repositoryHandler.delete(name);
    }

}
