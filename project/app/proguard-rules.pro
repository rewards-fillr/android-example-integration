-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!class/unboxing/enum

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# Preserve enums. (For awful reasons, the runtime accesses them using introspection...)
-keepclassmembers enum * {
     *;
}

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# RemoteViews might need annotations.

-keepattributes *Annotation*


# Preserve all View implementations, their special context constructors, and
# their setters.

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

# Preserve all classes that have special context constructors, and the
# constructors themselves.

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# Preserve the special fields of all Parcelable implementations.

-keepclassmembers class * implements android.os.Parcelable {
    static android.os.Parcelable$Creator CREATOR;
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.

-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the required interface from the License Verification Library
# (but don't nag the developer if the library is not used at all).

-keep public interface com.android.vending.licensing.ILicensingService

-dontnote com.android.vending.licensing.ILicensingService

# The Android Compatibility library references some classes that may not be
# present in all versions of the API, but we know that's ok.

-dontwarn android.support.**

# Preserve all native method names and the names of their classes.

-keepclasseswithmembernames class * {
    native <methods>;
}

-optimizations !class/merging/horizontal
-optimizations !class/merging/vertical

-optimizations !code/allocation/variable

-keepattributes JavascriptInterface

-keepclassmembers class * {
     @android.webkit.JavascriptInterface <methods>;
 }

-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes Exceptions

-keep class com.fillr.** { *; }
-keep interface com.fillr.** { *; }
-keep class net.oneformapp.** { *; }
-keep interface net.oneformapp.** { *; }

-dontwarn com.nineoldandroids.**
-dontwarn com.google.common.**
-dontwarn com.google.android.gms.**
-dontwarn org.android.api.**
-dontwarn org.springframework.**

-keep class com.google.android.gms.location.** { *; }
-keep interface com.google.android.gms.location.** { *; }

-keep class com.google.android.gms.analytics.** { *; }
-keep interface com.google.android.gms.analytics.** { *; }

-keep class com.google.android.gms.auth.** { *; }
-keep interface com.google.android.gms.auth.** { *; }

-keep class com.google.api.client.googleapis.extensions.android.gms.auth.** { *; }
-keep interface com.google.api.client.googleapis.extensions.android.gms.auth.** { *; }

#eventbus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class **.R$* {
    <fields>;
}

-keep class com.fillr.example.integration.** { *; }
