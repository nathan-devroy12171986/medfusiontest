package com.medfusion.interview.service

import com.medfusion.interview.data.DocumentReference
import com.medfusion.interview.util.Constants
import org.spockframework.util.Assert
import spock.lang.Specification

class ProviderServiceImplTest extends Specification {

    def providerService
    Map<String, String> searchParams


    void setup() {
        providerService = new ProviderServiceImpl()
        searchParams = new HashMap<>()
        searchParams.put(Constants.DOCUMENT_TYPE, Constants.CCD)
    }

    def searchTestHappyResponseReturnOutputDocumentType() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(searchParams, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 2
    }

    def searchTestHappyResponseReturnOutputId() {
        setup:
        def idParams = new HashMap()
        idParams.put(Constants.ID, "1")

        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(idParams, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 1
    }

    def searchTestHappyResponseReturnOutputCreated() {
        setup:
        def createdParams = new HashMap()
        createdParams.put(Constants.CREATED, "2001-01-01")

        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-01")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(createdParams, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 2
    }

    def searchTestHappyResponseReturnOutputCreatedNoHits() {
        setup:
        def createdParams = new HashMap()
        createdParams.put(Constants.CREATED, "2000-01-01")

        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-01")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(createdParams, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 0
    }


    def searchTestNullSearchParam() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(null, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 0
    }

    def searchTestNullReferenceList() {
        setup:
        when:
        def searchOutput = providerService.search(searchParams, null)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 0
    }

    def searchTestNullValuesInSearchParams() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        DocumentReference ref2 = new DocumentReference()
        DocumentReference ref3 = new DocumentReference()
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        when:
        def searchOutput = providerService.search(null, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 0
    }

    def searchTestMultipleValsInSearchParams() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Document")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        searchParams.put("another val", "some more stuff")
        when:
        def searchOutput = providerService.search(searchParams, listOfDocs)

        then:
        Assert.notNull(searchOutput)
        assert searchOutput.size() == 0
    }
}
