package org.larma.postnr;

public class PostnrInfo {
    public final String postnr;
    public final String poststed;
    public final String kommunenr;
    public final String kommune;

    public PostnrInfo(
            final String postnr,
            final String poststed,
            final String kommunenr,
            final String kommune)
    {
        this.postnr = postnr;
        this.poststed = poststed;
        this.kommunenr = kommunenr;
        this.kommune = kommune;
    }
}
