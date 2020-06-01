package com.dillonnys.arcgis_flutter;

import androidx.annotation.NonNull;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
//import io.flutter.embedding.engine.plugins.lifecycle.;
import java.util.concurrent.atomic.AtomicInteger;

/** ArcgisFlutterPlugin */
public class ArcgisFlutterPlugin implements ActivityAware, FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private final AtomicInteger state = new AtomicInteger(0);
  private FlutterPluginBinding pluginBinding;
  private Lifecycle lifecycle;

  private static final String VIEW_TYPE = "com.dillonnys/arcgis_flutter";

  public ArcgisFlutterPlugin() {}

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "arcgis_flutter");
    channel.setMethodCallHandler(this);
    pluginBinding = flutterPluginBinding;
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    pluginBinding = null;
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    pluginBinding
        .getPlatformViewRegistry()
        .registerViewFactory(
            VIEW_TYPE,
            new ArcGISViewFactory(
                state,
                pluginBinding.getBinaryMessenger(),
                binding.getActivity().getApplication(),
                lifecycle,
                null,
                binding.getActivity().hashCode()));
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.onDetachedFromActivity();
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
  }

  @Override
  public void onDetachedFromActivity() {

  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    if (registrar.activity() == null) {
      // When a background flutter view tries to register the plugin, the registrar has no activity.
      // We stop the registration process as this plugin is foreground only.
      return;
    }
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "arcgis_flutter");
    final ArcgisFlutterPlugin plugin = new ArcgisFlutterPlugin();
    channel.setMethodCallHandler(plugin);
    registrar
        .platformViewRegistry()
        .registerViewFactory(
            VIEW_TYPE,
            new ArcGISViewFactory(plugin.state, registrar.messenger(), null, null, registrar, -1));
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }
}
