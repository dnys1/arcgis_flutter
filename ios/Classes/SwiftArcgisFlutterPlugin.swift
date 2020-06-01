import Flutter
import UIKit
import ArcGIS

public class SwiftArcgisFlutterPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "arcgis_flutter", binaryMessenger: registrar.messenger())
    let instance = SwiftArcgisFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
    registrar.register(ArcGISViewFactory(), withId: "com.dillonnys/arcgis_flutter")
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}

public class ArcGISViewFactory : NSObject, FlutterPlatformViewFactory {
    public func create(withFrame frame: CGRect, viewIdentifier viewId: Int64, arguments args: Any?) -> FlutterPlatformView {
        return ArcGISView()
    }
}

public class ArcGISView : NSObject, FlutterPlatformView {
    public func view() -> UIView {
        let mapView = AGSMapView()
        mapView.map = AGSMap(basemapType: .navigationVector, latitude: 33.449580, longitude: -111.985040, levelOfDetail: 10)
        return mapView
    }
}
