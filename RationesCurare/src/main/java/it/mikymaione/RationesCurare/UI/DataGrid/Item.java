/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.mikymaione.RationesCurare.Globals.GB;

public class Item extends LinearLayout
{

    public int IDD = -1;
    private TextView[] artTextView;
    private Context mContext;
    private DataGrid.MemberCollection mc;
    private DataTable.DataRow Valore = null;
    private int MyColor;

    public Item(Context context, DataGrid.MemberCollection mc, DataTable.DataRow data, int IDD_)
    {
        super(context);

        IDD = IDD_;
        mContext = context;
        Valore = data;
        this.mc = mc;

        int tot = mc.COLUMN_STYLE.size();

        artTextView = new TextView[data.getColumnSize()];

        setOrientation(HORIZONTAL);

        for (int i = 0; i < tot; i++)
        {
            artTextView[i] = new TextView(mContext);
            artTextView[i].setTextColor(Color.BLACK);
            artTextView[i].setPadding(DataGridStyle.DataCell.LPadding, DataGridStyle.DataCell.TPadding, DataGridStyle.DataCell.RPadding, DataGridStyle.DataCell.BPadding);
            artTextView[i].setText(data.get_for_grid(mc.COLUMN_STYLE.get(i).getFieldName()));
            artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
            artTextView[i].setGravity(DataGridStyle.DataCell.Gravity);
            artTextView[i].setSingleLine(true);

            mc.ITEM_VIEW.get(i).add(artTextView[i]);

            addView(artTextView[i], new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, DataGridStyle.DataCell.Height));
        }
    }

    public void HighlightRow(boolean selected)
    {
        setBackgroundColor(selected ? GB.cHoloBlueLight : MyColor);
    }

    private void Colora(boolean selected, boolean pressed)
    {
        boolean spair = (Valore.getID() % 2 == 0);

        Colora(spair, selected, pressed);
    }

    private void Colora(boolean spair, boolean selected, boolean pressed)
    {
        int colore;

        if (pressed)
        {
            colore = GB.cHoloBlueDark;
        }
        else
        {
            if (selected)
            {
                colore = GB.cHoloBlueLight;
            }
            else
            {
                if (spair)
                {
                    colore = GB.cWhite;
                }
                else
                {
                    colore = GB.cDarkerGray;
                }

                MyColor = colore;
            }
        }

        setBackgroundColor(colore);
    }

    protected void Colora(boolean spair)
    {
        Colora(spair, isSelected(), isPressed());
    }

    public void populate(DataTable.DataRow data)
    {
        for (int i = 0; i < mc.COLUMN_STYLE.size(); i++)
        {
            artTextView[i].setWidth(mc.COLUMN_STYLE.get(i).getWitdh());
            artTextView[i].setText(data.get_for_grid(mc.COLUMN_STYLE.get(i).getFieldName()));
        }
    }

    public DataTable.DataRow getValore()
    {
        return Valore;
    }


}