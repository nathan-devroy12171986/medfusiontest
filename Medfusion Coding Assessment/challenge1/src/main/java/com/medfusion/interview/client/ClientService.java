package com.medfusion.interview.client;

import java.util.*;
import java.util.stream.Collectors;

import com.medfusion.interview.data.DocumentReference;
import com.medfusion.interview.service.ProviderServiceImpl;
import com.medfusion.interview.util.Constants;

public class ClientService {

    private List<DocumentReference> origRefList;
    private ProviderServiceImpl providerService;
    private HashMap<String, String> searchParams;

    public ClientService(List<DocumentReference> origRefList) {
        this.origRefList = origRefList;
        providerService = new ProviderServiceImpl();
        searchParams = new HashMap<>();
        searchParams.put(Constants.DOCUMENT_TYPE, Constants.CCD);
    }

    /**
     * <p>
     * Gets the Id of the most recent CCD Document.
     * </p>
     *
     * @return The id of the most recent CCD Document or null.
     */
    public String getCurrentCcdDocumentId() {
        List<DocumentReference> results = providerService.search(searchParams, origRefList);
        List<DocumentReference> sortedResults =
				results.stream()
						.filter(doc -> doc.getCreated().matches(Constants.DOCUMENT_REFERENCE_REGEX))
                        .sorted(Comparator.comparing(DocumentReference :: getCreated))
                        .collect(Collectors.toList());
        return sortedResults.size() > 0 ? sortedResults.get(sortedResults.size() - 1).getId() : null;
    }

    /**
     * <p>
     * Gets the number of CCD Documents.
     * </p>
     *
     * @return
     */
    public long getCcdDocumentCount() {
        List<DocumentReference> results = providerService.search(searchParams, origRefList);
        return results.stream().filter(docRef -> docRef.getType().equals(Constants.CCD)).count();
    }

    public ProviderServiceImpl getProviderService() {
        return this.providerService;
    }

    public void setProviderService(ProviderServiceImpl providerService) {
        this.providerService = providerService;
    }
}
