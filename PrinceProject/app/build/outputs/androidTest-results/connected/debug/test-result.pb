
‘D
n
EditProfileActivityTestcom.example.princeprojecttestUserInformationSubmission2óﬂ≥∫¿ö‡a:§ﬂ≥∫ÄèÖÕ¬=
Œandroidx.test.espresso.PerformException: Error performing 'single click - At Coordinates: 1141, 1639 and precision: 16, 16' on view 'Animations or transitions are enabled on the target device.
For more info check: https://developer.android.com/training/testing/espresso/setup#set-up-environment

an instance of android.widget.TextView and view.getText() with or without transformation to match: is "Confirm"'.
at androidx.test.espresso.PerformException$Builder.build(PerformException.java:86)
at androidx.test.espresso.base.PerformExceptionHandler.handleSafely(PerformExceptionHandler.java:56)
at androidx.test.espresso.base.PerformExceptionHandler.handleSafely(PerformExceptionHandler.java:31)
at androidx.test.espresso.base.DefaultFailureHandler$TypedFailureHandler.handle(DefaultFailureHandler.java:158)
at androidx.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:120)
at androidx.test.espresso.ViewInteraction.waitForAndHandleInteractionResults(ViewInteraction.java:385)
at androidx.test.espresso.ViewInteraction.desugaredPerform(ViewInteraction.java:212)
at androidx.test.espresso.ViewInteraction.perform(ViewInteraction.java:140)
at com.example.princeproject.EditProfileActivityTest.testUserInformationSubmission(EditProfileActivityTest.java:47)
... 32 trimmed
Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference
at com.example.princeproject.ProfilePage.EditProfileFragment.lambda$onCreateDialog$0$com-example-princeproject-ProfilePage-EditProfileFragment(EditProfileFragment.java:200)
at com.example.princeproject.ProfilePage.EditProfileFragment$$ExternalSyntheticLambda0.onClick(D8$$SyntheticClass:0)
at android.view.View.performClick(View.java:7792)
at android.widget.TextView.performClick(TextView.java:16112)
at com.google.android.material.button.MaterialButton.performClick(MaterialButton.java:1218)
at android.view.View.performClickInternal(View.java:7769)
at android.view.View.access$3800(View.java:910)
at android.view.View$PerformClick.run(View.java:30218)
at android.os.Handler.handleCallback(Handler.java:938)
at android.os.Handler.dispatchMessage(Handler.java:99)
at androidx.test.espresso.base.Interrogator.loopAndInterrogate(Interrogator.java:156)
at androidx.test.espresso.base.UiControllerImpl.loopUntil(UiControllerImpl.java:510)
at androidx.test.espresso.base.UiControllerImpl.loopUntil(UiControllerImpl.java:468)
at androidx.test.espresso.base.UiControllerImpl.injectMotionEvent(UiControllerImpl.java:234)
at androidx.test.espresso.action.MotionEvents.sendUp(MotionEvents.java:175)
at androidx.test.espresso.action.MotionEvents.sendUp(MotionEvents.java:138)
at androidx.test.espresso.action.Tap.sendSingleTap(Tap.java:168)
at androidx.test.espresso.action.Tap.access$100(Tap.java:30)
at androidx.test.espresso.action.Tap$1.sendTap(Tap.java:46)
at androidx.test.espresso.action.GeneralClickAction.perform(GeneralClickAction.java:134)
at androidx.test.espresso.ViewInteraction$SingleExecutionViewAction.perform(ViewInteraction.java:429)
at androidx.test.espresso.ViewInteraction.doPerform(ViewInteraction.java:297)
at androidx.test.espresso.ViewInteraction.access$300(ViewInteraction.java:70)
at androidx.test.espresso.ViewInteraction$1.call(ViewInteraction.java:193)
at androidx.test.espresso.ViewInteraction$1.call(ViewInteraction.java:182)
at java.util.concurrent.FutureTask.run(FutureTask.java:266)
at android.os.Handler.handleCallback(Handler.java:938)
at android.os.Handler.dispatchMessage(Handler.java:99)
at android.os.Looper.loopOnce(Looper.java:226)
at android.os.Looper.loop(Looper.java:313)
at android.app.ActivityThread.main(ActivityThread.java:8663)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:567)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1135)
java.lang.NullPointerExceptionŒandroidx.test.espresso.PerformException: Error performing 'single click - At Coordinates: 1141, 1639 and precision: 16, 16' on view 'Animations or transitions are enabled on the target device.
For more info check: https://developer.android.com/training/testing/espresso/setup#set-up-environment

