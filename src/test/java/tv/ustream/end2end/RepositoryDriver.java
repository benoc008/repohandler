package tv.ustream.end2end;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tv.ustream.domain.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryDriver {

    private TestRestTemplate testRestTemplate;

    private Repository getResult;

    private List<Repository> getByAccessCountResult;

    private HttpStatus responseStatus;


    public RepositoryDriver(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public RepositoryDriver addRepository(Repository repository) {
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/", repository, Void.class);
        responseStatus = responseEntity.getStatusCode();
        return this;
    }

    public RepositoryDriver deleteRepository(String name) throws URISyntaxException {
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/?name={name}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, name);
        responseStatus = responseEntity.getStatusCode();
        return this;
    }

    public RepositoryDriver callGetRepository(String name) {
        ResponseEntity<Repository> responseEntity = testRestTemplate.getForEntity("/?name={name}", Repository.class, name);
        getResult = null;
        responseStatus = responseEntity.getStatusCode();
        if (HttpStatus.OK.equals(responseStatus)) {
            getResult = responseEntity.getBody();
        }
        return this;
    }

    public RepositoryDriver thenGetRepositoryReturns(String name, String creator, long accessCount) {
        assertThat(getResult.getName()).isEqualTo(name);
        assertThat(getResult.getCreator()).isEqualTo(creator);
        assertThat(getResult.getAccessCounter()).isEqualTo(accessCount);
        return this;
    }

    public RepositoryDriver callGetRepositoryByAccessCount(Long accessCount) throws IOException {
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/by-count?count={accessCount}", String.class, accessCount);
        getByAccessCountResult = null;
        responseStatus = responseEntity.getStatusCode();
        if (HttpStatus.OK.equals(responseStatus)) {
            getByAccessCountResult = Arrays.asList(new ObjectMapper().readValue(responseEntity.getBody(), Repository[].class));
        }
        return this;
    }

    public RepositoryDriver thenGetRepositoryByAccessCountReturns(List<String> expectedRepositoryNames) {
        assertThat(getByAccessCountResult)
                .extracting(Repository::getName)
                .containsOnly((String[]) expectedRepositoryNames.toArray());
        return this;
    }

    public RepositoryDriver thenReturnsNotFound() {
        assertThat(responseStatus).isEqualTo(HttpStatus.NOT_FOUND);
        return this;
    }

}
