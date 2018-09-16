/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

class DataGridAdapter extends BaseAdapter
{
    private Context mContext;
    private DataGrid.MemberCollection mc;

    public DataGridAdapter(Context context, DataGrid.MemberCollection mc)
    {
        mContext = context;
        this.mc = mc;
    }

    public int getCount()
    {
        return mc.DATA_SOURCE.getRowSize();
    }

    public Object getItem(int position)
    {
        return mc.DATA_SOURCE.getRow(position);
    }

    public long getItemId(int position)
    {
        //return position;
        DataTable.DataRow data = mc.DATA_SOURCE.getRow(position);

        return data.getID();
    }

    /**
     * Make a SpeechView to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Item ri;
        DataTable.DataRow data = mc.DATA_SOURCE.getRow(position);
        int z = data.getID();

        if (convertView == null)
        {
            ri = new Item(mContext, mc, data, z);
        }
        else
        {
            ri = (Item) convertView;
            ri.populate(data);
        }

        boolean spair = (z % 2 == 0);
        ri.Colora(spair);

        return ri;
    }


}