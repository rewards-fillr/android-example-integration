//
//  FillrSDKManagerModule.m
//  ReactNativeIntegration20210208
//
//  Created by Alex Bin Zhao on 8/2/21.
//

#import "FillrHeadlessModule.h"
#import "React/RCTBridgeModule.h"
#import "Fillr.h"
#import "HeadlessBaseFillProvider.h"

@interface RCT_EXTERN_REMAP_MODULE(FillrModule, FillrHeadlessModule, NSObject)
RCT_EXTERN_METHOD(initializeFillr)
RCT_EXTERN_METHOD(updateProfilePayload:(NSDictionary *)profilePayload)
@end

@interface FillrHeadlessModule()<FillrProfilePayloadDelegate> {
  NSDictionary *_profilePayload;
}
@end
 
@implementation FillrHeadlessModule

- (void)initializeFillr {
  Fillr *fillr = [Fillr sharedInstance];
  FillrConfig *fillrConfig = [[FillrConfig alloc] initWithDevKey:@"Your Dev Key" secretKey:@"Your Secret Key"];
  [fillr initialiseWithConfig:fillrConfig];
  fillr.fillProvider = [HeadlessBaseFillProvider sharedInstance];
  fillr.profilePayloadDelegate = self;
  [fillr setHeadlessFillEnabled:YES];
  [fillr setDebugMode:YES];
  [fillr setEnabled:YES];
}

- (void)updateProfilePayload:(NSDictionary *)profilePayload {
  _profilePayload = profilePayload;
}

- (void)onProfilePayloadRequestedForWebView:(UIView *)webView mappingResult:(NSDictionary *)mappingResult requestedFields:(NSArray *)requestedFields selectedFields:(NSDictionary *)selectedFields
{
  // Pass payload back
   if (_profilePayload) {
     [[Fillr sharedInstance] fillFormWithMappings:mappingResult andPayload:_profilePayload];
   }
}

@end
