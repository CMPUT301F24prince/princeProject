
ùV
]
ListTestcom.example.princeprojecttestRemoveUserFromWaitlist2µÄ∞∫Äì´≤:ªÄ∞∫Ä»‚˚ÍN
õ'org.mockito.exceptions.base.MockitoException:
Failed to release mocks

This should not happen unless you are using a third-party mock maker
at com.example.princeproject.ListTest.setup(ListTest.java:44)
... 31 trimmed
Caused by: org.mockito.exceptions.base.MockitoException:
Mockito cannot mock this class: class com.google.firebase.firestore.CollectionReference.

Mockito can only mock non-private & non-final classes.
If you're not sure why you're getting this error, please report to the mailing list.



IMPORTANT INFORMATION FOR ANDROID USERS:

The regular Byte Buddy mock makers cannot generate code on an Android VM!
To resolve this, please use the 'mockito-android' dependency for your application:
https://search.maven.org/artifact/org.mockito/mockito-android

Java               : 0.9
JVM vendor name    : The Android Project
JVM vendor version : 2.1.0
JVM name           : Dalvik
JVM version        : 0.9
JVM info           : null
OS name            : Linux
OS version         : 6.6.30-android15-7-gbb616d66d8a9-ab11968886


Underlying exception : java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
... 33 more
Caused by: java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:789)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.doLoad(AndroidClassLoadingStrategy.java:652)
at net.bytebuddy.android.AndroidClassLoadingStrategy.load(AndroidClassLoadingStrategy.java:148)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.load(AndroidClassLoadingStrategy.java:645)
at net.bytebuddy.dynamic.TypeResolutionStrategy$Passive.initialize(TypeResolutionStrategy.java:101)
at net.bytebuddy.dynamic.DynamicType$Default$Unloaded.load(DynamicType.java:6166)
at org.mockito.internal.creation.bytebuddy.SubclassBytecodeGenerator.mockClass(SubclassBytecodeGenerator.java:289)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.lambda$mockClass$0$org-mockito-internal-creation-bytebuddy-TypeCachingBytecodeGenerator(TypeCachingBytecodeGenerator.java:47)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator$$ExternalSyntheticLambda0.call(D8$$SyntheticClass:0)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:168)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:399)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:190)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:410)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.mockClass(TypeCachingBytecodeGenerator.java:40)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMockType(SubclassByteBuddyMockMaker.java:77)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMock(SubclassByteBuddyMockMaker.java:43)
at org.mockito.android.internal.creation.AndroidByteBuddyMockMaker.createMock(AndroidByteBuddyMockMaker.java:39)
at org.mockito.internal.util.MockUtil.createMock(MockUtil.java:53)
at org.mockito.internal.MockitoCore.mock(MockitoCore.java:96)
at org.mockito.Mockito.mock(Mockito.java:1965)
at org.mockito.internal.configuration.MockAnnotationProcessor.processAnnotationForMock(MockAnnotationProcessor.java:66)
at org.mockito.internal.configuration.MockAnnotationProcessor.process(MockAnnotationProcessor.java:27)
at org.mockito.internal.configuration.MockAnnotationProcessor.process(MockAnnotationProcessor.java:24)
at org.mockito.internal.configuration.IndependentAnnotationEngine.createMockFor(IndependentAnnotationEngine.java:44)
at org.mockito.internal.configuration.IndependentAnnotationEngine.process(IndependentAnnotationEngine.java:72)
at org.mockito.internal.configuration.InjectingAnnotationEngine.processIndependentAnnotations(InjectingAnnotationEngine.java:73)
at org.mockito.internal.configuration.InjectingAnnotationEngine.process(InjectingAnnotationEngine.java:47)
at org.mockito.MockitoAnnotations.openMocks(MockitoAnnotations.java:81)
at org.mockito.MockitoAnnotations.initMocks(MockitoAnnotations.java:99)
... 33 more
Caused by: java.lang.SecurityException: Writable dex file '/data/user/0/com.example.princeproject/cache/a5HviUnx.jar' is not allowed.
at dalvik.system.DexFile.openDexFileNative(Native Method)
at dalvik.system.DexFile.openDexFile(DexFile.java:406)
at dalvik.system.DexFile.<init>(DexFile.java:128)
at dalvik.system.DexFile.<init>(DexFile.java:101)
at dalvik.system.DexPathList.loadDexFile(DexPathList.java:438)
at dalvik.system.DexPathList.makeDexElements(DexPathList.java:397)
at dalvik.system.DexPathList.addDexPath(DexPathList.java:220)
at dalvik.system.BaseDexClassLoader.addDexPath(BaseDexClassLoader.java:287)
at java.lang.reflect.Method.invoke(Native Method)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:780)
... 61 more
,org.mockito.exceptions.base.MockitoExceptionõ'org.mockito.exceptions.base.MockitoException:
Failed to release mocks

This should not happen unless you are using a third-party mock maker
at com.example.princeproject.ListTest.setup(ListTest.java:44)
... 31 trimmed
Caused by: org.mockito.exceptions.base.MockitoException:
Mockito cannot mock this class: class com.google.firebase.firestore.CollectionReference.

Mockito can only mock non-private & non-final classes.
If you're not sure why you're getting this error, please report to the mailing list.



