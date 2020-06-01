#import "ArcgisFlutterPlugin.h"
#if __has_include(<arcgis_flutter/arcgis_flutter-Swift.h>)
#import <arcgis_flutter/arcgis_flutter-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "arcgis_flutter-Swift.h"
#endif

@implementation ArcgisFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftArcgisFlutterPlugin registerWithRegistrar:registrar];
}
@end
