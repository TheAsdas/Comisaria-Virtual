package cl.carabineros.comisariaVirtual.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabMethods(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragmentList = ArrayList<Fragment>();
    private val titleList = ArrayList<String>();

    override fun getPageTitle(position: Int): CharSequence?
    {
        return titleList[position];
    }

    override fun getCount(): Int
    {
        return fragmentList.size;
    }

    override fun getItem(position: Int): Fragment
    {
        return fragmentList[position];
    }

    fun addTab(tab: Pair<String, Fragment>)
    {
        titleList.add(tab.first);
        fragmentList.add(tab.second);
    }

    fun removeTab(tab: Pair<String, Fragment>)
    {
        titleList.remove(tab.first);
        fragmentList.remove(tab.second);
    }
}