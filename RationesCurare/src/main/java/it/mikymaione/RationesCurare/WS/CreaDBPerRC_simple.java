/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.WS;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

/**
 * Created by michele on 31/03/15.
 */
public class CreaDBPerRC_simple extends WS<Boolean>
{
    private String utente, psw;

    public CreaDBPerRC_simple(String utente, String psw)
    {
        this.utente = utente;
        this.psw = psw;
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "CreaDBPerRC_simple";
    }

    @Override
    protected PropertyInfo[] getParameters()
    {
        return new PropertyInfo[]{
                newPropertyInfo("Utente", utente, String.class),
                newPropertyInfo("Psw", psw, String.class)
        };
    }

    @Override
    protected Boolean ConvertiRisultato(Object r)
    {
        if (r != null && r instanceof SoapPrimitive)
        {
            Object y = ((SoapPrimitive) r).getValue();

            return Boolean.parseBoolean(y.toString());
        }
        else
        {
            return false;
        }
    }


}