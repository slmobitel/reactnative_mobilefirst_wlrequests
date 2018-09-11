
# react-native-mobilefirst-wl-requests
This plugin supports for get,post,put,delete worklight requests on queryParams, formParams, jsonParams to mobilefirst adapters

## Getting started
// First need to register the application in mobilefirst
`$ npm install react-native-mobilefirst-wl-requests --save`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mobilefirst-wl-requests` and add `RNMobilefirstWlRequests.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMobilefirstWlRequests.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Add pod 'IBMMobileFirstPlatformFoundation' to project's pod file
#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactmfwllibrary.RNMobilefirstWlRequestsPackage;` to the imports at the top of the file
  - Add `new RNMobilefirstWlRequestsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-mobilefirst-wl-requests'
  	project(':react-native-mobilefirst-wl-requests').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mobilefirst-wl-requests/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-mobilefirst-wl-requests')
      compile('com.ibm.mobile.foundation:ibmmobilefirstplatformfoundation:8.0.+') {
       exclude group: 'com.squareup.okio'
   }
  	```

## Usage
```javascript
import {WLRequest} from 'react-native-mobilefirst-wl-requests';

// TODO: What to do with the module?
async queryRequest(){
  var values = {name: 'James', age:25};
  var headerValues= {token: '123456'};
try{
  var result  = await WLRequest.queryRequest(values,headerValues,"adapters/AdapterName/functionName","POST");
  return result;
}
catch(e){return e;}
}

async formRequest(){
  var values = {name: 'James', age:25};
  var headerValues= {token: '123456'};
try{
  var result  = await WLRequest.formRequest(values,headerValues,"adapters/AdapterName/functionName","POST");
  return result;
}
catch(e){return e;}
}
async jsonRequest(){
  var values = {name: 'James', age:25};
  var headerValues= {token: '123456'};
try{
  var result  = await WLRequest.jsonRequest(values,headerValues,"adapters/AdapterName/functionName","POST");
  return result;
}
catch(e){return e;}
}

```
