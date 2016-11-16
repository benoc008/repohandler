package tv.ustream.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tv.ustream.domain.Repository;
import tv.ustream.exception.NameAlreadyInUseException;
import tv.ustream.exception.NoSuchRepositoryException;
import tv.ustream.logic.RepositoryHandler;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/")
public class RepositoryHandlerRestController {

    private RepositoryHandler repositoryHandler = new RepositoryHandler();

    @RequestMapping(method = GET)
    public Repository get(@RequestParam(value = "name") String name) throws NoSuchRepositoryException {
        return repositoryHandler.getByName(name);
    }

    @RequestMapping(method = POST)
    public ResponseEntity post(@RequestBody Repository repository) throws NameAlreadyInUseException {
        repositoryHandler.add(repository);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "by-count", method = GET)
    public List<Repository> getByCount(@RequestParam(value = "count") Long accessCount) throws NoSuchRepositoryException {
        return repositoryHandler.getByAccessCount(accessCount);
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity delete(@RequestParam String name) throws NoSuchRepositoryException {
        repositoryHandler.delete(name);
        return new ResponseEntity(HttpStatus.OK);
    }

}
