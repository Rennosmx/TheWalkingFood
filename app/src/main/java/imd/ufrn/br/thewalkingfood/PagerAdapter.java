package imd.ufrn.br.thewalkingfood;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabelas;

    public PagerAdapter(FragmentManager fm, int numTabelas) {
        super(fm);
        this.numTabelas = numTabelas;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentMapa tabMapa = new FragmentMapa();
                return tabMapa;
            case 1:
                FragmentVendedores tabVendedores = new FragmentVendedores();
                return tabVendedores;
            case 2:
                FragmentChat tabChat = new FragmentChat();
                return tabChat;
            case 3:
                FragmentFeed tabFeed = new FragmentFeed();
                return tabFeed;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabelas;
    }
}
