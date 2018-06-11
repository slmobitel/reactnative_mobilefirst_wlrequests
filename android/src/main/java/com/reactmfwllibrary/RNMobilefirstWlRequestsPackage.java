
package com.reactmfwllibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;
import com.reactmfwllibrary.requests.WLRequest;
public class RNMobilefirstWlRequestsPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      List<NativeModule> modules = new ArrayList<>();
          modules.add(new WLRequest(reactContext));
          return modules;
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
      return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }
}
