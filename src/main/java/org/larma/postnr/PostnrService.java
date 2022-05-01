package org.larma.postnr;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Service
@Validated
public class PostnrService {
    private final static List<PostnrInfo> db = new ArrayList<>();

    public PostnrService()
    {
        fillDB();
    }

    public List<PostnrInfo> search(
            @Valid final PostnrInfo q)
    {
        return db.stream()
                .filter(f -> checkSearch(q.postnr, f.postnr))
                .filter(f -> checkSearch(q.poststed, f.poststed))
                .filter(f -> checkSearch(q.kommunenr, f.kommunenr))
                .filter(f -> checkSearch(q.kommune, f.kommune))
                .collect(Collectors.toList());
    }

    private boolean checkSearch(
            final String query,
            final String dbField)
    {
        return query == null || query.length() == 0 || dbField.equalsIgnoreCase(query);
    }

    private void fillDB()
    {
        db.add(new PostnrInfo("5162", "Laksevåg", "4601", "Bergen"));
        db.add(new PostnrInfo("5230", "Paradis", "4601", "Bergen"));
        db.add(new PostnrInfo("0153", "Oslo", "0301", "Oslo"));
        db.add(new PostnrInfo("7042", "Trondheim", "5001", "Trondheim"));
    }
}
