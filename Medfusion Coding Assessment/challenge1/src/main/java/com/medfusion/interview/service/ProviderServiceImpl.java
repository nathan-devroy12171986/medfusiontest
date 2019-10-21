package com.medfusion.interview.service;

import com.medfusion.interview.data.DocumentReference;
import com.medfusion.interview.util.Constants;

import java.util.*;

public class ProviderServiceImpl implements ProviderService {

    public ProviderServiceImpl() {
    }

    @Override
    public List<DocumentReference> search(Map<String, String> searchParams,
                                          List<DocumentReference> referenceList) {
        List<DocumentReference> refList = new Vector<>();
        if(null == searchParams || null == referenceList ||
                referenceList.stream().noneMatch(Objects::nonNull) ||
                searchParams.size() != 1) {
            return refList;
        }
        searchParams.keySet().stream().filter(this::getValue).forEach(val -> {
            for(DocumentReference doc : referenceList) {
                if(val.equals(Constants.ID) && null != doc.getId() && doc.getId().equals(searchParams.get(val))) {
                    refList.add(doc);
                }
                else if(val.equals(Constants.DOCUMENT_TYPE) && null != doc.getType() && doc.getType().equals(searchParams.get(val))) {
                    refList.add(doc);
                }
                if(val.equals(Constants.CREATED) && null != doc.getCreated() && doc.getCreated().equals(searchParams.get(val))) {
                    refList.add(doc);
                }
            }
        });
        return refList;
    }

    private Boolean getValue(String input) {
        return (input.equals(Constants.DOCUMENT_TYPE) || input.equals(Constants.ID) || input.equals(Constants.CREATED));
    }
}
