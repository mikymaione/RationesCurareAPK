package it.mikymaione.RationesCurare.Globals;

/**
 * Created by michele on 20/03/15.
 */
public class cErrore<V>
{
    public String testo;
    public V risultato;

    public cErrore(String testo, V risultato)
    {
        this.testo = testo;
        this.risultato = risultato;
    }


}