an instance of android.widget.TextView and view.getText() with or without transformation to match: is "Confirm"'.
at androidx.test.espresso.PerformException$Builder.build(PerformException.java:86)
at androidx.test.espresso.base.PerformExceptionHandler.handleSafely(PerformExceptionHandler.java:56)
at androidx.test.espresso.base.PerformExceptionHandler.handleSafely(PerformExceptionHandler.java:31)
at androidx.test.espresso.base.DefaultFailureHandler$TypedFailureHandler.handle(DefaultFailureHandler.java:158)
at androidx.test.espresso.base.DefaultFailureHandler.handle(DefaultFailureHandler.java:120)
at androidx.test.espresso.ViewInteraction.waitForAndHandleInteractionResults(ViewInteraction.java:385)
at androidx.test.espresso.ViewInteraction.desugaredPerform(ViewInteraction.java:212)
at androidx.test.espresso.ViewInteraction.perform(ViewInteraction.java:140)
at com.example.princeproject.EditProfileActivityTest.testUserInformationSubmission(EditProfileActivityTest.java:47)
... 32 trimmed
Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference
at com.example.princeproject.ProfilePage.EditProfileFragment.lambda$onCreateDialog$0$com-example-princeproject-ProfilePage-EditProfileFragment(EditProfileFragment.java:200)
at com.example.princeproject.ProfilePage.EditProfileFragment$$ExternalSyntheticLambda0.onClick(D8$$SyntheticClass:0)
at android.view.View.performClick(View.java:7792)
at android.widget.TextView.performClick(TextView.java:16112)
at com.google.android.material.button.MaterialButton.performClick(MaterialButton.java:1218)
at android.view.View.performClickInternal(View.java:7769)
at android.view.View.access$3800(View.java:910)
at android.view.View$PerformClick.run(View.java:30218)
at android.os.Handler.handleCallback(Handler.java:938)
at android.os.Handler.dispatchMessage(Handler.java:99)
at androidx.test.espresso.base.Interrogator.loopAndInterrogate(Interrogator.java:156)
at androidx.test.espresso.base.UiControllerImpl.loopUntil(UiControllerImpl.java:510)
at androidx.test.espresso.base.UiControllerImpl.loopUntil(UiControllerImpl.java:468)
at androidx.test.espresso.base.UiControllerImpl.injectMotionEvent(UiControllerImpl.java:234)
at androidx.test.espresso.action.MotionEvents.sendUp(MotionEvents.java:175)
at androidx.test.espresso.action.MotionEvents.sendUp(MotionEvents.java:138)
at androidx.test.espresso.action.Tap.sendSingleTap(Tap.java:168)
at androidx.test.espresso.action.Tap.access$100(Tap.java:30)
at androidx.test.espresso.action.Tap$1.sendTap(Tap.java:46)
at androidx.test.espresso.action.GeneralClickAction.perform(GeneralClickAction.java:134)
at androidx.test.espresso.ViewInteraction$SingleExecutionViewAction.perform(ViewInteraction.java:429)
at androidx.test.espresso.ViewInteraction.doPerform(ViewInteraction.java:297)
at androidx.test.espresso.ViewInteraction.access$300(ViewInteraction.java:70)
at androidx.test.espresso.ViewInteraction$1.call(ViewInteraction.java:193)
at androidx.test.espresso.ViewInteraction$1.call(ViewInteraction.java:182)
at java.util.concurrent.FutureTask.run(FutureTask.java:266)
at android.os.Handler.handleCallback(Handler.java:938)
at android.os.Handler.dispatchMessage(Handler.java:99)
at android.os.Looper.loopOnce(Looper.java:226)
at android.os.Looper.loop(Looper.java:313)
at android.app.ActivityThread.main(ActivityThread.java:8663)
at java.lang.reflect.Method.invoke(Native Method)
at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:567)
at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1135)
"˘

logcatandroid„
‡C:\Users\mikha\AndroidStudioProjects\princeProject1\PrinceProject\app\build\outputs\androidTest-results\connected\debug\SM-G973W - 12\logcat-com.example.princeproject.EditProfileActivityTest-testUserInformationSubmission.txt"≤

device-infoandroidó
îC:\Users\mikha\AndroidStudioProjects\princeProject1\PrinceProject\app\build\outputs\androidTest-results\connected\debug\SM-G973W - 12\device-info.pb"≥

device-info.meminfoandroidê
çC:\Users\mikha\AndroidStudioProjects\princeProject1\PrinceProject\app\build\outputs\androidTest-results\connected\debug\SM-G973W - 12\meminfo"≥

device-info.cpuinfoandroidê
çC:\Users\mikha\AndroidStudioProjects\princeProject1\PrinceProject\app\build\outputs\androidTest-results\connected\debug\SM-G973W - 12\cpuinfo" *ó
c
test-results.logOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver°
ûC:\Users\mikha\AndroidStudioProjects\princeProject1\PrinceProject\app\build\outputs\androidTest-results\connected\debug\SM-G973W - 12\testlog\test-results.log 2
text/plain