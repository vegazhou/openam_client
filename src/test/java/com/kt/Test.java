package com.kt;

import com.kt.ciclient.OfflineResolver;
import com.kt.ciclient.XMLHandler;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.algorithms.SignatureAlgorithm;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.Constants;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.security.x509.X509CertImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by Vega Zhou on 2017/7/25.
 */
public class Test {

    private static final String X509 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICQDCCAakCBEeNB0swDQYJKoZIhvcNAQEEBQAwZzELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNh\n" +
            "bGlmb3JuaWExFDASBgNVBAcTC1NhbnRhIENsYXJhMQwwCgYDVQQKEwNTdW4xEDAOBgNVBAsTB09w\n" +
            "ZW5TU08xDTALBgNVBAMTBHRlc3QwHhcNMDgwMTE1MTkxOTM5WhcNMTgwMTEyMTkxOTM5WjBnMQsw\n" +
            "CQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEUMBIGA1UEBxMLU2FudGEgQ2xhcmExDDAK\n" +
            "BgNVBAoTA1N1bjEQMA4GA1UECxMHT3BlblNTTzENMAsGA1UEAxMEdGVzdDCBnzANBgkqhkiG9w0B\n" +
            "AQEFAAOBjQAwgYkCgYEArSQc/U75GB2AtKhbGS5piiLkmJzqEsp64rDxbMJ+xDrye0EN/q1U5Of+\n" +
            "RkDsaN/igkAvV1cuXEgTL6RlafFPcUX7QxDhZBhsYF9pbwtMzi4A4su9hnxIhURebGEmxKW9qJNY\n" +
            "Js0Vo5+IgjxuEWnjnnVgHTs1+mq5QYTA7E6ZyL8CAwEAATANBgkqhkiG9w0BAQQFAAOBgQB3Pw/U\n" +
            "QzPKTPTYi9upbFXlrAKMwtFf2OW4yvGWWvlcwcNSZJmTJ8ARvVYOMEVNbsT4OFcfu2/PeYoAdiDA\n" +
            "cGy/F2Zuj8XJJpuQRSE6PtQqBuDEHjjmOQJ0rV/r8mO1ZCtHRhpZ5zYRjhRC9eCbjx9VrFax0JDC\n" +
            "/FfwWigmrW0Y0Q==\n" +
            "-----END CERTIFICATE-----";

