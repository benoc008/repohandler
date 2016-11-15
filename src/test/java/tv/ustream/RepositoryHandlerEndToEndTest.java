package tv.ustream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tv.ustream.domain.Repository;
import tv.ustream.logic.RepositoryBuilder;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RepositoryHandlerEndToEndTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private RepositoryDriver driver;

    @Before
    public void setUp() {
        this.driver = new RepositoryDriver(testRestTemplate);
    }

    @Test
    public void happyPathGet() {
        driver
                .addRepository(
                        new RepositoryBuilder()
                                .withName("first")
                                .withCreator("creator")
                                .build())
                .callGetRepository("first")
                .thenGetRepositoryReturns("first", "creator", 1);
    }

    @Test
    @Ignore
    public void happyPathGetByAccessCount() {
        driver
                .addRepository(
                        new RepositoryBuilder()
                                .withName("first")
                                .withCreator("creator")
                                .build())
                .addRepository(
                        new RepositoryBuilder()
                                .withName("second")
                                .withCreator("creator")
                                .build())
                .addRepository(
                        new RepositoryBuilder()
                                .withName("third")
                                .withCreator("creator")
                                .build())
                .callGetRepository("first")
                .callGetRepository("first")
                .callGetRepository("second")
                .callGetRepositoryByAccessCount(1L)
                .thenGetRepositoryByAccessCountReturns(Arrays.asList("first", "second"));
    }

    @Test
    @Ignore
    public void happyPathDelete() {
        driver
                .addRepository(
                        new RepositoryBuilder()
                                .withName("first")
                                .withCreator("creator")
                                .build())
                .deleteRepository("first")
                .callGetRepository("first")
                .thenGetRepositoryReturnsNotFound();
    }
}