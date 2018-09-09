package it.mikymaione.RationesCurare.WS;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

/**
 * Created by michele on 31/03/15.
 */
public class ControllaCredenzialiRC_simple extends WS<String>
{
    private String utente, psw;

    public ControllaCredenzialiRC_simple(String utente, String psw)
    {
        this.utente = utente;
        this.psw = psw;
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "ControllaCredenzialiRC_simple";
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
    protected String ConvertiRisultato(Object r)
    {
        if (r != null && r instanceof SoapPrimitive)
        {
            return ((SoapPrimitive) r).getValue().toString();
        }

        return "";
    }


}