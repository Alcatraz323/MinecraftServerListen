package com.volserver.assistant.adapters;
import android.content.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import com.volserver.assistant.beans.*;
import com.volserver.assistant.*;
import android.app.*;
import android.view.View.*;
import com.volserver.assistant.query.*;
import com.alcatraz.support.v4.appcompat.ViewPagerMin;
import com.alcatraz.support.v4.appcompat.ViewPagerAdapter;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainViewHolder>
{
	List<Bean> data;
	List<View> item=new LinkedList<View>();
	Activity ctx;
	LayoutInflater lf;
	public MainRecyclerAdapter(Activity c,List<Bean> data){
		ctx=c;
		this.data=data;
		lf=(LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public MainViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		View v=lf.inflate(R.layout.main_rec_item,p1,false);
		MainViewHolder mvh=new MainViewHolder(v);
		// TODO: Implement this method
		return mvh;
	}
	public int Dp2Px(Context context, float dp) { 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return (int) (dp * scale + 0.5f); 
	} 
	@Override
	public void onBindViewHolder(MainViewHolder p1, int p2)
	{
		View root=p1.getView();
		final Bean cur=data.get(p2);
		cur.bind(root);
		cur.startNetworkTracking(new Bean.resg(){

				@Override
				public void onFailure(final String exc)
				{
					ctx.runOnUiThread(new Runnable(){

							@Override
							public void run()
							{
								//Toast.makeText(ctx,exc,Toast.LENGTH_LONG).show();
								cur.killConnLayer();
								cur.update();
								
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
				

				@Override
				public void onSuccess()
				{
					ctx.runOnUiThread(new Runnable(){

							@Override
							public void run()
							{
								cur.killConnLayer();
								cur.update();
								cur.cv.setOnClickListener(new OnClickListener(){

										@Override
										public void onClick(View p1)
										{
											showCli_Al(cur.getResQueryRes(),cur.getMOTD());
											// TODO: Implement this method
										}
									});
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			});
		// TODO: Implement this method
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return data.size();
	}
	public void showCli_Al(QueryResponse qr,String t){
		View root=lf.inflate(R.layout.ad_p,null);
		ViewPagerMin vpm=(ViewPagerMin) root.findViewById(R.id.mainViewPagerMin1);
		TabLayout tl=(TabLayout) root.findViewById(R.id.mainTabLayout1);
		ArrayList<View> vp_v=new ArrayList<View>();
		vp_v.add(getPlayerView(qr.getPlayerList(),qr.getOnlinePlayers()));
		vp_v.add(getPlugView(qr.getPlugins()));
		ArrayList<String> title=new ArrayList<String>();
		title.add("玩家("+compressList(qr.getPlayerList(),qr.getOnlinePlayers()).size()+")");
		title.add("插件");
		ViewPagerAdapter vpa=new ViewPagerAdapter(vp_v,title);
		vpm.setAdapter(vpa);
		tl.setupWithViewPager(vpm.getViewPager());
		new android.support.v7.app.AlertDialog.Builder(ctx)
		.setMessage(t)
		.setView(root)
		.show();
	}
	public View getPlayerView(List<String> dnam,int onl){
		View pl=lf.inflate(R.layout.player_l,null);
		ListView lv=(ListView) pl.findViewById(R.id.playerlListView1);
		PlayerAdapter pa=new PlayerAdapter(compressList(dnam,onl),ctx);
		lv.setAdapter(pa);
		return pl;
	}
	public List<String> compressList(List<String> nmal,int onl){
		List<String> n=new LinkedList<String>();
		if(onl>0){
			for(int i=2;i<nmal.size();i++){
				n.add(nmal.get(i));
			}
		}
		return n;
	}
	public View getPlugView(String str){
		View plug=lf.inflate(R.layout.plug_vp,null);
		TextView txv=(TextView) plug.findViewById(R.id.plugvpTextView1);
		String[] plgs=str.split(":")[1].split(";");
		for(String i:plgs){
			txv.append(i+"\n");
		}
		return plug;
	}
}

class MainViewHolder extends RecyclerView.ViewHolder{
	View hold;
	public MainViewHolder(View v){
		super(v);
		hold=v;
	}
	public View getView(){
		return hold;
	}
}
