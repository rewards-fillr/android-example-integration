# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class com.fillr.browsersdk.Fillr$JSNativeInterface

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class com.fillr.browsersdk.Fillr$JSNativeInterface
-keepclassmembers class com.fillr.browsersdk.Fillr
-keepclassmembers class com.fillr.browsersdk.Fillr$JSNativeInterface
-keep public class * implements com.fillr.browsersdk.Fillr$JSNativeInterface
-keepclassmembers class com.fillr.browsersdk.Fillr$JSNativeInterface {
    <methods>;
}

-keep class com.fillr.analytics.**
-keep interface com.fillr.analytics.**

-keepattributes JavascriptInterface
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-dontwarn rx.**
-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn com.google.android.gms.**
-keepclassmembers class ** {
 @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-dontwarn org.springframework.**
-dontwarn com.google.common.io.**
