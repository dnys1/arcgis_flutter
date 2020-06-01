package com.dillonnys.arcgis_flutter;

import android.app.Application;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ArcGISViewFactory extends PlatformViewFactory {
    private final AtomicInteger mActivityState;
    private final BinaryMessenger binaryMessenger;
    private final Application application;
    private final int activityHashCode;
    private final Lifecycle lifecycle;
    private final PluginRegistry.Registrar registrar;

    ArcGISViewFactory(AtomicInteger state, BinaryMessenger binaryMessenger, Application application,
            Lifecycle lifecycle, PluginRegistry.Registrar registrar, int activityHashCode) {
        super(StandardMessageCodec.INSTANCE);
        mActivityState = state;
        this.binaryMessenger = binaryMessenger;
        this.application = application;
        this.activityHashCode = activityHashCode;
        this.lifecycle = lifecycle;
        this.registrar = registrar;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlatformView create(Context context, int id, Object args) {
        return new ArcGISPlatformView(context);
    }
}