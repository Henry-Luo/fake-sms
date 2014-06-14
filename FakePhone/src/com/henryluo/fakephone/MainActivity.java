package com.henryluo.fakephone;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;


/**@author Henry Luo
 * @version 1.0
 * 
 * © 2014 All Rights Reserved
 * HenryLuo@outlook.com
 */


public class MainActivity extends Activity {
	
	private DevicePolicyManager devicePolicyManager;
	private ComponentName componentName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new AlertDialog.Builder(MainActivity.this)
		.setIcon(R.drawable.ic_delete)
		.setTitle("抱歉！")
		.setMessage("程序发生了未知错误。\n暂时无法启动！")
        .setPositiveButton("退  出", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// lock off here
				//Toast.makeText(MainActivity.this, "test", 1).show();
		
				LockScreen();
				
			}
		})
		.setCancelable(false) //禁用返回键
        .show();
		
		
		
	}
	
	public void LockScreen(){
        
		devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 
        componentName = new ComponentName(this, LockReceiver.class); 
        
        if (devicePolicyManager.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器) 
        	devicePolicyManager.lockNow();// 直接锁屏 
            android.os.Process.killProcess(android.os.Process.myPid()); 
        }else{ 
            ActiveManager();//激活设备管理器获取权限 
            
            //android.os.Process.killProcess(android.os.Process.myPid());
        }     
    }
	
	
	// 解除绑定
    public void Bind(View v){
     
    	if(componentName != null){
    	devicePolicyManager.removeActiveAdmin(componentName);
        ActiveManager();
     }
    
    }
    
    @Override 
    protected void onResume() {//重写此方法用来在第一次激活设备管理器之后，返回再锁屏 
        
    	if (devicePolicyManager != null && devicePolicyManager.isAdminActive(componentName)) { 
            //devicePolicyManager.lockNow(); 
            //android.os.Process.killProcess(android.os.Process.myPid());
    		new AlertDialog.Builder(MainActivity.this)
    		.setIcon(R.drawable.ic_delete)
    		.setTitle("抱歉！")
    		.setMessage("程序发生了未知错误。\n暂时无法启动！")
            .setPositiveButton("退  出", new DialogInterface.OnClickListener() {
    			
    			@Override
    			public void onClick(DialogInterface dialog, int which) {
    				// lock off here
    				//Toast.makeText(MainActivity.this, "test", 1).show();
    		
    				LockScreen();
    				
    			}
    		})
    		.setCancelable(false) //禁用返回键
            .show();
        } 
        super.onResume(); 
    } 
	
    
    private void ActiveManager() { 
        
    	//使用隐式意图调用系统方法来激活指定的设备管理器 
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN); 
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName); 
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "锁定屏幕"); 
        startActivity(intent); 
    } 
   

    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
