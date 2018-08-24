项目地址：[https://github.com/kumaxiong/MirrorSwipeBack](https://github.com/kumaxiong/MirrorSwipeBack)
# MirrorSwipeBack

1. 继承自LinearLayout,左右滑动出现返回动画。
2. 默认设置左右两端都能够滑动,可以自行设置。（默认左右两侧）
3. 可以自行设置回调事件（默认就是调用当前activity的finish）

![ezgif-1-7a33c98bc2.gif](https://upload-images.jianshu.io/upload_images/1549768-6cd15f7721e16281.gif?imageMogr2/auto-orient/strip)


## Download

在build.gradle中引用

```groovy
 implementation 'com.kumaxiong.android:mirror-swipeback-layout:0.1.2'
```

### 使用

1. 在layout文件夹下建立：**layout_swipe_back.xml**

```xml
<com.kumaxiong.MirrorSwipeBackLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="match_parent"
  android:layout_height="match_parent"/>
```



2.在需要使用的activity中的onCreate方法中加入:

```java
public class MainActivity extends AppCompatActivity {

    MirrorSwipeBackLayout mMirrorSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMirrorSwipeBackLayout = MirrorSwipeBack.attach(this, R.layout.layout_swipe_back);
        mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
        mMirrorSwipeBackLayout.setRightSwipeEnable(true);
        mMirrorSwipeBackLayout.setSwipeBackListener(new MirrorSwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void completeSwipeBack() {
                // 你自己的逻辑
                Toast.makeText(MainActivity.this, "滑动返回 completeSwipeBack", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```


## 项目地址

如果觉得对你有帮助，欢迎star 。
使用过程中有问题，欢迎issue。

https://github.com/kumaxiong/MirrorSwipeBack
