import 'dart:html';
import 'dart:ui' as ui;

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:arcgis_flutter/arcgis_flutter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  IFrameElement _iframeElement;

  @override
  void initState() {
    super.initState();
    initPlatformState();
    _iframeElement = IFrameElement();
    _iframeElement.src =
        'https://codepen.io/esri_devlabs/embed/JZeeEZ?height=265&theme-id=light&default-tab=result';
    _iframeElement.width = '300';
    _iframeElement.height = '300';
    _iframeElement.style.border = 'none';
    // ignore: undefined_prefixed_name
    ui.platformViewRegistry.registerViewFactory(
      'iframeElement',
      (int viewId) => _iframeElement,
    );
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await ArcgisFlutter.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Widget getPlatformView() {
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return AndroidView(
          viewType: 'com.dillonnys/arcgis_flutter',
        );
      case TargetPlatform.iOS:
        return UiKitView(
          viewType: 'com.dillonnys/arcgis_flutter',
        );
      default:
        if (kIsWeb) {
          return HtmlElementView(
            viewType: 'iframeElement',
          );
        }
        throw UnsupportedError('Platform not supported.');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text('Running on: $_platformVersion\n',
                  textAlign: TextAlign.center),
              SizedBox(height: 25),
              SizedBox(
                width: 300,
                height: 300,
                child: getPlatformView(),
              )
            ],
          ),
        ),
      ),
    );
  }
}
