
�N
y
EntrantListUITestcom.example.princeproject-testMyEventsButtonLaunchesEntrantListActivity2��������:��������F
�#android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: java.lang.reflect.InvocationTargetException
at java.lang.reflect.Constructor.newInstance0(Native Method)
at java.lang.reflect.Constructor.newInstance(Constructor.java:343)
at android.view.LayoutInflater.createView(LayoutInflater.java:742)
at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:894)
at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:849)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1011)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.inflate(LayoutInflater.java:570)
at android.view.LayoutInflater.inflate(LayoutInflater.java:462)
at com.example.princeproject.ProfilePage.ProfileFragment.onCreateView(ProfileFragment.java:93)
at androidx.fragment.app.Fragment.performCreateView(Fragment.java:3119)
at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:577)
at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:286)
at androidx.fragment.app.FragmentStore.moveToExpectedState(FragmentStore.java:114)
at androidx.fragment.app.FragmentManager.moveToState(FragmentManager.java:1685)
at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:3319)
at androidx.fragment.app.FragmentManager.dispatchPause(FragmentManager.java:3255)
at androidx.fragment.app.FragmentController.dispatchPause(FragmentController.java:296)
at androidx.fragment.app.FragmentActivity.onPause(FragmentActivity.java:284)
at android.app.Activity.performPause(Activity.java:9207)
at android.app.Instrumentation.callActivityOnPause(Instrumentation.java:1776)
at androidx.test.runner.MonitoringInstrumentation.callActivityOnPause(MonitoringInstrumentation.java:804)
at android.app.ActivityThread.performPauseActivityIfNeeded(ActivityThread.java:5598)
at android.app.ActivityThread.performPauseActivity(ActivityThread.java:5559)
at android.app.ActivityThread.handlePauseActivity(ActivityThread.java:5511)
at android.app.servertransaction.PauseActivityItem.execute(PauseActivityItem.java:47)
at android.app.servertransaction.ActivityTransactionItem.execute(ActivityTransactionItem.java:60)
at android.app.servertransaction.TransactionExecutor.executeLifecycleItem(TransactionExecutor.java:225)
at android.app.servertransaction.TransactionExecutor.executeTransactionItems(TransactionExecutor.java:107)
at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:81)
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2636)
at android.os.Handler.dispatchMessage(Handler.java:107)
at android.os.Looper.loopOnce(Looper.java:232)
at android.os.Looper.loop(Looper.java:317)
at android.app.ActivityThread.main(ActivityThread.java:8705)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:580)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:886)
Caused by: java.lang.UnsupportedOperationException: Failed to resolve attribute at index 13: TypedValue{t=0x2/d=0x7f040115 a=-1}, theme={InheritanceMap=[id=0x7f130123com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, id=0x1030069android:style/Theme.WithActionBar, id=0x1030005android:style/Theme], Themes=[com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, forced, android:style/Theme.DeviceDefault.Light.DarkActionBar, forced]}
at android.content.res.TypedArray.getDrawableForDensity(TypedArray.java:1011)
at android.content.res.TypedArray.getDrawable(TypedArray.java:995)
at android.view.View.<init>(View.java:6017)
at android.view.ViewGroup.<init>(ViewGroup.java:703)
at android.view.ViewGroup.<init>(ViewGroup.java:699)
at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:265)
at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:261)
... 42 more
android.view.InflateException�#android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: java.lang.reflect.InvocationTargetException
at java.lang.reflect.Constructor.newInstance0(Native Method)
at java.lang.reflect.Constructor.newInstance(Constructor.java:343)
at android.view.LayoutInflater.createView(LayoutInflater.java:742)
at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:894)
at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:849)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1011)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
at android.view.LayoutInflater.inflate(LayoutInflater.java:570)
at android.view.LayoutInflater.inflate(LayoutInflater.java:462)
at com.example.princeproject.ProfilePage.ProfileFragment.onCreateView(ProfileFragment.java:93)
at androidx.fragment.app.Fragment.performCreateView(Fragment.java:3119)
at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:577)
at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:286)
at androidx.fragment.app.FragmentStore.moveToExpectedState(FragmentStore.java:114)
at androidx.fragment.app.FragmentManager.moveToState(FragmentManager.java:1685)
at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:3319)
at androidx.fragment.app.FragmentManager.dispatchPause(FragmentManager.java:3255)
at androidx.fragment.app.FragmentController.dispatchPause(FragmentController.java:296)
at androidx.fragment.app.FragmentActivity.onPause(FragmentActivity.java:284)
at android.app.Activity.performPause(Activity.java:9207)
at android.app.Instrumentation.callActivityOnPause(Instrumentation.java:1776)
at androidx.test.runner.MonitoringInstrumentation.callActivityOnPause(MonitoringInstrumentation.java:804)
at android.app.ActivityThread.performPauseActivityIfNeeded(ActivityThread.java:5598)
at android.app.ActivityThread.performPauseActivity(ActivityThread.java:5559)
at android.app.ActivityThread.handlePauseActivity(ActivityThread.java:5511)
at android.app.servertransaction.PauseActivityItem.execute(PauseActivityItem.java:47)
at android.app.servertransaction.ActivityTransactionItem.execute(ActivityTransactionItem.java:60)
at android.app.servertransaction.TransactionExecutor.executeLifecycleItem(TransactionExecutor.java:225)
at android.app.servertransaction.TransactionExecutor.executeTransactionItems(TransactionExecutor.java:107)
at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:81)
at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2636)
at android.os.Handler.dispatchMessage(Handler.java:107)
at android.os.Looper.loopOnce(Looper.java:232)
at android.os.Looper.loop(Looper.java:317)
at android.app.ActivityThread.main(ActivityThread.java:8705)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:580)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:886)
Caused by: java.lang.UnsupportedOperationException: Failed to resolve attribute at index 13: TypedValue{t=0x2/d=0x7f040115 a=-1}, theme={InheritanceMap=[id=0x7f130123com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, id=0x1030069android:style/Theme.WithActionBar, id=0x1030005android:style/Theme], Themes=[com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, forced, android:style/Theme.DeviceDefault.Light.DarkActionBar, forced]}
at android.content.res.TypedArray.getDrawableForDensity(TypedArray.java:1011)
at android.content.res.TypedArray.getDrawable(TypedArray.java:995)
at android.view.View.<init>(View.java:6017)
at android.view.ViewGroup.<init>(ViewGroup.java:703)
at android.view.ViewGroup.<init>(ViewGroup.java:699)
at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:265)
at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:261)
... 42 more
"�

