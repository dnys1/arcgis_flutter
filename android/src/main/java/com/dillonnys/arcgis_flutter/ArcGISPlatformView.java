package com.dillonnys.arcgis_flutter;

import android.view.View;
import androidx.annotation.Nullable;
import android.content.Context;
import io.flutter.plugin.platform.PlatformView;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class ArcGISPlatformView implements PlatformView {
    @Nullable private MapView mapView;

    ArcGISPlatformView(Context context) {
        Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR;
        double latitude = 33.449580;
        double longitude = -111.985040;
        int levelOfDetail = 10;
        ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
        MapView _mapView = new MapView(context);
        _mapView.setMap(map);
        this.mapView = _mapView;
    }

    @Override
    public View getView() {
        return mapView;
    }

    @Override
    public void dispose() {
        mapView.dispose();
    }
}