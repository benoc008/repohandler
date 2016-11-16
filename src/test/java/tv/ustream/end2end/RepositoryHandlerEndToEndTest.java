package tv.ustream.end2end;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import tv.ustream.logic.RepositoryBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void happyPathGetByAccessCount() throws IOException {
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
    public void happyPathDelete() throws URISyntaxException {
        driver
                .addRepository(
                        new RepositoryBuilder()
                                .withName("first")
                                .withCreator("creator")
                                .build())
                .deleteRepository("first")
                .callGetRepository("first")
                .thenReturnsNotFound();
    }

    @Test
    public void getByAccessCountNotFound() throws IOException {
        driver
                .addRepository(new RepositoryBuilder()
                        .withName("first")
                        .withCreator("creator")
                        .build())
                .addRepository(new RepositoryBuilder()
                        .withName("second")
                        .withCreator("creator")
                        .build())
                .callGetRepository("first")
                .callGetRepository("second")
                .callGetRepositoryByAccessCount(2L)
                .thenReturnsNotFound();
    }
}