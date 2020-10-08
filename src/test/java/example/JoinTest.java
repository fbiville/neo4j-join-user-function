package example;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.junit.extension.Neo4jExtension;

import java.util.logging.LogManager;

import static org.junit.Assert.assertThat;


public class JoinTest {

    // mute logging
    static {
        LogManager.getLogManager().reset();
    }

    // alternatively, use org.neo4j.harness.Neo4jBuilders to manually instantiate a Neo4j instance
    // and have tighter control of the Neo4j instance lifecycle
    @RegisterExtension
    static Neo4jExtension extension = Neo4jExtension.builder()
            .withFunction(Join.class)
            .build();

    Config unencrypted = Config.builder().withoutEncryption().build();

    @Test
    void joins_strings(Neo4j neo4j) {
        try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), unencrypted)) {
            Session session = driver.session();
            Result result = session.run("RETURN example.join(['A','quick','brown','fox'],' ') as sentence");
            assertThat(result.single().get("sentence").asString(), CoreMatchers.equalTo("A quick brown fox"));
        }
    }
}