logcatandroid�
�C:\Users\Kyle\Documents\GitHub\princeProject2\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\logcat-com.example.princeproject.EntrantListUITest-testMyEventsButtonLaunchesEntrantListActivity.txt"�

device-infoandroid�
�C:\Users\Kyle\Documents\GitHub\princeProject2\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\device-info.pb"�

device-info.meminfoandroid�
�C:\Users\Kyle\Documents\GitHub\princeProject2\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\meminfo"�

device-info.cpuinfoandroid�
�C:\Users\Kyle\Documents\GitHub\princeProject2\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\cpuinfo" *�
c
test-results.logOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver�
�C:\Users\Kyle\Documents\GitHub\princeProject2\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\testlog\test-results.log 2
text/plain2�
QOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver"INSTRUMENTATION_FAILED*OTest run failed to complete. Instrumentation run failed due to Process crashed.2�1*�1Logcat of last crash: 
Process: com.example.princeproject, PID: 696
java.lang.RuntimeException: Unable to pause activity {com.example.princeproject/androidx.fragment.app.testing.EmptyFragmentActivity}: android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
	at android.app.ActivityThread.performPauseActivityIfNeeded(ActivityThread.java:5608)
	at android.app.ActivityThread.performPauseActivity(ActivityThread.java:5559)
	at android.app.ActivityThread.handlePauseActivity(ActivityThread.java:5511)
	at android.app.servertransaction.PauseActivityItem.execute(PauseActivityItem.java:47)
	at android.app.servertransaction.ActivityTransactionItem.execute(ActivityTransactionItem.java:60)
	at android.app.servertransaction.TransactionExecutor.executeLifecycleItem(TransactionExecutor.java:225)
	at android.app.servertransaction.TransactionExecutor.executeTransactionItems(TransactionExecutor.java:107)
	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:81)
	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2636)
	at android.os.Handler.dispatchMessage(Handler.java:107)
	at android.os.Looper.loopOnce(Looper.java:232)
	at android.os.Looper.loop(Looper.java:317)
	at android.app.ActivityThread.main(ActivityThread.java:8705)
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:580)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:886)
Caused by: android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: android.view.InflateException: Binary XML file line #30 in com.example.princeproject:layout/fragment_profile: Error inflating class <unknown>
Caused by: java.lang.reflect.InvocationTargetException
	at java.lang.reflect.Constructor.newInstance0(Native Method)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:343)
	at android.view.LayoutInflater.createView(LayoutInflater.java:742)
	at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:894)
	at android.view.LayoutInflater.createViewFromTag(LayoutInflater.java:849)
	at android.view.LayoutInflater.rInflate(LayoutInflater.java:1011)
	at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
	at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
	at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
	at android.view.LayoutInflater.rInflate(LayoutInflater.java:1014)
	at android.view.LayoutInflater.rInflateChildren(LayoutInflater.java:972)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:570)
	at android.view.LayoutInflater.inflate(LayoutInflater.java:462)
	at com.example.princeproject.ProfilePage.ProfileFragment.onCreateView(ProfileFragment.java:93)
	at androidx.fragment.app.Fragment.performCreateView(Fragment.java:3119)
	at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:577)
	at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:286)
	at androidx.fragment.app.FragmentStore.moveToExpectedState(FragmentStore.java:114)
	at androidx.fragment.app.FragmentManager.moveToState(FragmentManager.java:1685)
	at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:3319)
	at androidx.fragment.app.FragmentManager.dispatchPause(FragmentManager.java:3255)
	at androidx.fragment.app.FragmentController.dispatchPause(FragmentController.java:296)
	at androidx.fragment.app.FragmentActivity.onPause(FragmentActivity.java:284)
	at android.app.Activity.performPause(Activity.java:9207)
	at android.app.Instrumentation.callActivityOnPause(Instrumentation.java:1776)
	at androidx.test.runner.MonitoringInstrumentation.callActivityOnPause(MonitoringInstrumentation.java:804)
	at android.app.ActivityThread.performPauseActivityIfNeeded(ActivityThread.java:5598)
	at android.app.ActivityThread.performPauseActivity(ActivityThread.java:5559)
	at android.app.ActivityThread.handlePauseActivity(ActivityThread.java:5511)
	at android.app.servertransaction.PauseActivityItem.execute(PauseActivityItem.java:47)
	at android.app.servertransaction.ActivityTransactionItem.execute(ActivityTransactionItem.java:60)
	at android.app.servertransaction.TransactionExecutor.executeLifecycleItem(TransactionExecutor.java:225)
	at android.app.servertransaction.TransactionExecutor.executeTransactionItems(TransactionExecutor.java:107)
	at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:81)
	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2636)
	at android.os.Handler.dispatchMessage(Handler.java:107)
	at android.os.Looper.loopOnce(Looper.java:232)
	at android.os.Looper.loop(Looper.java:317)
	at android.app.ActivityThread.main(ActivityThread.java:8705)
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:580)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:886)
Caused by: java.lang.UnsupportedOperationException: Failed to resolve attribute at index 13: TypedValue{t=0x2/d=0x7f040115 a=-1}, theme={InheritanceMap=[id=0x7f130123com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, id=0x1030069android:style/Theme.WithActionBar, id=0x1030005android:style/Theme], Themes=[com.example.princeproject:style/FragmentScenarioEmptyFragmentActivityTheme, forced, android:style/Theme.DeviceDefault.Light.DarkActionBar, forced]}
	at android.content.res.TypedArray.getDrawableForDensity(TypedArray.java:1011)
	at android.content.res.TypedArray.getDrawable(TypedArray.java:995)
	at android.view.View.<init>(View.java:6017)
	at android.view.ViewGroup.<init>(ViewGroup.java:703)
	at android.view.ViewGroup.<init>(ViewGroup.java:699)
	at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:265)
	at androidx.appcompat.widget.Toolbar.<init>(Toolbar.java:261)
	... 42 more