package org.larma.postnr;

import org.larma.validation.Numeric;

public class PostnrInfo {
    @Numeric
    public final String postnr;
    public final String poststed;
    @Numeric
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
