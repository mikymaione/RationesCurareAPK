package it.mikymaione.RationesCurare.DB.Tables;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by michele on 21/03/15.
 */
public class movimenti_ricerca implements Serializable
{

    public boolean bData = false, bSoldi = false;
    public String cassa = "", descrizione = "%", MacroArea = "%";
    public Date dataDa = new Date(), dataA = new Date();
    public Double soldiDa = 0d, soldiA = 0d;


}