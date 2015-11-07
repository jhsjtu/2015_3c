package com.example.demo3c;


import com.example.demo3c.DeviceListFragment.DeviceActionListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements
	ActionBar.TabListener,
	ChannelListener,
	DeviceActionListener
{
	private static final String SELECTED_ITEM = "selected_item";
	TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final ActionBar actionBar = getActionBar();
		// 设置ActionBar的导航方式：Tab导航
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 依次添加3个Tab页，并为3个Tab标签添加事件监听器
		actionBar.addTab(actionBar.newTab().setText("连接")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("按键")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("声音")
			.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
			.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("手势")
				.setTabListener(this));
		
		//textView
		
		 tv= (TextView)findViewById(R.id.textView);
		tv.setText("Hello,pp!");
		//tv.setOnClickListener(null);
		
		Button bt=(Button)findViewById(R.id.button);
		bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv.setText("msoswdewh");
			}
			
		});
		
		
		//following is for wifi
		// add necessary intent values to be matched.

		intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		intentFilter
				.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

		manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		channel = manager.initialize(this, getMainLooper(), null);
	}
	 

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (savedInstanceState.containsKey(SELECTED_ITEM))
		{
			// 选中前面保存的索引对应的Fragment页
			getActionBar().setSelectedNavigationItem(
				savedInstanceState.getInt(SELECTED_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		// 将当前选中的Fragment页的索引保存到Bundle中
		outState.putInt(SELECTED_ITEM, 
			getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
		FragmentTransaction fragmentTransaction)
	{
	}
	// 当指定Tab被选中时激发该方法
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
		
		Fragment fragment = null;
		
		switch(tab.getPosition()){
		case(1):{
			// 创建一个新的Fragment对象
			fragment=new ButtonControlFragment();
			break;
			}
		}
		if (fragment!=null){
			// 获取FragmentTransaction对象
			FragmentTransaction ft = getFragmentManager()
					.beginTransaction();
			// 使用fragment代替该Activity中的container组件
			ft.replace(R.id.container, fragment);
			// 提交事务
			ft.commit();
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
	}
	
	
	//the following is for wifi 
	public static final String TAG = "wifidirectdemo";
	private WifiP2pManager manager;
	private boolean isWifiP2pEnabled = false;
	private boolean retryChannel = false;

	private final IntentFilter intentFilter = new IntentFilter();
	private Channel channel;
	private BroadcastReceiver receiver = null;

	/**
	 * @param isWifiP2pEnabled
	 *            the isWifiP2pEnabled to set
	 */
	public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled)
	{
		this.isWifiP2pEnabled = isWifiP2pEnabled;
	}
	
	/** register the BroadcastReceiver with the intent values to be matched */
	@Override
	public void onResume()
	{
		super.onResume();
		receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
		registerReceiver(receiver, intentFilter);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		unregisterReceiver(receiver);
	}

	/**
	 * Remove all peers and clear all fields. This is called on
	 * BroadcastReceiver receiving a state change event.
	 */
	public void resetData()
	{
		DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()
				.findFragmentById(R.id.frag_list);
		DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager()
				.findFragmentById(R.id.frag_detail);
		if (fragmentList != null)
		{
			fragmentList.clearPeers();
		}
		if (fragmentDetails != null)
		{
			fragmentDetails.resetViews();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_items, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.atn_direct_enable:
			if (manager != null && channel != null)
			{

				// Since this is the system wireless settings activity, it's
				// not going to send us a result. We will be notified by
				// WiFiDeviceBroadcastReceiver instead.

				startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
			} else
			{
				Log.e(TAG, "channel or manager is null");
			}
			return true;

		case R.id.atn_direct_discover:
			if (!isWifiP2pEnabled)
			{
				Toast.makeText(MyActivity.this,
						R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
				return true;
			}
			final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
					.findFragmentById(R.id.frag_list);
			fragment.onInitiateDiscovery();
			manager.discoverPeers(channel, new WifiP2pManager.ActionListener()
			{

				@Override
				public void onSuccess()
				{
					Toast.makeText(MyActivity.this,
							"Discovery Initiated", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(int reasonCode)
				{
					Toast.makeText(MyActivity.this,
							"Discovery Failed : " + reasonCode,
							Toast.LENGTH_SHORT).show();
				}
			});
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void showDetails(WifiP2pDevice device)
	{
		DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
				.findFragmentById(R.id.frag_detail);
		fragment.showDetails(device);

	}

	@Override
	public void connect(WifiP2pConfig config)
	{
		manager.connect(channel, config, new ActionListener()
		{

			@Override
			public void onSuccess()
			{
				// WiFiDirectBroadcastReceiver will notify us. Ignore for now.
			}

			@Override
			public void onFailure(int reason)
			{
				Toast.makeText(MyActivity.this,
						"Connect failed. Retry.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void disconnect()
	{
		final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
				.findFragmentById(R.id.frag_detail);
		fragment.resetViews();
		manager.removeGroup(channel, new ActionListener()
		{

			@Override
			public void onFailure(int reasonCode)
			{
				Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);

			}

			@Override
			public void onSuccess()
			{
				fragment.getView().setVisibility(View.GONE);
			}

		});
	}

	@Override
	public void onChannelDisconnected()
	{
		// we will try once more
		if (manager != null && !retryChannel)
		{
			Toast.makeText(this, "Channel lost. Trying again",
					Toast.LENGTH_LONG).show();
			resetData();
			retryChannel = true;
			manager.initialize(this, getMainLooper(), this);
		} else
		{
			Toast.makeText(
					this,
					"Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void cancelDisconnect()
	{

		/*
		 * A cancel abort request by user. Disconnect i.e. removeGroup if
		 * already connected. Else, request WifiP2pManager to abort the ongoing
		 * request
		 */
		if (manager != null)
		{
			final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
					.findFragmentById(R.id.frag_list);
			if (fragment.getDevice() == null
					|| fragment.getDevice().status == WifiP2pDevice.CONNECTED)
			{
				disconnect();
			} else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
					|| fragment.getDevice().status == WifiP2pDevice.INVITED)
			{

				manager.cancelConnect(channel, new ActionListener()
				{

					@Override
					public void onSuccess()
					{
						Toast.makeText(MyActivity.this,
								"Aborting connection", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onFailure(int reasonCode)
					{
						Toast.makeText(
								MyActivity.this,
								"Connect abort request failed. Reason Code: "
										+ reasonCode, Toast.LENGTH_SHORT)
								.show();
					}
				});
			}
		}

	}

}
