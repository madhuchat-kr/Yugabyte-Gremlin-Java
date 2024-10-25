package com.mycompany.project;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
//import org.apache.commons.configuration2.Configuration;
import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.ResultSet;
import org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor;
import org.apache.tinkerpop.gremlin.jsr223.ConcurrentBindings;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class JanusgraphExample {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Graph graph = TinkerGraph.open();
    //Configuration c = graph.configuration();
    GraphTraversalSource g = graph.traversal();

    // Creating graph
    Vertex marko = g.addV("person").property("name", "marko").property("age", 29).next();
    Vertex lop = g.addV("software").property("name", "lop").property("lang", "java").next();
    g.addE("created").from(marko).to(lop).property("weight", 0.6d).iterate();
    g.io("test.xml").write().iterate(); // saving to file

    //standard query
    GraphTraversal<Vertex, Map<Object, Object>> javaQueryResult =
        g.V().hasLabel("person").valueMap();

    // preparing GremlinExecutor
    ConcurrentBindings b = new ConcurrentBindings();
    b.putIfAbsent("g", g);

    GremlinExecutor ge =
        GremlinExecutor.build().evaluationTimeout(15000L).globalBindings(b).create();

    CompletableFuture<Object> evalResult = ge.eval("g.V().hasLabel('person').valueMap()");
    GraphTraversal actualResult = (GraphTraversal) evalResult.get();
    System.out.println("madhu = " +  g.V().hasLabel("person").valueMap(true));
  }

  public static void mainold(String[] args) throws Exception {
    // Configure the connection to JanusGraph server
    Cluster cluster = Cluster.build("localhost")
        .port(8182)
        .create();

    Client client = cluster.connect();
    //GraphTraversalSource g = traversal().withRemote("/Users/MC49461/janusgraph-0.6.4/conf/remote-graph.properties");
    //try (Client client = cluster.connect()) {
      // Execute a Gremlin query
      ResultSet result = client.submit("g.V().has('name', 'saturn')");
    //ResultSet result = client.submit("g.V().valueMap(true)");

      // Process the
    // Process the result
    //result.one();
    System.out.println("madhu start");
    result.forEach(System.out::println);
    //Object herculesAge = g.V().has("name", "hercules").values("age").next();
    //System.out.println("Hercules is " + herculesAge + " years old.");
    System.out.println("madhu end");

    //} catch (Exception e) {
   //   e.printStackTrace();
    //} finally {
      cluster.close();
   // }
  }
}