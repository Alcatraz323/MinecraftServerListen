package com.volserver.assistant.adapters;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.app.*;
import com.volserver.assistant.*;

public class PlayerAdapter extends BaseAdapter
{
	List<String> name;
	Activity act;
	LayoutInflater lf;
	public PlayerAdapter(List<String> name,Activity act){
		this.act=act;
		this.name=name;
		lf=act.getLayoutInflater();
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return name.size();
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return name.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		if(p2==null){
			p2=lf.inflate(R.layout.player_item,null);
		}
		TextView txv=(TextView) p2.findViewById(R.id.playeritemTextView1);
		txv.setText(name.get(p1));
		// TODO: Implement this method
		return p2;
	}
	
}
