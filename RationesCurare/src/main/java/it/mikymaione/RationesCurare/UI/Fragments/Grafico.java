/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import it.mikymaione.RationesCurare.DB.Tables.grafico_anno;
import it.mikymaione.RationesCurare.DB.Tables.grafico_mese;
import it.mikymaione.RationesCurare.DB.Wrappers.cMovimenti;
import it.mikymaione.RationesCurare.Globals.GB;
import it.mikymaione.RationesCurare.R;
import it.mikymaione.RationesCurare.UI.Adattatori.MoneyAsXAxisLabelFormatter;
import it.mikymaione.RationesCurare.UI.Templates.baseDB;

public class Grafico extends baseDB
{

    private boolean Annuale = true;
    private GraphView grafico;

    @Override
    public CharSequence GetTitolo()
    {
        String r = (Annuale ? "annuale" : "mensile");
        cMovimenti m = new cMovimenti(DB);

        return r.substring(0, 4) + "…: " + GB.DoubleToEuro(m.Saldo());
    }

    @Override
    protected int get_R_layout_fragment_name()
    {
        return R.layout.fragment_grafico;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int z = item.getItemId();

        if (z == R.id.action_Annuale)
        {
            DisegnaAnnuale();
        }
        else if (z == R.id.action_Mensile)
        {
            DisegnaMensile();
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    protected View GetFirstControlToFocus()
    {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        MyView = super.onCreateView(inflater, container, savedInstanceState);

        grafico = (GraphView) MyView.findViewById(R.id.grafico);

        DisegnaAnnuale();

        return MyView;
    }

    private void DisegnaMensile()
    {
        Annuale = false;
        grafico.removeAllSeries();
        cMovimenti m = new cMovimenti(DB);
        ArrayList<grafico_mese> gg = m.Grafico_Mensile(null);

        if (gg.size() > 0)
        {
            int i = -1;
            Date minAnno = null;
            Date maxAnno = null;
            double minVal = -1;
            double maxVal = 0;
            DataPoint[] punti = new DataPoint[gg.size()];

            for (grafico_mese g : gg)
            {
                if (minAnno == null)
                {
                    minAnno = g.getDate();
                }

                if (maxAnno == null)
                {
                    maxAnno = g.getDate();
                }

                if (g.getDate().compareTo(minAnno) < 0)
                {
                    minAnno = g.getDate();
                }

                if (g.getDate().compareTo(maxAnno) > 0)
                {
                    maxAnno = g.getDate();
                }

                if (minVal == -1)
                {
                    minVal = g.importo;
                }

                if (g.importo < minVal)
                {
                    minVal = g.importo;
                }

                if (g.importo > maxVal)
                {
                    maxVal = g.importo;
                }

                punti[i += 1] = new DataPoint(g.getDate(), g.importo);
            }

            grafico.getGridLabelRenderer().setLabelFormatter(new MoneyAsXAxisLabelFormatter(getActivity()));
            grafico.addSeries(SetGrafico(AddMonth(minAnno, -2), AddMonth(maxAnno, 2), minVal, maxVal, punti));
        }
    }

    private double AddMonth(Date d, int m)
    {
        Calendar cal = Calendar.getInstance();

        cal.clear();
        cal.setTime(d);
        cal.add(Calendar.MONTH, m);

        return cal.getTime().getTime();
    }

    private BarGraphSeries<DataPoint> SetGrafico(double minX, double maxX, double minY, double maxY, DataPoint[] punti)
    {
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(punti);

        minY += (minY / 4);
        maxY += (maxY / 4);

        grafico.getViewport().setXAxisBoundsManual(true);
        grafico.getViewport().setYAxisBoundsManual(true);
        grafico.getGridLabelRenderer().setHighlightZeroLines(false);
        grafico.getViewport().setMinY(minY);
        grafico.getViewport().setMaxY(maxY);
        grafico.getViewport().setMinX(minX);
        grafico.getViewport().setMaxX(maxX);
        series.setValuesOnTopColor(GB.cHoloBlueLight);
        series.setValueDependentColor(new MoneyValueDependentColor());
        series.setSpacing(20);

        getActivity().getActionBar().setTitle(GetTitolo());

        return series;
    }

    private void DisegnaAnnuale()
    {
        Annuale = true;
        grafico.removeAllSeries();
        cMovimenti m = new cMovimenti(DB);
        ArrayList<grafico_anno> gg = m.Grafico_Annuale(null);

        if (gg.size() > 0)
        {
            int i = -1;
            int minAnno = -1;
            int maxAnno = 0;
            double minVal = -1;
            double maxVal = 0;
            DataPoint[] punti = new DataPoint[gg.size()];

            for (grafico_anno g : gg)
            {
                if (minAnno == -1)
                {
                    minAnno = g.anno;
                }

                if (g.anno < minAnno)
                {
                    minAnno = g.anno;
                }

                if (g.anno > maxAnno)
                {
                    maxAnno = g.anno;
                }

                if (minVal == -1)
                {
                    minVal = g.importo;
                }

                if (g.importo < minVal)
                {
                    minVal = g.importo;
                }

                if (g.importo > maxVal)
                {
                    maxVal = g.importo;
                }

                punti[i += 1] = new DataPoint(g.anno, g.importo);
            }

            BarGraphSeries<DataPoint> series = SetGrafico(minAnno - 1, maxAnno + 1, minVal, maxVal, punti);
            series.setDrawValuesOnTop(true);

            grafico.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
            {
                @Override
                public String formatLabel(double value, boolean isValueX)
                {
                    if (isValueX)
                    {
                        DecimalFormat myFormatter = new DecimalFormat("#");

                        return myFormatter.format(value);
                    }
                    else
                    {
                        return GB.DoubleToEuro(value);
                    }
                }
            });

            grafico.addSeries(series);
        }
    }

    public class MoneyValueDependentColor implements ValueDependentColor<DataPoint>
    {

        @Override
        public int get(DataPoint data)
        {
            return (data.getY() < 0 ? Color.RED : Color.GREEN);
        }
    }

}