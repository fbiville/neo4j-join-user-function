package example;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.harness.junit.rule.Neo4jRule;

import static org.junit.Assert.assertThat;


/**
 * Unit test for simple App.
 */
public class JoinTest {

    @Rule
    public Neo4jRule neo4j = new Neo4jRule().withFunction(Join.class);

    @Test
    public void joinsStrings() {
        Config config = Config.builder()
                .withoutEncryption()
                .build();

        try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), config)) {
            Session session = driver.session();
            Result result = session.run("RETURN example.join(['A','quick','brown','fox'],' ') as sentence");
            assertThat(result.single().get("sentence").asString(), CoreMatchers.equalTo("A quick brown fox"));
        }
    }
}
