package it.mikymaione.RationesCurare.WS;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;

/**
 * Created by michele on 31/03/15.
 */
public class DeZippaDBRC extends WS<Boolean>
{
    private String PathFile;

    public DeZippaDBRC(String pathFile)
    {
        PathFile = pathFile;
    }

    @Override
    protected String getOPERATION_NAME()
    {
        return "DeZippaDBRC";
    }

    @Override
    protected PropertyInfo[] getParameters()
    {
        return new PropertyInfo[]{
                newPropertyInfo("PathFile", PathFile, String.class)
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
