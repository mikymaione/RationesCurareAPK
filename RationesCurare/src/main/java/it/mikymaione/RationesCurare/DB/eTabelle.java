package it.mikymaione.RationesCurare.DB;

/**
 * Created by michele on 06/03/15.
 */
public enum eTabelle
{
    Casse("Casse"), Calendario("Calendario"), movimenti("movimenti"), MovimentiTempo("MovimentiTempo"), utenti("utenti");

    private final String text;

    private eTabelle(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}