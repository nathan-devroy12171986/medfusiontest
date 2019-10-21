package com.medfusion.interview.client

import com.medfusion.interview.data.DocumentReference
import com.medfusion.interview.service.ProviderServiceImpl
import spock.lang.Specification

class ClientServiceTest extends Specification {

    ClientService service

    void setup() {}

    /**
     * Basic setup tests
     */
    def getSetTest() {
        setup:
        def providerService = new ProviderServiceImpl()

        when:
        service = new ClientService()
        service.setProviderService(providerService)

        then:
        assert null != service.getProviderService()
        notThrown(Exception)
    }

    /**
     * Simple happy path case with YYYY-MM-DD Dates
     */
    def getCurrentDocumentIdTest() {
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
        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("3")
    }

    /**
     * Simple exception case with no found types
     */
    def getCurrentDocumentIdTestWithNoResponse() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("No Match")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("No Match")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("No Match")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        listOfDocs.add(ref3)

        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId == null
    }

    /**
     * Happy path case in which date types differ
     */
    def getCurrentDocumentIdSameDateDifferentDateTypesHappyTest() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2005-05-03")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("2005-05")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-05-03T05:23:12")
        DocumentReference ref4 = new DocumentReference()
        ref4.setId("4")
        ref4.setType("CCD")
        ref4.setCreated("2005-05-03T05:23:12.12343Z")

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref4)
        listOfDocs.add(ref2)
        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("4")
    }

    /**
     * A variation on happy path case in which date types differ
     */
    def getCurrentDocumentIdSameDateDifferentDateTypesHappyTest2() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2005-05-03")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("2005-05")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2005")

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("1")
    }

    /**
     * A variation on happy path case in which date types differ
     */
    def getCurrentDocumentIdSameDateDifferentDateTypesHappyTest3() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2015-02-07T13:28:17-05:00")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("2015-02-07T13:28:17-11:00")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2015-02-07T13:28:17-06:00")

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("2")
    }

    /**
     * Happy path case --> all dates are identical
     * expect the ID for last returned record will be returned
     */
    def getCurrentDocumentIdSameDateDifferentDateTypesHappyTest4() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2015-02-07T13:28:17")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("2015-02-07T13:28:17")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2015-02-07T13:28:17")

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref2)
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("1")
    }

    /**
     * Validate on reference types containing
     * invalid date types
     */
    def getCurrentDocumentIdInvalidDateFormats() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2015/02/07")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("02-07-2015")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-02-12")
        DocumentReference ref4 = new DocumentReference()
        ref4.setId("4")
        ref4.setType("CCD")
        ref4.setCreated("2001-02-34")

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref3)
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        listOfDocs.add(ref4)

        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId.equals("3")
    }


    /**
     * Null check - pass list of null objects
     */
    def getCurrentDocumentIdNullReference() {
        setup:
        DocumentReference ref1 = null

        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref1)

        service = new ClientService(listOfDocs)

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId == null
    }

    /**
     * Null check - pass null
     */
    def getCurrentDocumentIdNullReference2() {
        setup:
        service = new ClientService()

        when:
        def mostCurrentDocumentId = service.getCurrentCcdDocumentId()

        then:
        assert mostCurrentDocumentId == null
    }

    /*
     * GetCCDDocument count --> happy path scenario
     */
    def getCcdDocumentCountTestHappyScenario() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("CCD")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        listOfDocs.add(ref3)

        service = new ClientService(listOfDocs)
        when:
        def count = service.getCcdDocumentCount()

        then:
        assert count == 3
        notThrown(Exception)
    }

    /*
     * GetCCDDocument count --> Non CCD reference types
     */
    def getCcdDocumentCountTestUncountedElements() {
        setup:
        DocumentReference ref1 = new DocumentReference()
        ref1.setId("1")
        ref1.setType("CCD")
        ref1.setCreated("2001-01-01")
        DocumentReference ref2 = new DocumentReference()
        ref2.setId("2")
        ref2.setType("Don't count this one")
        ref2.setCreated("2001-01-02")
        DocumentReference ref3 = new DocumentReference()
        ref3.setId("3")
        ref3.setType("CCD")
        ref3.setCreated("2001-01-03")
        List<DocumentReference> listOfDocs = new ArrayList<>()
        listOfDocs.add(ref1)
        listOfDocs.add(ref2)
        listOfDocs.add(ref3)

        service = new ClientService(listOfDocs)
        when:
        def count = service.getCcdDocumentCount()

        then:
        assert count == 2
        notThrown(Exception)
    }
}
