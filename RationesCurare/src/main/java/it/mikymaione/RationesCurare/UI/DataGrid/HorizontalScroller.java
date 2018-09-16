/*
MIT License
Copyright (c) 2018 Michele Maione
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package it.mikymaione.RationesCurare.UI.DataGrid;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

class HorizontalScroller extends HorizontalScrollView
{

    private DataGrid.MemberCollection mc;

    public HorizontalScroller(Context context, DataGrid.MemberCollection mc)
    {
        super(context);
        this.setSmoothScrollingEnabled(true);
        this.setHorizontalFadingEdgeEnabled(false);
        this.mc = mc;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {

        try
        {
            switch (event.getAction())
            {

                case MotionEvent.ACTION_DOWN:
                {
                    mc.DOWNSTART = MotionEvent.obtain(event);
                    break;
                }

                case MotionEvent.ACTION_MOVE:
                {
                    if (!mc.IS_SPLITER_CLICKED)
                    {
                        float deltaX = event.getX() - mc.DOWNSTART.getX();
                        float deltaY = event.getY() - mc.DOWNSTART.getY();
                        if (Math.abs(deltaX) > Math.abs(deltaY))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {

        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {

        if (e.getAction() != 2)
        {
            return false;
        }

        int WindowWidth = mc.DATAGRID_WINDOW.getWidth();
        int ScrollWidth = -((int) ((e.getX() - mc.DOWNSTART.getX()) / 4));
        int balance;

        if (ScrollWidth != 0 && mc.DATAGRID_VIEW.getWidth() > WindowWidth)
        {
            //Scrolls to the right
            if (ScrollWidth > 0)
            {
                //Scroll to the end of right edge
                if (ScrollWidth + WindowWidth > (mc.DATAGRID_VIEW.getWidth() - mc.DATAGRID_VIEW.getScrollX()))
                {
                    balance = ScrollWidth + WindowWidth - (mc.DATAGRID_VIEW.getWidth() - mc.DATAGRID_VIEW.getScrollX());
                    mc.DATAGRID_VIEW.scrollBy(ScrollWidth - balance, 0);

                }
                else
                {
                    mc.DATAGRID_VIEW.scrollBy(ScrollWidth, 0);
                }
            }
            //Scroll to the left
            else
            {
                if (mc.DATAGRID_VIEW.getScrollX() != 0)
                {
                    //Scroll to the end of left edge
                    if (Math.abs(ScrollWidth) > mc.DATAGRID_VIEW.getScrollX())
                    {
                        mc.DATAGRID_VIEW.scrollBy(-mc.DATAGRID_VIEW.getScrollX(), 0);
                    }
                    else
                    {
                        mc.DATAGRID_VIEW.scrollBy(ScrollWidth, 0);
                    }
                }
            }
        }

        return true;
    }


}