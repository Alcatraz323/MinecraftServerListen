package com.volserver.assistant.anim;
import android.animation.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;

public class Animation
{
	public static void reavelUnitStart(final View v,int startX,int startY,int initialRadius,int finalRadius){
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			Animator a=ViewAnimationUtils.createCircularReveal(v,startX,startY,initialRadius,finalRadius);
			a.setInterpolator(new AccelerateInterpolator());
			a.setDuration(500);
			a.start();
			a.addListener(new Animator.AnimatorListener(){

					@Override
					public void onAnimationStart(Animator p1)
					{
						
						// TODO: Implement this method
					}

					@Override
					public void onAnimationEnd(Animator p1)
					{
						v.setVisibility(View.GONE);
						// TODO: Implement this method
					}

					@Override
					public void onAnimationCancel(Animator p1)
					{
						// TODO: Implement this method
					}

					@Override
					public void onAnimationRepeat(Animator p1)
					{
						// TODO: Implement this method
					}
				});
		}else{
			v.setVisibility(View.GONE);
		}
	}
}
