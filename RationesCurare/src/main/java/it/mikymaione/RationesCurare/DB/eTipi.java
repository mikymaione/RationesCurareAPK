package it.mikymaione.RationesCurare.DB;

/**
 * Created by michele on 06/03/15.
 */
public enum eTipi
{
    Carica("Carica"),
    Ricerca("Ricerca"),
    Saldi("Saldi"),
    Saldo("Saldo"),
    SaldoByTipo("SaldoByTipo"),
    SaldoByParams("SaldoByParams"),
    GraficoAnnuale("GraficoAnnuale"),
    GraficoMensile("GraficoMensile"),
    MacroAree("MacroAree"),
    getMacroAreaForDescrizione("getMacroAreaForDescrizione"),
    Descrizioni("Descrizioni"),
    Scadenze("Scadenze");

    private final String text;

    private eTipi(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }


}