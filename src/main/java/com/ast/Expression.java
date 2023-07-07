package main.java.com.ast;

/* Interface for expression nodes in the AST.
 * 
 * An expression is something that produces a value, e.g. "5", add(1,2).
 */
public interface Expression extends Node {
    Node expressionNode();
}