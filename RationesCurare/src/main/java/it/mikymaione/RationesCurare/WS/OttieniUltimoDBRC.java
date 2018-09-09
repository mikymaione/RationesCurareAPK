package it.mikymaione.RationesCurare.WS;

import org.apache.commons.io.FileUtils;
import org.kobjects.base64.Base64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

import java.io.File;
import java.util.Date;

import it.mikymaione.RationesCurare.Globals.GB;

/**
 * Created by michele on 01/04/15.
 */
public class OttieniUltimoDBRC extends WS<String>
{

    private File tempFile = null;
    private String yyyyMMddHHmmss, email, psw;

    public OttieniUltimoDBRC(File tempFile, Date VersioneAttuale, String email, String psw)
    {
        this.yyyyMMddHHmmss = GB.DateToString(VersioneAttuale, "yyyyMMddHHmmss");
        this.email = email;
        this.psw = psw;
        this.tempFile = tempFile;

        if (yyyyMMddHHmmss == null || yyyyMMddHHmmss.equals("") || yyyyMMddHHmmss.length() != 14)
        {
            this.yyyyMMddHHmmss = GB.DateToString(new Date(90, 1, 1, 0, 0, 0), "yyyyMMddHHmmss");
        }
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "OttieniUltimoDBRC";
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
        {
            return ((SoapPrimitive) r).getValue().toString();
        }

        return null;
    }

    public boolean SalvaDBSuDisco()
    {
        boolean r = false;

        try
        {
            String db = Call();

            if (db != null && db.length() > 0)
            {
                FileUtils.writeByteArrayToFile(tempFile, Base64.decode(db), false);
                r = true;
            }
        }
        catch (Exception e)
        {
            //errore
        }

        return r;
    }


}