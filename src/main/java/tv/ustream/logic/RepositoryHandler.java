package tv.ustream.logic;

import tv.ustream.domain.Repository;
import tv.ustream.exception.NameAlreadyInUseException;
import tv.ustream.exception.NoSuchRepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositoryHandler {

    private List<Repository> repositories = new ArrayList<>();

    public void add(Repository repository) throws NameAlreadyInUseException {
        List<Repository> repositoriesWithSameName = repositories.stream().filter(x -> x.getName().equals(repository.getName())).collect(Collectors.toList());
        if (!repositoriesWithSameName.isEmpty()) {
            throw new NameAlreadyInUseException();
        }
        repositories.add(repository);
    }

    public Repository getByName(String name) throws NoSuchRepositoryException {
        Repository repository = findByName(name);
        repository.setAccessCounter(repository.getAccessCounter() + 1);
        return repository;
    }

    public List<Repository> getByAccessCount(long count) throws NoSuchRepositoryException {
        List<Repository> results =
                repositories
                        .stream()
                        .filter(x -> x.getAccessCounter() >= count)
                        .collect(Collectors.toList());
        results.forEach(x -> x.setAccessCounter(x.getAccessCounter() + 1));
        if (results.isEmpty()) {
            throw new NoSuchRepositoryException();
        }
        return results;
    }

    private Repository findByName(String name) throws NoSuchRepositoryException {
        Optional<Repository> repository = repositories.stream().filter(x -> x.getName().equals(name)).findFirst();
        if (repository.isPresent()) {
            return repository.get();
        }
        throw new NoSuchRepositoryException();
    }

    public void delete(String name) throws NoSuchRepositoryException {
        if (!repositories.removeIf(x -> x.getName().equals(name))) {
            throw new NoSuchRepositoryException();
        }
    }

}
