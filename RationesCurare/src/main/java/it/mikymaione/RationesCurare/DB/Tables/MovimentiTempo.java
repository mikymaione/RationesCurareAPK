package it.mikymaione.RationesCurare.DB.Tables;

import org.joda.time.DateTime;

/**
 * Created by michele on 07/03/15.
 */

public class MovimentiTempo extends movimenti implements Cloneable
{
    public Integer NumeroGiorni;
    public String TipoGiorniMese;
    public DateTime GiornoDelMese, PartendoDalGiorno, Scadenza;

    public char getTipoGiorniMese()
    {
        if (TipoGiorniMese != null && TipoGiorniMese.length() > 0)
        {
            return TipoGiorniMese.charAt(0);
        }
        else
        {
            return ' ';
        }
    }

    public Integer GiornoDelMese_SoloGiorno()
    {
        if (GiornoDelMese != null)
        {
            return GiornoDelMese.getDayOfMonth();
        }

        return null;
    }

    public String Periodo()
    {
        if (TipoGiorniMese != null && TipoGiorniMese.equals("G"))
        {
            return "Giorni";
        }
        else
        {
            return "Mese";
        }
    }

    @Override
    public Integer getDBID()
    {
        return ID;
    }

    public MovimentiTempo clone()
    {
        try
        {
            return (MovimentiTempo) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            return null;
        }
    }


}