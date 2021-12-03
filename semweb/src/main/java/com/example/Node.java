package com.example;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

public class Node {
    private Resource subject;
    private Property predicate;
    private RDFNode object;

    public Node() {
    }

    public Node(Resource subject, Property predicate, RDFNode object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public Resource getSubject() {
        return subject;
    }

    public void setSubject(Resource subject) {
        this.subject = subject;
    }

    public Property getPredicate() {
        return predicate;
    }

    public void setPredicate(Property predicate) {
        this.predicate = predicate;
    }

    public RDFNode getObject() {
        return object;
    }

    public void setObject(RDFNode object) {
        this.object = object;
    }
}
