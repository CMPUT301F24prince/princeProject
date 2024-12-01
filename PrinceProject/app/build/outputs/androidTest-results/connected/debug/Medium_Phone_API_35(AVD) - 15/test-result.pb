
ì¤
Z
ListTestcom.example.princeprojecttestSelectRandomEntrantUI2ˆÃ¯ºÀïÐO:˜Ã¯ºÀêü3¼
ÃNandroidx.test.espresso.NoMatchingViewException: No views in hierarchy found matching: an instance of android.widget.TextView and view.getText() with or without transformation to match: is "Chosen"
If the target view is not part of the view hierarchy, you may need to use Espresso.onData to load it from one of the following AdapterViews:androidx.appcompat.widget.AppCompatSpinner{95e7d06 V.ED..CL. ........ 105,173-231,236 #7f0900df app:id/event_spinner aid=1073741824}

View Hierarchy:
+>DecorView{id=-1, visibility=VISIBLE, width=1080, height=2400, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params={(0,0)(fillxfill) ty=BASE_APPLICATION wanim=0x1030309
fl=LAYOUT_IN_SCREEN LAYOUT_INSET_DECOR SPLIT_TOUCH HARDWARE_ACCELERATED DRAWS_SYSTEM_BAR_BACKGROUNDS
pfl=NO_MOVE_ANIMATION FORCE_DRAW_STATUS_BAR_BACKGROUND FIT_INSETS_CONTROLLED
bhv=DEFAULT
fitSides=
frameRateBoostOnTouch=true
dvrrWindowFrameRateHint=true}, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=3}
|
+->LinearLayout{id=-1, visibility=VISIBLE, width=1080, height=2337, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=2}
|
+-->ViewStub{id=16908747, res-name=action_mode_bar_stub, visibility=GONE, width=0, height=0, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=true, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
|
+-->FrameLayout{id=-1, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=63.0, child-count=1}
|
+--->FitWindowsLinearLayout{id=2131296313, res-name=action_bar_root, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=2}
|
+---->ViewStubCompat{id=2131296324, res-name=action_mode_bar_stub, visibility=GONE, width=0, height=0, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=true, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
|
+---->ContentFrameLayout{id=16908290, res-name=content, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=1}
|
+----->ConstraintLayout{id=2131296471, res-name=event_fragment, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=6}
|
+------>MaterialTextView{id=2131296801, res-name=textView, visibility=VISIBLE, width=247, height=57, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=137.0, y=84.0, text=Select Event:, input-type=0, ime-target=false, has-links=false}
|
+------>AppCompatSpinner{id=2131296479, res-name=event_spinner, visibility=VISIBLE, width=126, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=105.0, y=173.0, child-count=0}
|
+------>MaterialButton{id=2131296560, res-name=lottery_button, visibility=VISIBLE, width=335, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=671.0, y=199.0, text=Start Lottery, input-type=0, ime-target=false, has-links=false, is-checked=false}
|
+------>MaterialButton{id=2131296564, res-name=manage_event_button, visibility=VISIBLE, width=364, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=642.0, y=68.0, text=Manage Event, input-type=0, ime-target=false, has-links=false, is-checked=false}
|
+------>TabLayout{id=2131296780, res-name=tab_layout, visibility=VISIBLE, width=1080, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=357.0, child-count=1}
|
+------->SlidingTabIndicator{id=-1, visibility=VISIBLE, width=1080, height=126, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=0}
|
+------>ViewPager2{id=2131296852, res-name=view_pager, visibility=VISIBLE, width=1080, height=1791, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=483.0, child-count=1}
|
+------->RecyclerViewImpl{id=1, visibility=VISIBLE, width=1080, height=1791, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=android.view.ViewGroup$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=0}
|
+->View{id=16908336, res-name=navigationBarBackground, visibility=VISIBLE, width=1080, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=2337.0}
|
+->View{id=16908335, res-name=statusBarBackground, visibility=VISIBLE, width=1080, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
The complete view hierarchy is available in artifact file 'view-hierarchy-1.txt'.
at androidx.test.espresso.NoMatchingViewException$Builder.build(NoMatchingViewException.java:185)
at androidx.test.espresso.base.DefaultFailureHandler.lambda$getNoMatchingViewExceptionTruncater$0(DefaultFailureHandler.java:93)
at androidx.test.espresso.base.DefaultFailureHandler$$ExternalSyntheticLambda1.truncateExceptionMessage(D8$$SyntheticClass:0)
at androidx.test.espresso.base.ViewHierarchyExceptionHandler.handleSafely(ViewHierarchyExceptionHandler.java:72)
at androidx.test.espresso.base.ViewHierarchyExceptionHandler.handleSafely(ViewHierarchyExceptionHandler.java:38)
at androidx.test.espresso.base.DefaultFailureHandler$TypedFailureHandler.handle(DefaultFailureHandler.java:158)
at androidx.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:120)
at androidx.test.espresso.ViewInteraction.waitForAndHandleInteractionResults(ViewInteraction.java:385)
at androidx.test.espresso.ViewInteraction.check(ViewInteraction.java:366)
at com.example.princeproject.ListTest.testSelectRandomEntrantUI(ListTest.java:31)
.androidx.test.espresso.NoMatchingViewExceptionÃNandroidx.test.espresso.NoMatchingViewException: No views in hierarchy found matching: an instance of android.widget.TextView and view.getText() with or without transformation to match: is "Chosen"
If the target view is not part of the view hierarchy, you may need to use Espresso.onData to load it from one of the following AdapterViews:androidx.appcompat.widget.AppCompatSpinner{95e7d06 V.ED..CL. ........ 105,173-231,236 #7f0900df app:id/event_spinner aid=1073741824}

View Hierarchy:
+>DecorView{id=-1, visibility=VISIBLE, width=1080, height=2400, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params={(0,0)(fillxfill) ty=BASE_APPLICATION wanim=0x1030309
fl=LAYOUT_IN_SCREEN LAYOUT_INSET_DECOR SPLIT_TOUCH HARDWARE_ACCELERATED DRAWS_SYSTEM_BAR_BACKGROUNDS
pfl=NO_MOVE_ANIMATION FORCE_DRAW_STATUS_BAR_BACKGROUND FIT_INSETS_CONTROLLED
bhv=DEFAULT
fitSides=
frameRateBoostOnTouch=true
dvrrWindowFrameRateHint=true}, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=3}
|
+->LinearLayout{id=-1, visibility=VISIBLE, width=1080, height=2337, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=2}
|
+-->ViewStub{id=16908747, res-name=action_mode_bar_stub, visibility=GONE, width=0, height=0, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=true, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
|
+-->FrameLayout{id=-1, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=63.0, child-count=1}
|
+--->FitWindowsLinearLayout{id=2131296313, res-name=action_bar_root, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=2}
|
+---->ViewStubCompat{id=2131296324, res-name=action_mode_bar_stub, visibility=GONE, width=0, height=0, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=true, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
|
+---->ContentFrameLayout{id=16908290, res-name=content, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.LinearLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=1}
|
+----->ConstraintLayout{id=2131296471, res-name=event_fragment, visibility=VISIBLE, width=1080, height=2274, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=6}
|
+------>MaterialTextView{id=2131296801, res-name=textView, visibility=VISIBLE, width=247, height=57, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=137.0, y=84.0, text=Select Event:, input-type=0, ime-target=false, has-links=false}
|
+------>AppCompatSpinner{id=2131296479, res-name=event_spinner, visibility=VISIBLE, width=126, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=105.0, y=173.0, child-count=0}
|
+------>MaterialButton{id=2131296560, res-name=lottery_button, visibility=VISIBLE, width=335, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=671.0, y=199.0, text=Start Lottery, input-type=0, ime-target=false, has-links=false, is-checked=false}
|
+------>MaterialButton{id=2131296564, res-name=manage_event_button, visibility=VISIBLE, width=364, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=true, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=642.0, y=68.0, text=Manage Event, input-type=0, ime-target=false, has-links=false, is-checked=false}
|
+------>TabLayout{id=2131296780, res-name=tab_layout, visibility=VISIBLE, width=1080, height=126, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=357.0, child-count=1}
|
+------->SlidingTabIndicator{id=-1, visibility=VISIBLE, width=1080, height=126, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=0}
|
+------>ViewPager2{id=2131296852, res-name=view_pager, visibility=VISIBLE, width=1080, height=1791, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=androidx.constraintlayout.widget.ConstraintLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=483.0, child-count=1}
|
+------->RecyclerViewImpl{id=1, visibility=VISIBLE, width=1080, height=1791, has-focus=false, has-focusable=true, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=true, is-layout-requested=false, is-selected=false, layout-params=android.view.ViewGroup$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0, child-count=0}
|
+->View{id=16908336, res-name=navigationBarBackground, visibility=VISIBLE, width=1080, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=2337.0}
|
+->View{id=16908335, res-name=statusBarBackground, visibility=VISIBLE, width=1080, height=63, has-focus=false, has-focusable=false, has-window-focus=true, is-clickable=false, is-enabled=true, is-focused=false, is-focusable=false, is-layout-requested=false, is-selected=false, layout-params=android.widget.FrameLayout$LayoutParams@YYYYYY, tag=null, root-is-layout-requested=false, has-input-connection=false, x=0.0, y=0.0}
The complete view hierarchy is available in artifact file 'view-hierarchy-1.txt'.
at androidx.test.espresso.NoMatchingViewException$Builder.build(NoMatchingViewException.java:185)
at androidx.test.espresso.base.DefaultFailureHandler.lambda$getNoMatchingViewExceptionTruncater$0(DefaultFailureHandler.java:93)
at androidx.test.espresso.base.DefaultFailureHandler$$ExternalSyntheticLambda1.truncateExceptionMessage(D8$$SyntheticClass:0)
at androidx.test.espresso.base.ViewHierarchyExceptionHandler.handleSafely(ViewHierarchyExceptionHandler.java:72)
at androidx.test.espresso.base.ViewHierarchyExceptionHandler.handleSafely(ViewHierarchyExceptionHandler.java:38)
at androidx.test.espresso.base.DefaultFailureHandler$TypedFailureHandler.handle(DefaultFailureHandler.java:158)
at androidx.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:120)
at androidx.test.espresso.ViewInteraction.waitForAndHandleInteractionResults(ViewInteraction.java:385)
at androidx.test.espresso.ViewInteraction.check(ViewInteraction.java:366)
at com.example.princeproject.ListTest.testSelectRandomEntrantUI(ListTest.java:31)
"÷

logcatandroidá
ÞC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\logcat-com.example.princeproject.ListTest-testSelectRandomEntrantUI.txt"Ã

device-infoandroid¨
¥C:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\device-info.pb"Ä

device-info.meminfoandroid¡
žC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\meminfo"Ä

device-info.cpuinfoandroid¡
žC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\cpuinfo*¨
c
test-results.logOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver²
¯C:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\testlog\test-results.log 2
text/plain