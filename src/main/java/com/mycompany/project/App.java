package com.mycompany.project;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;


public class App {
    public static void main(String[] args) throws Exception {
        JanusgraphClient client = new JanusgraphClient("/Users/MC49461/Yugabyte-Gremlin-Java/src/main/java/com/mycompany/project/janusgraph-cql-es.properties");

        Map<String, String> properties = new HashMap<>();
        properties.put("locationId","540FC001");
        properties.put("vendor","FC");

        client.addNode("Ocado", properties);

        Map<String, String> edgeProperties = new HashMap<String, String>();
        properties.put("modalityType","Pickup");

        client.createEdge("OCADO123", "Pickup", "Stage", edgeProperties);

        client.createVertices ();
    }
}
