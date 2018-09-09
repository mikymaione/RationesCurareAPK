package it.mikymaione.RationesCurare.DB.Tables;

import java.util.Date;

/**
 * Created by michele on 25/03/15.
 */
public class grafico_mese extends grafico_anno
{
    public Integer mese;

    public Date getDate()
    {
        return new Date(anno - 1900, mese - 1, 1);
    }


}