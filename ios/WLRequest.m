//
//  WLRequest.m
//  test4
//
//  Created by Mobitel on 6/8/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "WLRequest.h"

@interface WLRequest ()

@end

@implementation WLRequest
RCT_EXPORT_MODULE();
NSString * const  WLREQUEST_SUCCESS=@"Success";
NSString * const  WLREQUEST_Failed=@"Error";

- (NSDictionary *)constantsToExport
{
  return @{ @"GET": GET_METHOD, @"POST" : POST_METHOD ,@"PUT" :@"PUT", @"DELETE" :@"DELETE"};
}



-(WLResourceRequest *) getHeaderParams:(NSDictionary *)headerParams forRequest:(WLResourceRequest *) request{
  NSEnumerator *enumerator = [headerParams keyEnumerator];
  id key;
  while((key = [enumerator nextObject])){
    [request setHeaderValue:[headerParams objectForKey:key] forName:key];
  }
  return request;
}

-(WLResourceRequest *) getQueryParams:(NSDictionary *)queryparams forRequest:(WLResourceRequest *) request{
  NSEnumerator *enumerator = [queryparams keyEnumerator];
  id key;
  while((key = [enumerator nextObject])){
    [request setQueryParameterValue:[queryparams objectForKey:key] forName:key];
  }
  return request;
}





RCT_EXPORT_METHOD(queryRequest :(NSDictionary *)queryparams headerParams:(NSDictionary *)headerParams url:(NSString *) urlString method:(NSString *) method resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
  //NSURL *url = [NSURL URLWithString:urlString];
  WLResourceRequest *request = [WLResourceRequest requestWithURL:[NSURL URLWithString:urlString] method:method];
  if([headerParams count] >0){
  request = [self getHeaderParams:headerParams forRequest:request];
  }
  if([queryparams count] >0){
    request = [self getHeaderParams:queryparams forRequest:request];
  }
  [request sendWithCompletionHandler:^(WLResponse *response, NSError *error) {
    NSString* resultText;
    if(error != nil){
      resultText = @"";
      resultText = [resultText stringByAppendingString: error.localizedDescription];
      resolve(resultText);
    }
    else{
      resultText = response.responseText;
      resolve(resultText);
    }
  }];
}

RCT_EXPORT_METHOD(formRequest :(NSDictionary *)formparams headerParams:(NSDictionary *)headerParams url:(NSString *) urlString method:(NSString *) method resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
  //NSURL *url = [NSURL URLWithString:urlString]; sendWithFormParameters
  WLResourceRequest *request = [WLResourceRequest requestWithURL:[NSURL URLWithString:urlString] method:method];
  if([headerParams count] >0){
    request = [self getHeaderParams:headerParams forRequest:request];
  }
  
  [request sendWithFormParameters:formparams completionHandler:^(WLResponse *response, NSError *error) {
    NSString* resultText;
    if(error != nil){
      resultText = @"";
      resultText = [resultText stringByAppendingString: error.localizedDescription];
      resolve(resultText);
    }
    else{
      resultText = response.responseText;
      resolve(resultText);
    }
  }];
}

RCT_EXPORT_METHOD(jsonRequest :(NSDictionary *)bodyparams headerParams:(NSDictionary *)headerParams url:(NSString *) urlString method:(NSString *) method resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
  //NSURL *url = [NSURL URLWithString:urlString]; sendWithFormParameters
  WLResourceRequest *request = [WLResourceRequest requestWithURL:[NSURL URLWithString:urlString] method:method];
  if([headerParams count] >0){
    request = [self getHeaderParams:headerParams forRequest:request];
  }
  [request sendWithJSON:bodyparams completionHandler:^(WLResponse *response, NSError *error) {
    NSString* resultText;
    if(error != nil){
      resultText = @"";
      resultText = [resultText stringByAppendingString: error.localizedDescription];
      resolve(resultText);
    }
    else{
      resultText = response.responseText;
      resolve(resultText);
    }
  }];
  
}


@end
