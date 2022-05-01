package org.larma.postnr;

public class PostnrError {
    final public String property;
    final public String message;
    
    public PostnrError(
        final String property,
        final String message)
    {
        this.property = property;
        this.message = message;
    }
}
