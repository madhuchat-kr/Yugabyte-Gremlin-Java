package com.mycompany.project;

import java.util.Map;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

public class JanusgraphClient {

  private static volatile JanusgraphClient client = null;

  public GraphTraversalSource g = null;
  public JanusGraph graph = null;

  public JanusgraphClient(String configLocation) {
    graph = JanusGraphFactory.open(configLocation);
    g = graph.traversal();

  }

  public void addNode(String title, Map<String, String> properties) {
    GraphTraversal<Vertex, Vertex> graphTraversal = g.addV(title);

    for (String key : properties.keySet()) {
      String value = properties.get(key);
      graphTraversal.property(key, value);
    }

    g.tx().commit();
  }

  public void createEdge(String subjectTitle, String relationLabel, String objectTitle,
                         Map<String, String> edgeProperties) {
    Vertex subject = g.addV(subjectTitle).next();
    Vertex object = g.addV(objectTitle).next();

    Edge edge = subject.addEdge(relationLabel, object);

    for (String key : edgeProperties.keySet()) {
      String value = edgeProperties.get(key);
      edge.property(key, value);
    }

    g.tx().commit();
  }

  public void createVertices () {
   Vertex n1 = g.addV("Store").property("name","K0140035399999").property("id","1").property("tenant","Kroger").property("vendor","Kroger").property("locationId","01400353").property("modalityType","Pickup").next();
   Vertex n2 = g.addV("Shed").property("name","K540FC00199999").property("id","1").property("tenant","Kroger").property("vendor","FC").property("locationId","540FC001").property("modalityType","Pickup").next();
   Edge e =  g.addE("StagePICKLastMileHandOff").from(n1).to(n2).property("modalityType","PickupSPLHandoff").next();

   g.tx().commit();
  }
}