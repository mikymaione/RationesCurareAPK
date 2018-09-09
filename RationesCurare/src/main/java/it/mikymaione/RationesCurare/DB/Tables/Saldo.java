package it.mikymaione.RationesCurare.DB.Tables;

import it.mikymaione.RationesCurare.DB.Templates.baseTabellaDB;

/**
 * Created by michele on 07/03/15.
 */

public class Saldo extends baseTabellaDB<String>
{
    public String tipo;
    public Double soldi;

    public Saldo()
    {
        this(0d, "");
    }

    public Saldo(Double soldi, String tipo)
    {
        this.soldi = soldi;
        this.tipo = tipo;
    }


    @Override
    public String getDBID()
    {
        return tipo;
    }


}