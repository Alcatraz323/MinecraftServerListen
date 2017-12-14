package com.volserver.assistant.query;

public class QueryImpl
{
	public static interface UDP{
		public void onSuccess(byte[] o);
		public void onFailure(String exc);
	}
	public static interface QueryCall{
		public void onSuc(QueryResponse qr);
		public void onFail(String f);
	}
}
