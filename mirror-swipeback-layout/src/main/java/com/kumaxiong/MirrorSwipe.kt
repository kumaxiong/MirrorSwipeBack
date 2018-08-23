package com.kumaxiong

import android.app.Activity
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

object MirrorSwipe {

  fun attach(activity: Activity, `fun`: MirrorSwipeBackLayout.OnSwipeBackListener, @LayoutRes layoutId: Int): MirrorSwipeBackLayout {
    val layout = LayoutInflater.from(activity).inflate(layoutId, null) as MirrorSwipeBackLayout
    Helper(layout).attachSwipeBack(activity, `fun`)
    return layout
  }

  fun attach(activity: Activity, @LayoutRes layoutId: Int): MirrorSwipeBackLayout {
    val layout = LayoutInflater.from(activity).inflate(layoutId, null) as MirrorSwipeBackLayout
    Helper(layout).attachSwipeBack(activity, null)
    return layout
  }

  internal class Helper(val mSwipeBackLayout: MirrorSwipeBackLayout) {

    fun attachSwipeBack(activity: Activity?, `fun`: MirrorSwipeBackLayout.OnSwipeBackListener?) {
      if (`fun` == null) {
        mSwipeBackLayout.setSwipeBackListener(MirrorSwipeBackLayout.OnSwipeBackListener { activity!!.finish() })
      } else {
        mSwipeBackLayout.setSwipeBackListener(`fun`)
      }

      if (activity == null || activity.window == null || activity.window.decorView !is ViewGroup) {
        return
      }

      val decorView = activity.window.decorView as ViewGroup
      if (decorView != null) {
        if (decorView.childCount > 0) {
          val child = decorView.getChildAt(0)
          decorView.removeView(child)
          mSwipeBackLayout.addView(child)
        }

        decorView.addView(mSwipeBackLayout)
      }
    }
  }
}
