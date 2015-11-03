package com.example.demo3c;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ButtonControlFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view=inflater.inflate(R.layout.controlbutton,container,false);
		SurfaceView surface=(SurfaceView)view.findViewById(R.id.show);
		System.out.println("s："+surface);
		MyPaintingBoard mpb=new MyPaintingBoard(surface,null,this);
		return view;
	}
	
	public void output(){
		
	}
}
