package it.mikymaione.RationesCurare.UI.Adattatori;

import android.content.Context;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import java.text.DateFormat;

import it.mikymaione.RationesCurare.Globals.GB;

/**
 * Created by michele on 25/03/15.
 */
public class MoneyAsXAxisLabelFormatter extends DateAsXAxisLabelFormatter
{
    public MoneyAsXAxisLabelFormatter(Context context)
    {
        super(context);
    }

    public MoneyAsXAxisLabelFormatter(Context context, DateFormat dateFormat)
    {
        super(context, dateFormat);
    }

    @Override
    public String formatLabel(double value, boolean isValueX)
    {
        if (isValueX)
        {
            return super.formatLabel(value, isValueX);
        }
        else
        {
            return GB.DoubleToEuro(value);
        }
    }


}