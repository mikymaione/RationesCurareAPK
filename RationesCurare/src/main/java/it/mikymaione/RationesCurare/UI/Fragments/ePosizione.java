package it.mikymaione.RationesCurare.UI.Fragments;

/**
 * Created by michele on 18/03/15.
 */
public enum ePosizione
{

    None(-1),
    Casse(0),
    Calendario(1),
    Periodici(2),
    Grafico(3),
    GestioneCasse(4),
    Utente_Dettaglio(5),
    DataBaseSync(6),
    Movimenti(7),
    Movimenti_Dettaglio(8),
    Cerca(9),
    GestioneCasse_Dettaglio(10),
    RisultatoRicerca(11),
    Periodici_Dettaglio(12),
    Calendario_Dettaglio(13),
    Utente_Login(14);

    private final int id;

    ePosizione(final int i_)
    {
        id = i_;
    }

    public int GetID()
    {
        return id;
    }

    public boolean IsEmpty()
    {
        return this.equals(None);
    }

    public boolean Compare(int i)
    {
        return id == i;
    }

    public static ePosizione GetValue(int _id)
    {
        ePosizione[] As = ePosizione.values();

        for (int i = 0; i < As.length; i++)
        {
            if (As[i].Compare(_id))
            {
                return As[i];
            }
        }

        return None;
    }

}