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