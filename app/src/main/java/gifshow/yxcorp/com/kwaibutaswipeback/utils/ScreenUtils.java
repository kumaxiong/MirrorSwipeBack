package gifshow.yxcorp.com.kwaibutaswipeback.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {
  private ScreenUtils(){}

  public static int getScreenWidth(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.widthPixels;
  }
  public static int getScreenHeight(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    DisplayMetrics outMetrics = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(outMetrics);
    return outMetrics.heightPixels;
  }
}
