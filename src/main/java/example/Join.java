package example;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.UserFunction;

import java.util.List;

public class Join {

    @UserFunction
    public String join(@Name("words") List<String> words, @Name("separator") String separator) {
        return String.join(separator, words);
    }
}
