package tv.ustream.logic;

import org.apache.log4j.Logger;
import tv.ustream.domain.Repository;
import tv.ustream.exception.NameAlreadyInUseException;
import tv.ustream.exception.NoSuchRepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepositoryHandler {

    private static Logger LOGGER = Logger.getLogger(RepositoryHandler.class);
    private List<Repository> repositories = new ArrayList<>();

    public void add(Repository repository) throws NameAlreadyInUseException {
        List<Repository> repositoriesWithSameName = repositories.stream().filter(x -> x.getName().equals(repository.getName())).collect(Collectors.toList());
        if (!repositoriesWithSameName.isEmpty()) {
            LOGGER.warn("Unable to create new Repository with name '" + repository.getName() + "', because it is already in use.");
            throw new NameAlreadyInUseException();
        }
        repositories.add(repository);
        LOGGER.info("Repository added: " + repository.toString());
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
            LOGGER.warn("No repositories found with access count >= " + count + ".");
            throw new NoSuchRepositoryException();
        }
        LOGGER.info(results.size() + " repositories found with access count >= " + count + ".");
        return results;
    }

    private Repository findByName(String name) throws NoSuchRepositoryException {
        Optional<Repository> repository = repositories.stream().filter(x -> x.getName().equals(name)).findFirst();
        if (repository.isPresent()) {
            LOGGER.info("Repository found: " + repository.get().toString());
            return repository.get();
        }
        LOGGER.warn("Repository not found with name: '" + name + "'.");
        throw new NoSuchRepositoryException();
    }

    public void delete(String name) throws NoSuchRepositoryException {
        if (!repositories.removeIf(x -> x.getName().equals(name))) {
            LOGGER.warn("Repository not found with name: '" + name + "'.");
            throw new NoSuchRepositoryException();
        }
        LOGGER.info("Repository '" + name + "' deleted.");
    }

}
