/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.DataGrid;

import android.graphics.Color;

class DataGridStyle
{

    public class DataGrid
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class HeaderContainer
    {
        public static final int BackgroundColor = Color.BLACK;
        public static final int TextColor = Color.WHITE;
    }

    public class HeaderCell
    {
        public static final int Height = 60;
        public static final int Width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 3;
        public static final int BPadding = 3;
        public static final int BackgroundColor = Color.TRANSPARENT;
        public static final int Gravity = android.view.Gravity.CENTER_VERTICAL;
    }

    public class DataContainer
    {
        public static final int BackgroundColor = Color.TRANSPARENT;
    }

    public class DataCell
    {
        public static final int Height = HeaderCell.Height;
        public static final int Gravity = HeaderCell.Gravity;
        public static final int LPadding = 5;
        public static final int RPadding = 5;
        public static final int TPadding = 2;
        public static final int BPadding = 2;
        public static final int ForegroundColor = Color.BLACK;
    }


}