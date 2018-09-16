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

import java.util.Date;

import it.mikymaione.RationesCurare.Globals.GB;

/**
 * Created by michele on 01/04/15.
 */
public class ComparaDBRC extends WS<String>
{
    private String yyyyMMddHHmmss, email, psw;

    public ComparaDBRC(Date VersioneAttuale, String email, String psw)
    {
        this.yyyyMMddHHmmss = GB.DateToString(VersioneAttuale, "yyyyMMddHHmmss");
        this.email = email;
        this.psw = psw;

        if (yyyyMMddHHmmss == null || yyyyMMddHHmmss.equals("") || yyyyMMddHHmmss.length() != 14)
            this.yyyyMMddHHmmss = GB.DateToString(new Date(90, 1, 1, 0, 0, 0), "yyyyMMddHHmmss");
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "ComparaDBRC";
    }

    @Override
    protected PropertyInfo[] getParameters()
    {
        return new PropertyInfo[]{
                newPropertyInfo("yyyyMMddHHmmss", yyyyMMddHHmmss, String.class),
                newPropertyInfo("email", email, String.class),
                newPropertyInfo("psw", psw, String.class)
        };
    }

    @Override
    protected String ConvertiRisultato(Object r)
    {
        if (r != null && r instanceof SoapPrimitive)
            return ((SoapPrimitive) r).getValue().toString();

        return "";
    }


}