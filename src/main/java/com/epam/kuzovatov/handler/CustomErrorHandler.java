package com.epam.kuzovatov.handler;

import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

public class CustomErrorHandler extends DefaultHandler {
    private static Logger log = Logger.getLogger(CustomErrorHandler.class);
    public void warning(SAXParseException e) {
        log.warn(getLineAddress(e) + "-" + e.getMessage());
    }
    public void error(SAXParseException e) {
        log.error(getLineAddress(e) + " - " + e.getMessage());
    }
    public void fatalError(SAXParseException e) {
        log.fatal(getLineAddress(e) + " - " + e.getMessage());
    }
    private String getLineAddress(SAXParseException e) {
        return e.getLineNumber() + " : " + e.getColumnNumber();
    }
}