    private static final String XML = "<saml:Assertion xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\" ID=\"s2ac21f033135c28a4fe7e857e67cceea0697d7d89\"\n" +
            "                IssueInstant=\"2015-12-08T06:46:32Z\" Version=\"2.0\">\n" +
            "    <saml:Issuer>idp</saml:Issuer>\n" +
            "    <ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">\n" +
            "        <ds:SignedInfo>\n" +
            "            <ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "            <ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>\n" +
            "            <ds:Reference URI=\"#s2ac21f033135c28a4fe7e857e67cceea0697d7d89\">\n" +
            "                <ds:Transforms>\n" +
            "                    <ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/>\n" +
            "                    <ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/>\n" +
            "                </ds:Transforms>\n" +
            "                <ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>\n" +
            "                <ds:DigestValue>QZ2BhMQqzIWJO/3x30c5I9nRpiU=</ds:DigestValue>\n" +
            "            </ds:Reference>\n" +
            "        </ds:SignedInfo>\n" +
            "        <ds:SignatureValue>Xl83mIkB0iUkh/6K3Ih4c/AIrijgqyVOrkaLVy6GBIAp3N8DE9ZGKMXMjs5xyWLqMmC/Gf5drOn0\n" +
            "+McMtcPrRrJrZ9kXF5q1xJg3GKLx11K6pndyvyRsHwed8t8YFH21mVQh5sU4ccx38aFVb76bxd9o\n" +
            "IN2y/DL9cAbDow12blA=</ds:SignatureValue>\n" +
            "        <ds:KeyInfo>\n" +
            "            <ds:X509Data>\n" +
            "                <ds:X509Certificate>MIICQDCCAakCBEeNB0swDQYJKoZIhvcNAQEEBQAwZzELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNh\n" +
            "bGlmb3JuaWExFDASBgNVBAcTC1NhbnRhIENsYXJhMQwwCgYDVQQKEwNTdW4xEDAOBgNVBAsTB09w\n" +
            "ZW5TU08xDTALBgNVBAMTBHRlc3QwHhcNMDgwMTE1MTkxOTM5WhcNMTgwMTEyMTkxOTM5WjBnMQsw\n" +
            "CQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEUMBIGA1UEBxMLU2FudGEgQ2xhcmExDDAK\n" +
            "BgNVBAoTA1N1bjEQMA4GA1UECxMHT3BlblNTTzENMAsGA1UEAxMEdGVzdDCBnzANBgkqhkiG9w0B\n" +
            "AQEFAAOBjQAwgYkCgYEArSQc/U75GB2AtKhbGS5piiLkmJzqEsp64rDxbMJ+xDrye0EN/q1U5Of+\n" +
            "RkDsaN/igkAvV1cuXEgTL6RlafFPcUX7QxDhZBhsYF9pbwtMzi4A4su9hnxIhURebGEmxKW9qJNY\n" +
            "Js0Vo5+IgjxuEWnjnnVgHTs1+mq5QYTA7E6ZyL8CAwEAATANBgkqhkiG9w0BAQQFAAOBgQB3Pw/U\n" +
            "QzPKTPTYi9upbFXlrAKMwtFf2OW4yvGWWvlcwcNSZJmTJ8ARvVYOMEVNbsT4OFcfu2/PeYoAdiDA\n" +
            "cGy/F2Zuj8XJJpuQRSE6PtQqBuDEHjjmOQJ0rV/r8mO1ZCtHRhpZ5zYRjhRC9eCbjx9VrFax0JDC\n" +
            "/FfwWigmrW0Y0Q==</ds:X509Certificate>\n" +
            "            </ds:X509Data>\n" +
            "        </ds:KeyInfo>\n" +
            "    </ds:Signature>\n" +
            "    <saml:Subject>\n" +
            "        <saml:NameID Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress\" NameQualifier=\"idp\">admin23@ketianyun.com</saml:NameID>\n" +
            "        <saml:SubjectConfirmation Method=\"urn:oasis:names:tc:SAML:2.0:cm:bearer\">\n" +
            "            <saml:SubjectConfirmationData InResponseTo=\"s2ea15421e25545c530649468e73a0e8d95c041407\"\n" +
            "                                          NotOnOrAfter=\"2015-12-08T06:56:32Z\"\n" +
            "                                          Recipient=\"https://ectest.webex.com.cn/dispatcher/SAML2AuthService?siteurl=ectest\"/>\n" +
            "        </saml:SubjectConfirmation>\n" +
            "    </saml:Subject>\n" +
            "    <saml:Conditions NotBefore=\"2015-12-08T06:36:32Z\" NotOnOrAfter=\"2015-12-08T06:56:32Z\">\n" +
            "        <saml:AudienceRestriction>\n" +
            "            <saml:Audience>https://ectest.webex.com.cn</saml:Audience>\n" +
            "        </saml:AudienceRestriction>\n" +
            "    </saml:Conditions>\n" +
            "    <saml:AuthnStatement AuthnInstant=\"2015-12-08T06:46:32Z\"\n" +
            "                         SessionIndex=\"s2763372b89c6ccdc5af4ca270d8958c6b06c2d301\">\n" +
            "        <saml:AuthnContext>\n" +
            "            <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef>\n" +
            "        </saml:AuthnContext>\n" +
            "    </saml:AuthnStatement>\n" +
            "    <saml:AttributeStatement>\n" +
            "        <saml:Attribute Name=\"firstname\">\n" +
            "            <saml:AttributeValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">Admin 23</saml:AttributeValue>\n" +
            "        </saml:Attribute>\n" +
            "        <saml:Attribute Name=\"updatetimestamp\">\n" +
            "            <saml:AttributeValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">2015-12-08 05:25:31</saml:AttributeValue>\n" +
            "        </saml:Attribute>\n" +
            "        <saml:Attribute Name=\"email\">\n" +
            "            <saml:AttributeValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">admin23@ketianyun.com</saml:AttributeValue>\n" +
            "        </saml:Attribute>\n" +
            "        <saml:Attribute Name=\"lastname\">\n" +
            "            <saml:AttributeValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
            "                                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">Admin 23</saml:AttributeValue>\n" +
            "        </saml:Attribute>\n" +
            "    </saml:AttributeStatement>\n" +
            "</saml:Assertion>";


