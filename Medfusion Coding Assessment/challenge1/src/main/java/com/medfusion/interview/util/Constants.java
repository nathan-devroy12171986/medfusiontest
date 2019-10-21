package com.medfusion.interview.util;

public interface Constants {

    static final String DOCUMENT_REFERENCE_REGEX="-?([1-9][0-9]{3}|0[0-9]{3})(-(0[1-9]|1[0-2])(-(0[1-9]|[12][0-9]|3[01])(T(([01][0-9]|2[0-3]):[0-5][0-9]:[0-5\n" +
            "\t * ][0-9](\\.[0-9]+)?|(24:00:00(\\.0+)?))(Z|(\\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00))?)?)?)?";
    static final String DOCUMENT_TYPE = "type";
    static final String DOCUMENT_PERIOD_BEFORE = "periodBefore";
    static final String DOCUMENT_PERIOD_AFTER = "periodAfter";
    static final String CCD = "CCD";
    static final String ID = "id";
    static final String CREATED = "created";
}
