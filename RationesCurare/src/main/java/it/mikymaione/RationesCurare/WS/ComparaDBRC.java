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