IMPORTANT INFORMATION FOR ANDROID USERS:

The regular Byte Buddy mock makers cannot generate code on an Android VM!
To resolve this, please use the 'mockito-android' dependency for your application:
https://search.maven.org/artifact/org.mockito/mockito-android

Java               : 0.9
JVM vendor name    : The Android Project
JVM vendor version : 2.1.0
JVM name           : Dalvik
JVM version        : 0.9
JVM info           : null
OS name            : Linux
OS version         : 6.6.30-android15-7-gbb616d66d8a9-ab11968886


Underlying exception : java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
... 33 more
Caused by: java.lang.IllegalStateException: Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:789)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.doLoad(AndroidClassLoadingStrategy.java:652)
at net.bytebuddy.android.AndroidClassLoadingStrategy.load(AndroidClassLoadingStrategy.java:148)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting.load(AndroidClassLoadingStrategy.java:645)
at net.bytebuddy.dynamic.TypeResolutionStrategy$Passive.initialize(TypeResolutionStrategy.java:101)
at net.bytebuddy.dynamic.DynamicType$Default$Unloaded.load(DynamicType.java:6166)
at org.mockito.internal.creation.bytebuddy.SubclassBytecodeGenerator.mockClass(SubclassBytecodeGenerator.java:289)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.lambda$mockClass$0$org-mockito-internal-creation-bytebuddy-TypeCachingBytecodeGenerator(TypeCachingBytecodeGenerator.java:47)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator$$ExternalSyntheticLambda0.call(D8$$SyntheticClass:0)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:168)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:399)
at net.bytebuddy.TypeCache.findOrInsert(TypeCache.java:190)
at net.bytebuddy.TypeCache$WithInlineExpunction.findOrInsert(TypeCache.java:410)
at org.mockito.internal.creation.bytebuddy.TypeCachingBytecodeGenerator.mockClass(TypeCachingBytecodeGenerator.java:40)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMockType(SubclassByteBuddyMockMaker.java:77)
at org.mockito.internal.creation.bytebuddy.SubclassByteBuddyMockMaker.createMock(SubclassByteBuddyMockMaker.java:43)
at org.mockito.android.internal.creation.AndroidByteBuddyMockMaker.createMock(AndroidByteBuddyMockMaker.java:39)
at org.mockito.internal.util.MockUtil.createMock(MockUtil.java:53)
at org.mockito.internal.MockitoCore.mock(MockitoCore.java:96)
at org.mockito.Mockito.mock(Mockito.java:1965)
at org.mockito.internal.configuration.MockAnnotationProcessor.processAnnotationForMock(MockAnnotationProcessor.java:66)
at org.mockito.internal.configuration.MockAnnotationProcessor.process(MockAnnotationProcessor.java:27)
at org.mockito.internal.configuration.MockAnnotationProcessor.process(MockAnnotationProcessor.java:24)
at org.mockito.internal.configuration.IndependentAnnotationEngine.createMockFor(IndependentAnnotationEngine.java:44)
at org.mockito.internal.configuration.IndependentAnnotationEngine.process(IndependentAnnotationEngine.java:72)
at org.mockito.internal.configuration.InjectingAnnotationEngine.processIndependentAnnotations(InjectingAnnotationEngine.java:73)
at org.mockito.internal.configuration.InjectingAnnotationEngine.process(InjectingAnnotationEngine.java:47)
at org.mockito.MockitoAnnotations.openMocks(MockitoAnnotations.java:81)
at org.mockito.MockitoAnnotations.initMocks(MockitoAnnotations.java:99)
... 33 more
Caused by: java.lang.SecurityException: Writable dex file '/data/user/0/com.example.princeproject/cache/a5HviUnx.jar' is not allowed.
at dalvik.system.DexFile.openDexFileNative(Native Method)
at dalvik.system.DexFile.openDexFile(DexFile.java:406)
at dalvik.system.DexFile.<init>(DexFile.java:128)
at dalvik.system.DexFile.<init>(DexFile.java:101)
at dalvik.system.DexPathList.loadDexFile(DexPathList.java:438)
at dalvik.system.DexPathList.makeDexElements(DexPathList.java:397)
at dalvik.system.DexPathList.addDexPath(DexPathList.java:220)
at dalvik.system.BaseDexClassLoader.addDexPath(BaseDexClassLoader.java:287)
at java.lang.reflect.Method.invoke(Native Method)
at net.bytebuddy.android.AndroidClassLoadingStrategy$Injecting$Dispatcher$ForAndroidPVm.loadDex(AndroidClassLoadingStrategy.java:780)
... 61 more
"¯

logcatandroid‚
ﬂC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\logcat-com.example.princeproject.ListTest-testRemoveUserFromWaitlist.txt"√

device-infoandroid®
•C:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\device-info.pb"ƒ

device-info.meminfoandroid°
ûC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\meminfo"ƒ

device-info.cpuinfoandroid°
ûC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\cpuinfo*®
c
test-results.logOcom.google.testing.platform.runtime.android.driver.AndroidInstrumentationDriver≤
ØC:\Users\Alina Shahid\Desktop\CMPUT301\princeProject\PrinceProject\app\build\outputs\androidTest-results\connected\debug\Medium_Phone_API_35(AVD) - 15\testlog\test-results.log 2
text/plain