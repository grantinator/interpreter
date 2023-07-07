package main.java.com.ast;

/* Interface for statement nodes in the AST.
 * 
 * A statement is a node that does not produce a value, e.g. let x = 5, return 5.
 */
public interface Statement extends Node {
    public Node statementNode();

}