    @org.junit.Test
    public void test() throws CertificateException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");;
        SignatureAlgorithm.registerDefaultAlgorithms();
        JCEMapper.registerDefaultAlgorithms();
        Certificate cert = cf.generateCertificate(new ByteArrayInputStream(X509.getBytes()));
//        Certificate cert = new X509CertImpl(X509.getBytes());
        boolean result = verify(XML, "s2ac21f033135c28a4fe7e857e67cceea0697d7d89", (X509Certificate) cert);
        return;
    }

    public boolean verify(
            String xmlString,
            String idValue,
            X509Certificate senderCert
    ) {
        if (xmlString == null ||
                xmlString.length() == 0 ||
                idValue == null ||
                idValue.length() == 0) {

            return false;
        }
        Document doc =
                toDOMDocument(xmlString);
        if (doc == null) {
            return false;
        }
        Element nscontext =
                org.apache.xml.security.utils.XMLUtils.
                        createDSctx(doc, "ds", Constants.SignatureSpecNS);
        Element sigElement = null;
        try {
            sigElement = (Element) org.apache.xpath.XPathAPI.selectSingleNode(
                    doc,
                    "//ds:Signature[1]", nscontext);
        } catch (TransformerException te) {
            return false;
        }
        Element refElement;
        try {
            refElement = (Element) XPathAPI.selectSingleNode(
                    doc,
                    "//ds:Reference[1]", nscontext);
        } catch (TransformerException te) {
            return false;
        }
        String refUri = refElement.getAttribute("URI");
        String signedId = ((Element) sigElement.getParentNode()).getAttribute("ID");
        if (refUri == null || signedId == null || !refUri.substring(1).equals(signedId)) {
            return false;
        }

        doc.getDocumentElement().setIdAttribute("ID", true);
        XMLSignature signature = null;
        try {
            signature = new
                    XMLSignature((Element) sigElement, "");
        } catch (XMLSignatureException sige) {
            return false;
        } catch (XMLSecurityException xse) {
            return false;
        }
        signature.addResourceResolver(
                new OfflineResolver());
        KeyInfo ki = signature.getKeyInfo();
        X509Certificate certToUse = null;
        if (ki != null && ki.containsX509Data()) {
            try {
                certToUse = ki.getX509Certificate();
            } catch (KeyResolverException kre) {
                certToUse = null;
            }
            if (certToUse != null) {
                if (!certToUse.equals(senderCert)) {
                    return false;
                }
            }
        }
        if (certToUse == null) {
            certToUse = senderCert;
        }



        boolean result = false;
        try {
            result = signature.checkSignatureValue(certToUse);
        } catch (XMLSignatureException xse) {
            return false;
        }
        if (!result) {

            return false;
        }
        return true;
    }


    public static Document toDOMDocument(String xmlString) {
        if ((xmlString == null) || (xmlString.length() == 0)) {
            return null;
        }

        try {
            ByteArrayInputStream is = new ByteArrayInputStream(xmlString
                    .getBytes("UTF-8"));
            return toDOMDocument(is);
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }


    public static Document toDOMDocument(InputStream is) {
        DocumentBuilder documentBuilder = null;
        try {
            // Assign new debug object
            documentBuilder = getSafeDocumentBuilder(false);
        } catch (ParserConfigurationException pe) {
        }

        try {
            if (documentBuilder == null) {
                return null;
            }

            return documentBuilder.parse(is);
        } catch (Exception e) {
            return null;
        }
    }

    public static DocumentBuilder getSafeDocumentBuilder(boolean validating) throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(validating);
        dbf.setNamespaceAware(true);
        dbf.setXIncludeAware(false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        db.setEntityResolver(new XMLHandler());
        return db;
    }



}

