package it.mikymaione.RationesCurare.DB.Tables;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;

/**
 * Created by michele on 24/03/15.
 */
public class grafico_anno extends baseTabellaDB<Integer>
{

    public Integer anno;
    public Double importo;

    @Override
    public Integer getDBID()
    {
        return anno;
    }


}