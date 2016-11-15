package tv.ustream.logic;

import org.springframework.stereotype.Component;
import tv.ustream.domain.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RepositoryHandler {

    private static List<Repository> repositories = new ArrayList<>();

    public void add(Repository repository){
        //todo validate
        repositories.add(repository);
    }

    public Repository getByName(String name){
        Repository repository = findByName(name);
        repository.setAccessCounter(repository.getAccessCounter() + 1);
        return repository;
    }

    public List<Repository> getByAccessCount(long count){
        List<Repository> results =
                repositories
                        .stream()
                        .filter(x -> x.getAccessCounter() >= count)
                        .collect(Collectors.toList());
        results.forEach(x -> x.setAccessCounter(x.getAccessCounter() + 1));
        if(results.isEmpty()){
            throw new NoSuchElementException();
        }
        return results;
    }

    private Repository findByName(String name) throws NoSuchElementException {
        Optional<Repository> repository = repositories.stream().filter(x -> x.getName().equals(name)).findFirst();
        if(repository.isPresent()){
            return repository.get();
        }
        throw new NoSuchElementException();
    }

    public void delete(String name){
        //todo implement
    }

}
