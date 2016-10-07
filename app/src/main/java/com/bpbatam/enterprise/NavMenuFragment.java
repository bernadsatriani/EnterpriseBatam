package com.bpbatam.enterprise;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bpbatam.AppConstant;
import com.bpbatam.AppController;
import com.bpbatam.enterprise.adapter.ExpandableListAdapter;
import com.bpbatam.enterprise.model.AuthUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 14/06/2016.
 */
public class NavMenuFragment extends Fragment {
    private static String TAG = NavMenuFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private static int[] icon = null;
    private static int[] iconSelected = null;
    private FragmentDrawerListener drawerListener;
    TextView tvVersion, txtDate;
    TextView tvName, tvEmail;
    int iPosition;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public NavMenuFragment() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setIcon(icon[i]);
            navItem.setIconSelected(iconSelected[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppConstant.menuList = new String[]{
                String.valueOf(getResources().getText(R.string.main_home)),
                String.valueOf(getResources().getText(R.string.main_mytransaction)),
                String.valueOf(getResources().getText(R.string.main_Information))
        };

        AppConstant.menuIcon = new int[]{
                R.drawable.gns_home_grey,
                R.drawable.gns_transaction_grey,
                R.drawable.gns_info_grey};

        AppConstant.menuIconSelected = new int[]{
                R.drawable.gns_home_white,
                R.drawable.gns_transaction_white,
                R.drawable.gns_info_white};

        // drawer labels
        titles = AppConstant.menuList;
        icon = AppConstant.menuIcon;
        iconSelected =  AppConstant.menuIconSelected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_menu, container, false);
        tvVersion = (TextView) layout.findViewById(R.id.tVVersion);
        txtDate = (TextView) layout.findViewById(R.id.txtDate);
        tvName = (TextView) layout.findViewById(R.id.name);
        tvEmail = (TextView) layout.findViewById(R.id.email);

        AuthUser authUser = AppController.getInstance().getSessionManager().getUserProfile();

        for (AuthUser.Datum dat : authUser.data){
            tvName.setText(dat.user_name);
            tvEmail.setText(dat.nik);
        }




        //txtDate.setText(getResources().getString(R.string.text_date).replace("[date]", AppConstant.strTgltrans) );
        //tvVersion.setText(getResources().getText(R.string.main_version) +  " " + GblFunction.getSoftwareVersion(getActivity()));

       /* recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        adapter.setCurrentPosition(0);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                iPosition = position;
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/

        expListView = (ExpandableListView) layout.findViewById(R.id.lvExp);
        //expListView.setGroupIndicator(null);
        expListView.setChildIndicator(null);
        expListView.setChildDivider(null);
        expListView.setDivider(null);
        expListView.setDividerHeight(0);

        DisplayMetrics metrics = new DisplayMetrics();
        int width = metrics.widthPixels;
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expListView.setIndicatorBounds(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        } else {
            expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));
        }

        LisdataExpandable();

        return layout;
    }

    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    void LisdataExpandable(){
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<>();
        listDataHeader.add("BERANDA");
        List<String>  lstHome = new ArrayList<>();
        listDataChild.put(listDataHeader.get(0), lstHome);


        listDataHeader.add("BBS");
        List<String>  lstBBS = new ArrayList<>();
        listDataChild.put(listDataHeader.get(1), lstBBS);

        listDataHeader.add("PERSURATAN");
        List<String>  lstPersuratan = new ArrayList<>();
        lstPersuratan.add("Pribadi");
        lstPersuratan.add("Umum");
        lstPersuratan.add("Permohonan");
        lstPersuratan.add("Dalam Proses");
        lstPersuratan.add("Simpan");
        lstPersuratan.add("Dikembalikan");
        listDataChild.put(listDataHeader.get(2), lstPersuratan);

        listDataHeader.add("DIPOSISI");
        List<String>  lstDisposisi = new ArrayList<>();
        lstDisposisi.add("Pribadi");
        lstDisposisi.add("Umum");
        lstDisposisi.add("Permohonan");
        lstDisposisi.add("Dalam Proses");
        lstDisposisi.add("Riwayat");
        listDataChild.put(listDataHeader.get(3), lstDisposisi);

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int iGroup, iChild;
                iGroup = groupPosition;
                iChild = childPosition;
                if (iGroup == 2 || iGroup == 3){
                    mDrawerLayout.closeDrawer(containerView);

                    String sPosition = Integer.toString(iGroup) + Integer.toString(iChild)  ;
                    iPosition =  Integer.parseInt(sPosition);
                }

                return false;
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int iGroup, iChild;
                iGroup = groupPosition;
                if (iGroup == 0 || iGroup == 1){
                    String sPosition = Integer.toString(iGroup) + "0"  ;
                    iPosition =  Integer.parseInt(sPosition);
                    mDrawerLayout.closeDrawer(containerView);
                }

                iGroup = iGroup;


                return false;
            }
        });
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

                //adapter.setCurrentPosition(iPosition);
                drawerListener.onDrawerItemSelected(drawerView, iPosition);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    //Adapter
    public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

        List<NavDrawerItem> data = Collections.emptyList();
        private LayoutInflater inflater;
        private Context context;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();
        private int pos = -1;

        public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.data = data;
        }

        public void delete(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }

        private void setCurrentPosition(int position) {
            selectedItems.put(position, true);
            pos = position;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_row, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            NavDrawerItem current = data.get(position);
            holder.textView.setText(current.getTitle());
            holder.imageView.setImageResource(current.getIcon());// Settimg the image with array of our icons

            Drawable icon = null;

            boolean isSelected = selectedItems.get(position, false);

            if (isSelected){
                icon = getResources().getDrawable(current.getIconSelected());
            }else{
                icon = getResources().getDrawable(current.getIcon());
            }
            holder.container.setSelected(isSelected);
            holder.textView.setSelected(isSelected);
            holder.imageView.setImageDrawable(icon);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView textView;
            ImageView imageView;
            ImageView profile;
            TextView Name;
            TextView email;
            LinearLayout container;

            public ViewHolder(View itemView) {
                super(itemView);
                container = (LinearLayout) itemView.findViewById(R.id.RelativeLayout1);
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                profile = (ImageView) itemView.findViewById(R.id.circleView);

                container.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (pos > -1) {
                    selectedItems.delete(pos);
                }
                if (!selectedItems.get(getAdapterPosition(), false)) {
                    setCurrentPosition(getAdapterPosition());
                }
            }
        }

    }
}
