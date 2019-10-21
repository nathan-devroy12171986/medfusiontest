package com.medfusion.interview.data

import spock.lang.Specification

class DocumentReferenceTest extends Specification {

    private DocumentReference documentReference;

    void setup() {
        documentReference = new DocumentReference()
    }

    def docReferenceSetup() {
        when:
        documentReference.setCreated("12345")
        documentReference.setId("id")
        documentReference.setType("type")

        then:
        assert documentReference.getCreated().equals("12345")
        assert documentReference.getId().equals("id")
        assert documentReference.getType().equals("type")
    }
}
