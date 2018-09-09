package it.mikymaione.RationesCurare.UI.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import it.mikymaione.RationesCurare.DB.Tables.MovimentiTempo;
import it.mikymaione.RationesCurare.DB.Wrappers.cCalendario;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimentiTempo;
import it.mikymaione.RationesCurare.DB.Wrappers.cUtenti;
import it.mikymaione.RationesCurare.DB.cDB;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.Globals.PB;
import it.mikymaione.RationesCurare.Globals.cErrore;
import it.mikymaione.RationesCurare.Globals.cOggiDomani;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Templates.baseFragment;


public class RationesCurare extends Activity implements MenuATendina.NavigationDrawerCallbacks, baseFragment.OnFragmentInteractionListener
{
    private HashMap<ePosizione, Integer> hMenu = new HashMap<ePosizione, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        RiempiHashMenu();

        //init the DB
        new cDB(getAssets(), getExternalFilesDir(null));

        setContentView(R.layout.activity_rationes_curare);

        MenuATendina mMenuATendina = (MenuATendina) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mMenuATendina.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        ControllaEventi();
        ControllaPeriodici();
    }

    private void RiempiHashMenu()
    {
        hMenu.put(ePosizione.Casse, R.menu.casse);
        hMenu.put(ePosizione.Periodici, R.menu.ricerca_con_dettaglio);
        hMenu.put(ePosizione.GestioneCasse, R.menu.ricerca_con_dettaglio_solo_nuovo);
        hMenu.put(ePosizione.Movimenti, R.menu.ricerca_con_dettaglio_movimenti);
        hMenu.put(ePosizione.RisultatoRicerca, R.menu.ricerca_con_dettaglio_soloedit);
        hMenu.put(ePosizione.Calendario, R.menu.calendario_);
        hMenu.put(ePosizione.Movimenti_Dettaglio, R.menu.dettaglio);
        hMenu.put(ePosizione.Periodici_Dettaglio, R.menu.dettaglio);
        hMenu.put(ePosizione.GestioneCasse_Dettaglio, R.menu.dettaglio);
        hMenu.put(ePosizione.Utente_Dettaglio, R.menu.dettaglio_solo_salva);
        hMenu.put(ePosizione.Calendario_Dettaglio, R.menu.dettaglio_tutti_tasti);
        hMenu.put(ePosizione.Cerca, R.menu.cerca);
        hMenu.put(ePosizione.Grafico, R.menu.grafico);
        hMenu.put(ePosizione.Utente_Login, R.menu.syncdb);
        hMenu.put(ePosizione.DataBaseSync, R.menu.syncdb);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        Azione(ePosizione.GetValue(position));
    }

    private void Azione_pt2(ePosizione p)
    {
        baseFragment f = null;

        switch (p)
        {
            case Casse:
                f = PB.newCasse();
                break;
            case Calendario:
                f = PB.newCalendario();
                break;
            case Periodici:
                f = PB.newMovimentiPeriodici();
                break;
            case Grafico:
                f = PB.newGrafico();
                break;
            case GestioneCasse:
                f = PB.newGestioneCasse();
                break;
            case Utente_Login:
                f = PB.newUtente_Login();
                break;
            case Utente_Dettaglio:
                cUtenti c = new cUtenti(cDB.DB());
                f = PB.newUtente_Dettaglio(c.getMioID());
                break;
            case DataBaseSync:
                f = PB.newDataBaseSync();
                break;
        }

        if (f != null)
            GB.NavigateTo(this, f, p);
    }

    private void Azione(ePosizione p)
    {
        if (!GB.Esiste1Utente)
        {
            cUtenti c = new cUtenti(cDB.DB());
            GB.Esiste1Utente = c.Esiste1Utente();
        }

        if (GB.Esiste1Utente)
        {
            Azione_pt2(p);
        }
        else
        {
            final android.content.Context ContestoAttuale = this;

            GB.QuestionBox(ContestoAttuale, "Nessun utente presente", "Hai già delle credenziali di Rationes Curare?", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int whichButton)
                {
                    if (whichButton == DialogInterface.BUTTON_POSITIVE)
                    {
                        Azione_pt2(ePosizione.Utente_Login);
                    }
                    else
                    {
                        GB.MsgBox(ContestoAttuale, "Nessun utente presente", "È necessario creare un utente!");
                        Azione_pt2(ePosizione.Utente_Dettaglio);
                    }
                }
            });
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        menu.clear();
        getMenuInflater().inflate(hMenu.get(GB.CurrentPosition), menu);

        boolean r = super.onPrepareOptionsMenu(menu);

        getActionBar().setTitle(GB.CurrentTitle);

        return r;
    }

    private void MostraMsgEventi(ArrayList<it.mikymaione.RationesCurare.DB.Tables.Calendario> eventi, String nomeGiorno)
    {
        if (eventi != null && eventi.size() > 0)
        {
            int i = 0;
            String u = "";

            for (it.mikymaione.RationesCurare.DB.Tables.Calendario e : eventi)
            {
                i++;
                u += i + "- " + String.format(e + "%n");
            }

            GB.MsgBox(this, "Ci sono " + eventi.size() + " eventi " + nomeGiorno, u);
        }
    }

    private void ControllaEventi()
    {
        cCalendario c = new cCalendario(cDB.DB());
        cOggiDomani<it.mikymaione.RationesCurare.DB.Tables.Calendario> p = c.getPromemoria();

        MostraMsgEventi(p.Oggi, "oggi");
        MostraMsgEventi(p.Domani, "domani");
    }

    private void InserisciPeriodici(final ArrayList<MovimentiTempo> p)
    {
        for (final MovimentiTempo pi : p)
        {
            DateTime n = DateTime.now();
            DateTime dtd = DateTime.now();

            if (pi.TipoGiorniMese.equalsIgnoreCase("M"))
            {
                dtd = new DateTime(n.getYear(), n.getMonthOfYear(), pi.GiornoDelMese.getDayOfMonth(), 0, 0).plusMonths(1);
            }
            else
            {
                if (pi.PartendoDalGiorno.getYear() < 1900)
                {
                    dtd = new DateTime(n.getYear(), n.getMonthOfYear(), pi.GiornoDelMese.getDayOfMonth(), 0, 0).plusDays(pi.NumeroGiorni);
                }
                else
                {
                    dtd = new DateTime(n.getYear(), n.getMonthOfYear(), pi.PartendoDalGiorno.getDayOfMonth(), 0, 0).plusDays(pi.NumeroGiorni);
                }
            }

            final DateTime finalDtd = dtd;
            String desc = pi.descrizione + (pi.MacroArea != null && pi.MacroArea.length() > 0 ? " (" + pi.MacroArea + ")" : "") + " di " + GB.DoubleToEuro(pi.soldi) + " in " + pi.tipo;

            GB.QuestionBox(this, "Vuoi inserire questo movimento periodico?", desc, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int whichButton)
                {
                    if (whichButton == DialogInterface.BUTTON_POSITIVE)
                    {
                        pi.data = new Date(); //inserito ora

                        cMovimenti mv = new cMovimenti(cDB.DB());
                        cErrore<Long> resu = mv.Salva(-1, pi);

                        if (resu.risultato > 0)
                        {
                            //Aggiorna il movimento periodico
                            if (pi.TipoGiorniMese.equalsIgnoreCase("M"))
                            {
                                pi.PartendoDalGiorno = new DateTime();
                                pi.GiornoDelMese = finalDtd;
                            }
                            else
                            {
                                pi.GiornoDelMese = new DateTime();
                                pi.PartendoDalGiorno = finalDtd;
                            }

                            cMovimentiTempo mt = new cMovimentiTempo(cDB.DB());
                            mt.Salva(pi.ID, pi);
                            //Aggiorna il movimento periodico
                        }
                        else
                        {
                            GB.MsgBox(getApplication(), "Errore", resu.testo);
                        }
                    }
                }
            });
        }
    }

    private void ControllaPeriodici()
    {
        cMovimentiTempo c = new cMovimentiTempo(cDB.DB());
        final ArrayList<MovimentiTempo> p = c.RicercaScadenzeEntroOggi();

        if (p != null && p.size() > 0)
        {
            GB.QuestionBox(this, "Movimenti periodici", "Ci sono " + p.size() + " movimenti periodici da inserire, vuoi visualizzarli ora?", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int whichButton)
                {
                    if (whichButton == DialogInterface.BUTTON_POSITIVE)
                    {
                        InserisciPeriodici(p);
                    }
                }
            });
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {
        //niente
    }


}