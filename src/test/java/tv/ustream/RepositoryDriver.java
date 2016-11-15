package tv.ustream;

import org.springframework.boot.test.web.client.TestRestTemplate;
import tv.ustream.domain.Repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryDriver {

    private TestRestTemplate testRestTemplate;

    private Repository getResult;

    private List<Repository> getByAccessCountResult;

    public RepositoryDriver(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public RepositoryDriver addRepository(Repository repository) {
        testRestTemplate.postForObject("/", repository, Repository.class);
        return this;
    }

    public RepositoryDriver deleteRepository(String name) {
        testRestTemplate.delete("/", name);
        return this;
    }

    public RepositoryDriver callGetRepository(String name) {
        getResult = testRestTemplate.getForObject("/?name={name}", Repository.class, name);
        return this;
    }

    public RepositoryDriver thenGetRepositoryReturns(String name, String creator, long accessCount) {
        assertThat(getResult.getName()).isEqualTo(name);
        assertThat(getResult.getCreator()).isEqualTo(creator);
        assertThat(getResult.getAccessCounter()).isEqualTo(accessCount);
        return this;
    }

    public RepositoryDriver callGetRepositoryByAccessCount(Long accessCount) {
        getByAccessCountResult = testRestTemplate.getForObject("/by-count?count={accessCount}", List.class, accessCount);
        return this;
    }

    public RepositoryDriver thenGetRepositoryByAccessCountReturns(List<String> expectedRepositoryNames) {
        assertThat(getByAccessCountResult)
                .extracting(Repository::getName)
                .containsOnly((String[]) expectedRepositoryNames.toArray());
        return this;
    }

    public RepositoryDriver thenGetRepositoryReturnsNotFound() {
        //todo implement
        return this;
    }

}
