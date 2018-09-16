/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
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

}