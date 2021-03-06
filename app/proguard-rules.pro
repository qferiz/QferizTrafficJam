# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in I:\ADT-ANDROID-DEVTOOLS-25102014\ADT-x86-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class com.bumptech.glide.integration.volley.VolleyGlideModule
-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}
#-keep class org.apache.http.** { ; }
#-dontwarn org.apache.http.*