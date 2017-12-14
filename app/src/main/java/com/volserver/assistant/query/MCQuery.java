package com.volserver.assistant.query;

import java.net.*;
import java.util.*;
import android.util.*;

/**
 * A class that handles Minecraft Query protocol requests
 * 
 * @author Ryan McCann
 */
public class MCQuery
{
	final static byte HANDSHAKE = 9;
	final static byte STAT = 0;
	int sess=0;
	String serverAddress = "xk.ceojy.org";
	int queryPort = 19132; // the default minecraft query port
	
	int localPort = 19136; // the local port we're connected to the server on
	
	private DatagramSocket socket = null; //prevent socket already bound exception
	private static int token;
	
	public MCQuery(){} // for testing, defaults to "localhost:25565"
	public MCQuery(String address)
	{
		this(address, 19132);
	}
	public MCQuery(String address, int port)
	{
		serverAddress = address;
		queryPort = port;
	}
	
	// used to get a session token
	private int handshake(final QueryImpl.QueryCall c)
	{
		QueryRequest req = new QueryRequest();
		req.type = HANDSHAKE;
		req.sessionID = generateSessionID();
		
		int val = 11 - req.toBytes().length; //should be 11 bytes total
		byte[] input = ByteUtils.padArrayEnd(req.toBytes(), val);
		sendUDP(input, new QueryImpl.UDP(){

				@Override
				public void onSuccess(byte[] o)
				{
					token = Integer.parseInt(new String(o).trim());
					// TODO: Implement this method
				}

				@Override
				public void onFailure(String exc)
				{
					c.onFail(exc);
					// TODO: Implement this method
				}
			});
		return req.sessionID;
	}

	/**
	 * Use this to get basic status information from the server.
	 * @return a <code>QueryResponse</code> object
	 */
	public void basicStat(final QueryImpl.QueryCall c)
	{
		handshake(new QueryImpl.QueryCall(){

				@Override
				public void onSuc(QueryResponse qr)
				{
					// TODO: Implement this method
				}

				@Override
				public void onFail(String f)
				{
					c.onFail(f);
					// TODO: Implement this method
				}
			}); //get the session token first

		QueryRequest req = new QueryRequest(); //create a request
		req.type = STAT;
		req.sessionID = generateSessionID();
		req.setPayload(token);
		byte[] send = req.toBytes();
		
		sendUDP(send, new QueryImpl.UDP(){

				@Override
				public void onSuccess(byte[] o)
				{
					QueryResponse res = new QueryResponse(o, false);
					c.onSuc(res);
					// TODO: Implement this method
				}

				@Override
				public void onFailure(String exc)
				{
					c.onFail(exc);
					// TODO: Implement this method
				}
			});
		
		
	}
	
	/**
	 * Use this to get more information, including players, from the server.
	 * @return a <code>QueryResponse</code> object
	 */
	public void fullStat(final QueryImpl.QueryCall c)
	{
//		basicStat() calls handshake()
//		QueryResponse basicResp = this.basicStat();
//		int numPlayers = basicResp.onlinePlayers; //TODO use to determine max length of full stat
		int i=handshake(new QueryImpl.QueryCall(){

				@Override
				public void onSuc(QueryResponse qr)
				{
					// TODO: Implement this method
				}

				@Override
				public void onFail(String f)
				{
					c.onFail(f);
					// TODO: Implement this method
				}
			});
		
		QueryRequest req = new QueryRequest();
		req.type = STAT;
		req.sessionID = i;
		req.setPayload(token);
		req.payload = ByteUtils.padArrayEnd(req.payload, 4); //for full stat, pad the payload with 4 null bytes
		
		byte[] send = req.toBytes();
		sendUDP(send, new QueryImpl.UDP(){

				@Override
				public void onSuccess(byte[] o)
				{
					QueryResponse res = new QueryResponse(o, true);
					c.onSuc(res);
					// TODO: Implement this method
				}

				@Override
				public void onFailure(String exc)
				{
					c.onFail(exc);
					// TODO: Implement this method
				}
			});
		
		/*
		 * note: buffer size = base + #players(online) * 16(max username length)
		 */
		
		
	}
	
	private void sendUDP(byte[] input,QueryImpl.UDP udp_call)
	{
		try
		{
			while(socket == null)
			{
				try {
					socket = new DatagramSocket(localPort); //create the socket
				} catch (BindException e) {
					++localPort; // increment if port is already in use
				}
			}
			
			//create a packet from the input data and send it on the socket
			InetAddress address = InetAddress.getByName(serverAddress); //create InetAddress object from the address
			DatagramPacket packet1 = new DatagramPacket(input, input.length, address, queryPort);
			socket.send(packet1);
			
			//receive a response in a new packet
			byte[] out = new byte[1024]; //TODO guess at max size
			DatagramPacket packet = new DatagramPacket(out, out.length);
			socket.setSoTimeout(500); //one half second timeout
			socket.receive(packet);
			
			udp_call.onSuccess(packet.getData());
		}
		catch (SocketException e)
		{
			udp_call.onFailure(e.getMessage());
			e.printStackTrace();
		}
		catch (SocketTimeoutException e)
		{
			udp_call.onFailure("Socket Timeout! Is the server offline?");
			//System.exit(1);
			// throw exception
		}
		catch (UnknownHostException e)
		{
			udp_call.onFailure("Unknown host!");
			e.printStackTrace();
			//System.exit(1);
			// throw exception
		}
		catch (Exception e) //any other exceptions that may occur
		{
			udp_call.onFailure(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private int generateSessionID()
	{
		/*
		 * Can be anything, so we'll just use 1 for now. Apparently it can be omitted altogether.
		 * TODO: increment each time, or use a random int
		 */
		return ++sess;
	}
	
	@Override
	public void finalize()
	{
		socket.close();
	}
	
	//debug
	static void printBytes(byte[] arr)
	{
		for(byte b : arr) System.out.print(b + " ");
		System.out.println();
	}
	static void printHex(byte[] arr)
	{
		System.out.println(toHex(arr));
	}
	static String toHex(byte[] b)
	{
		String out = "";
		for(byte bb : b)
		{
			out += String.format("%02X ", bb);
		}
		return out;
	}
	
	
}
