package tv.ustream.unit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tv.ustream.domain.Repository;
import tv.ustream.exception.NameAlreadyInUseException;
import tv.ustream.exception.NoSuchRepositoryException;
import tv.ustream.logic.RepositoryBuilder;
import tv.ustream.logic.RepositoryHandler;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryHandlerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private RepositoryHandler repositoryHandler;

    @Before
    public void setUp() {
        repositoryHandler = new RepositoryHandler();
    }

    @Test
    public void addRepositoryFailed() throws NameAlreadyInUseException {
        repositoryHandler.add(
                new RepositoryBuilder()
                        .withName("sameName")
                        .withCreator("creator")
                        .build());
        expectedException.expect(NameAlreadyInUseException.class);
        repositoryHandler.add(
                new RepositoryBuilder()
                        .withName("sameName")
                        .withCreator("creator")
                        .build());
    }

    @Test
    public void getByNameNotFound() throws NoSuchRepositoryException {
        expectedException.expect(NoSuchRepositoryException.class);
        repositoryHandler.getByName("unavailable");
    }

    @Test
    public void getByAccessCount() throws NameAlreadyInUseException, NoSuchRepositoryException {
        repositoryHandler.add(
                new RepositoryBuilder()
                        .withName("first")
                        .withCreator("creator")
                        .build());
        repositoryHandler.add(
                new RepositoryBuilder()
                        .withName("second")
                        .withCreator("creator")
                        .build());
        repositoryHandler.getByName("first");
        repositoryHandler.getByName("first");
        List<Repository> results = repositoryHandler.getByAccessCount(2);
        assertThat(results).hasSize(1);
        repositoryHandler.getByAccessCount(3);
        assertThat(results).hasSize(1);
    }

    @Test
    public void getByAccessCountEmpty() throws NoSuchRepositoryException {
        expectedException.expect(NoSuchRepositoryException.class);
        repositoryHandler.getByAccessCount(2);
    }

    @Test
    public void delete() throws NameAlreadyInUseException, NoSuchRepositoryException {
        repositoryHandler.add(new RepositoryBuilder()
                .withName("repository")
                .withCreator("creator")
                .build());
        repositoryHandler.getByName("repository");
        repositoryHandler.delete("repository");
        expectedException.expect(NoSuchRepositoryException.class);
        repositoryHandler.getByName("repository");
    }

    @Test
    public void deleteNotFound() throws NoSuchRepositoryException {
        expectedException.expect(NoSuchRepositoryException.class);
        repositoryHandler.delete("repository");
    }

}
