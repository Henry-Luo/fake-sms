package com.henryluo.fakephone;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class LockReceiver extends DeviceAdminReceiver{ 
	   
	   
    @Override 
    public void onReceive(Context context, Intent intent) { 
    	
    	System.out.println("Receive");
    	super.onReceive(context, intent); 
    
    } 
   
    @Override 
    public void onEnabled(Context context, Intent intent) { 
        
    	System.out.println("Enabled"); 
        super.onEnabled(context, intent); 
        
    } 
   
    @Override 
    public void onDisabled(Context context, Intent intent) { 
        
    	System.out.println("Disabled"); 
        super.onDisabled(context, intent); 
        
    } 
   
   
} 
