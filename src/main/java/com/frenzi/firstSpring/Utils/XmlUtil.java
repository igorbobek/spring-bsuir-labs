package com.frenzi.firstSpring.Utils;


import com.thoughtworks.xstream.XStream;

public class XmlUtil {

    public static String xmlString(Object obj) {
        XStream xStream = new XStream();
        return xStream.toXML(obj);
    }

}
