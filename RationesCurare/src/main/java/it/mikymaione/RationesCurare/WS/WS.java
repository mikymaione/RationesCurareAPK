/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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
    private final String SOAP_ADDRESS = "EmailSending.asmx";

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
        String OperationName = getOPERATION_NAME();
        HttpTransportSE httpTransport = new HttpTransportSE(WSDL_TARGET_NAMESPACE + SOAP_ADDRESS);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OperationName);
        PropertyInfo[] params = getParameters();

        if (params != null && params.length > 0)
            for (int i = 0; i < params.length; i++)
                request.addProperty(params[i]);

        envelope.implicitTypes = true;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        //httpTransport.debug = true;
        httpTransport.call(WSDL_TARGET_NAMESPACE + OperationName, envelope);

        //String z1 = httpTransport.requestDump;
        //String z2 = httpTransport.responseDump;

        Object TheResponse = envelope.getResponse();

        return TheResponse;
    }


}