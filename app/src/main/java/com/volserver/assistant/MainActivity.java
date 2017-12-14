package com.volserver.assistant;

import android.app.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.support.design.widget.*;
import android.support.v4.widget.*;
import com.alcatraz.support.v4.appcompat.*;
import java.util.*;
import com.volserver.assistant.beans.*;
import com.volserver.assistant.adapters.*;
import android.widget.*;
import java.io.*;
import android.content.*;

public class MainActivity extends AppCompatActivity 
{
	//Views
	android.support.v7.widget.Toolbar tb;
	View Nv;
	NavigationView nv;
	RecyclerView rv;
	DrawerLayout dl;
	MainRecyclerAdapter mra;
	
	//Data
	List<Bean> data=new LinkedList<Bean>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		findViews();
		initViews();
		initDefaultData();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		new MenuInflater(this).inflate(R.menu.main_menu, menu);
		// TODO: Implement this method
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.item1:
				//Setting Button
				
				break;
			case R.id.item2:
				
				break;
		}
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}
	public void findViews()
	{
		tb = (android.support.v7.widget.Toolbar) findViewById(R.id.mainToolbar1);
		dl = (DrawerLayout) findViewById(R.id.mainDrawerLayout1);
		nv = (NavigationView) findViewById(R.id.navigation);
		rv = (RecyclerView) findViewById(R.id.mainRc1);
		Nv = findViewById(R.id.mainView1);
	}
	public void initDefaultData(){
		Bean p1=new Bean();
		p1.setSource("xk.ceojy.org",19132);
		p1.setMOTD("虚空之遗主服");
		p1.setActivity(this);
		data.add(p1);
		flushState();
		Bean p3=new Bean();
		p3.setSource("xk.ceojy.org",6666);
		p3.setMOTD("虚空之遗游戏服");
		p3.setActivity(this);
		data.add(p3);
		flushState();
		Bean p2=new Bean();
		p2.setSource("s1.mcter.cn",19132);
		p2.setMOTD("TwilightLand Server");
		p2.setActivity(this);
		data.add(p2);
		flushState();
	}
	public void flushState(){
		
		mra.notifyItemInserted(1);
	}
	public void initData(){
		
	}
	public void initViews()
	{
		nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

				@Override
				public boolean onNavigationItemSelected(MenuItem p1)
				{
					if (p1.getItemId() == R.id.nav_support_1_2)
					{
						
					}
					else if (p1.getItemId() == R.id.nav_support_2_3)
					{
						
					}
					// TODO: Implement this method
					return false;
				}
			});
		mra = new MainRecyclerAdapter(this, data);
		rv.setLayoutManager(new LinearLayoutManager(this));
		rv.setItemAnimator(new DefaultItemAnimator());
		rv.setAdapter(mra);
		
		tb.setTitle(R.string.app_name);
		setSupportActionBar(tb);
		new DrawerLayoutUtil().setImmersiveToolbarWithDrawer(tb, dl, this, Nv, "#2196f3", Build.VERSION.SDK_INT);
	}
	public void showUsrChange(){
		
	}
}
