package it.mikymaione.RationesCurare.WS;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by michele on 30/03/15.
 */
public abstract class WS<T>
{
    protected final String WSDL_TARGET_NAMESPACE = "http://www.maionemiky.it/";
    private final String SOAP_ADDRESS = "http://www.maionemiky.it/EmailSending.asmx";

    protected abstract String getOPERATION_NAME();

    protected abstract PropertyInfo[] getParameters();

    protected abstract T ConvertiRisultato(Object r);

    protected PropertyInfo newPropertyInfo(String nome, Object valore, Object tipo)
    {
        PropertyInfo pi = new PropertyInfo();
        pi.setName(nome);
        pi.setValue(valore);
        pi.setType(tipo);

        return pi;
    }

    public T Call() throws IOException, XmlPullParserException
    {
        return ConvertiRisultato(CallThis());
    }

    private Object CallThis() throws XmlPullParserException, IOException
    {
        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, getOPERATION_NAME());
        PropertyInfo[] params = getParameters();

        if (params != null && params.length > 0)
            for (int i = 0; i < params.length; i++)
                request.addProperty(params[i]);

        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        httpTransport.debug = true;
        httpTransport.call(WSDL_TARGET_NAMESPACE + getOPERATION_NAME(), envelope);

        //String z1 = httpTransport.requestDump;
        //String z2 = httpTransport.responseDump;

        return envelope.getResponse();
    }


}