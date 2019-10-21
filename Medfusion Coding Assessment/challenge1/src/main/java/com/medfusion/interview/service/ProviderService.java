package com.medfusion.interview.service;

import java.util.List;
import java.util.Map;

import com.medfusion.interview.data.DocumentReference;

public interface ProviderService {

    /**
     * <p>
     * Searches for documents based on the given search parameters. Any documents that match all of the search
     * parameters will have a document reference returned.
     * </p>
     *
     * @param searchParams
     *            The search parameters where the key is the field name to search on and the value is the field value to
     *            match.
     * @param  referenceList a list of DocumentReference objects to search from
     * @return A list of document references for matching documents. If no documents match the search parameters, then
     *         an empty list is returned.
     */
    List<DocumentReference> search(Map<String, String> searchParams,
                                   List<DocumentReference> referenceList);
}
