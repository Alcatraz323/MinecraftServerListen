package com.volserver.assistant.beans;
import java.util.*;
import com.volserver.assistant.query.*;
import android.view.*;
import android.widget.*;
import com.volserver.assistant.*;
import java.net.*;
import android.graphics.*;
import com.volserver.assistant.anim.*;
import android.app.*;
import android.util.*;
import android.support.v7.widget.*;

public class Bean
{
	public static String color_outage="#EF5350";
	public static String color_operational="#4CAF50";
	private QueryResponse qr1;
	private List<String> res=new LinkedList<String>();
	private String motd;
	private String o_m;
	private String source;
	private static boolean isOnline;
	private View item;
	private Activity act;
	public LinearLayout con_layer;
	public LinearLayout delete_layer;
	public TextView title;
	public TextView source_v;
	public TextView om_view;
	public TextView isVi;
	public CardView cv;
	private MCQuery mcw;
	private int port;
	static String ip="";
	public void update()
	{
		if (motd != null)
		{
			title.setText(motd);
		}
		else
		{
			title.setText(qr1.getMOTD());
		}
		source_v.setText(source+":"+port);
		getIP(source, new NetWorkCallback(){

				@Override
				public void onSuccess(String raw)
				{
					ip="";
					ip+=raw;
					act.runOnUiThread(new Runnable(){

							@Override
							public void run()
							{
								source_v.setText(source + ":" + port + "(" + ip + ")");
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			});
		
		try
		{
			om_view.setText(compressList(qr1.getPlayerList(),qr1.getOnlinePlayers()).size() + "/" + qr1.getMaxPlayers());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (isOnline)
		{
			isVi.setText("Online");
			isVi.setTextColor(Color.parseColor(color_operational));
		}
		else
		{
			isVi.setText("Offline");
			isVi.setTextColor(Color.parseColor(color_outage));
		}
		// TODO: Implement this method
	}
	public void setActivity(Activity act){
		this.act=act;
	}
	
	public void getIP(String name,NetWorkCallback nc){
		InetAddress address = null;
		try {
			address = InetAddress.getByName(name);
		} catch (UnknownHostException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nc.onSuccess(address.getHostAddress().toString());
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
	public List<String> getList()
	{
		return res;
	}
	public void clear()
	{
		res.clear();
	}
	public String getMOTD()
	{
		return motd;
	}
	public void setMOTD(String motd)
	{
		this.motd = motd;
	}
	public void add(String a)
	{
		res.add(a);
	}
	public void setO_M(String o_m)
	{
		this.o_m = o_m;
	}
	public void setSource(String source, int port)
	{
		this.source = source;
		this.port = port;
		mcw = new MCQuery(source, port);
	}
	public String getO_M()
	{
		return o_m;
	}
	public String getSource()
	{
		return source;
	}
	public void setIsOnline(boolean isOnline)
	{
		this.isOnline = isOnline;
	}
	public void setQueryRes(QueryResponse qr)
	{
		this.qr1 = qr;
	}
	public QueryResponse getResQueryRes()
	{
		return qr1;
	}
	public void bind(View item1)
	{
		this.item = item1;
		con_layer = (LinearLayout) item.findViewById(R.id.processingll);
		delete_layer = (LinearLayout) item.findViewById(R.id.deletell);
		title = (TextView) item.findViewById(R.id.mainrecabvlayerTextView1);
		source_v = (TextView) item.findViewById(R.id.mainrecabvlayerTextView2);
		om_view = (TextView) item.findViewById(R.id.mainrecabvlayerTextView3);
		isVi = (TextView) item.findViewById(R.id.mainrecabvlayerTextView4);
		cv=(CardView) item.findViewById(R.id.listcardCardView1);
	}
	public void startNetworkTracking(final resg i)
	{
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					mcw.fullStat(new QueryImpl.QueryCall(){

							@Override
							public void onSuc(QueryResponse qr)
							{
								Log.e("Alc_se","toBean");
								qr1=qr;
								isOnline=true;
								i.onSuccess();
								// TODO: Implement this method
							}

							@Override
							public void onFail(String f)
							{
								isOnline=false;
								i.onFailure(f);
								
								// TODO: Implement this method
							}
						});
					// TODO: Implement this method
				}
			}).start();
	}
	public void killConnLayer(){
		Animation.reavelUnitStart(con_layer,(int)(con_layer.getX()+(con_layer.getWidth()/2)),(int)(con_layer.getY()+(con_layer.getHeight()/2)),(int)Math.hypot(con_layer.getWidth()/2,con_layer.getHeight()/2),0);
	}
	
	public static interface resg
	{
		public void onSuccess();
		public void onFailure(String exc);
	}
	public static interface NetWorkCallback{
		public void onSuccess(String raw);
	}
}
