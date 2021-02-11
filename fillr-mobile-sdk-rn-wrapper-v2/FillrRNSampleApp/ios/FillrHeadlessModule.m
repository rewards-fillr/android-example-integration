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
@end

@interface FillrHeadlessModule()<FillrProfilePayloadDelegate>

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

- (void)onProfilePayloadRequestedForWebView:(UIView *)webView mappingResult:(NSDictionary *)mappingResult requestedFields:(NSArray *)requestedFields selectedFields:(NSDictionary *)selectedFields
{
  NSMutableDictionary *profilePayload = [[NSMutableDictionary alloc] init];
  [profilePayload setValue:@"Mr." forKey:@"PersonalDetails.Honorific"];
  [profilePayload setValue:@"Stuart" forKey:@"PersonalDetails.FirstName"];
  [profilePayload setValue:@"Rowe" forKey:@"PersonalDetails.LastName"];
  [profilePayload setValue:@"1999" forKey:@"PersonalDetails.BirthDate.Year"];
  [profilePayload setValue:@"1" forKey:@"PersonalDetails.BirthDate.Month"];
  [profilePayload setValue:@"11" forKey:@"PersonalDetails.BirthDate.Day"];
  [profilePayload setValue:@"Other" forKey:@"PersonalDetails.Gender"];
  
  [profilePayload setValue:@"stuart.rowe@fillr.com" forKey:@"ContactDetails.Emails.Email.Address"];
  [profilePayload setValue:@"1" forKey:@"ContactDetails.CellPhones.CellPhone.CountryCode"];
  [profilePayload setValue:@"5553387786" forKey:@"ContactDetails.CellPhones.CellPhone.Number"];
  [profilePayload setValue:@"1" forKey:@"ContactDetails.LandlinePhones.LandlinePhone.CountryCode"];
  [profilePayload setValue:@"208" forKey:@"ContactDetails.LandlinePhones.LandlinePhone.AreaCode"];
  [profilePayload setValue:@"4561343" forKey:@"ContactDetails.LandlinePhones.LandlinePhone.Number"];
  
  // Residential Address
  
  [profilePayload setValue:@"27 Oakmont Drive" forKey:@"AddressDetails.HomeAddress.AddressLine1"];
  [profilePayload setValue:@"27" forKey:@"AddressDetails.HomeAddress.StreetNumber"];
  [profilePayload setValue:@"Oakmont" forKey:@"AddressDetails.HomeAddress.StreetName"];
  [profilePayload setValue:@"Drive" forKey:@"AddressDetails.HomeAddress.StreetType"];
  [profilePayload setValue:@"Brentwood" forKey:@"AddressDetails.HomeAddress.Suburb"];
  [profilePayload setValue:@"California" forKey:@"AddressDetails.HomeAddress.AdministrativeArea"];
  [profilePayload setValue:@"94513" forKey:@"AddressDetails.HomeAddress.PostalCode"];
  [profilePayload setValue:@"United States" forKey:@"AddressDetails.HomeAddress.Country"];
  
  // Shipping Address
  [profilePayload setValue:@"27 Oakmont Drive" forKey:@"AddressDetails.PostalAddress.AddressLine1"];
  [profilePayload setValue:@"27" forKey:@"AddressDetails.PostalAddress.StreetNumber"];
  [profilePayload setValue:@"Oakmont" forKey:@"AddressDetails.PostalAddress.StreetName"];
  [profilePayload setValue:@"Drive" forKey:@"AddressDetails.PostalAddress.StreetType"];
  
  [profilePayload setValue:@"Brentwood" forKey:@"AddressDetails.PostalAddress.Suburb"];
  [profilePayload setValue:@"California" forKey:@"AddressDetails.PostalAddress.AdministrativeArea"];
  [profilePayload setValue:@"94513" forKey:@"AddressDetails.PostalAddress.PostalCode"];
  [profilePayload setValue:@"United States" forKey:@"AddressDetails.PostalAddress.Country"];
  
  // Billing Address
  [profilePayload setValue:@"27 Oakmont Drive" forKey:@"AddressDetails.BillingAddress.AddressLine1"];
  [profilePayload setValue:@"27" forKey:@"AddressDetails.BillingAddress.StreetNumber"];
  [profilePayload setValue:@"Oakmont" forKey:@"AddressDetails.BillingAddress.StreetName"];
  [profilePayload setValue:@"Drive" forKey:@"AddressDetails.BillingAddress.StreetType"];
  
  [profilePayload setValue:@"Brentwood" forKey:@"AddressDetails.BillingAddress.Suburb"];
  [profilePayload setValue:@"California" forKey:@"AddressDetails.BillingAddress.AdministrativeArea"];
  [profilePayload setValue:@"94513" forKey:@"AddressDetails.BillingAddress.BillingCode"];
  [profilePayload setValue:@"United States" forKey:@"AddressDetails.BillingAddress.Country"];
  
  // Work Address
  [profilePayload setValue:@"27 Oakmont Drive" forKey:@"AddressDetails.WorkAddress.AddressLine1"];
  [profilePayload setValue:@"27" forKey:@"AddressDetails.WorkAddress.StreetNumber"];
  [profilePayload setValue:@"Oakmont" forKey:@"AddressDetails.WorkAddress.StreetName"];
  [profilePayload setValue:@"Drive" forKey:@"AddressDetails.WorkAddress.StreetType"];
  
  [profilePayload setValue:@"Brentwood" forKey:@"AddressDetails.WorkAddress.Suburb"];
  [profilePayload setValue:@"California" forKey:@"AddressDetails.WorkAddress.AdministrativeArea"];
  [profilePayload setValue:@"94513" forKey:@"AddressDetails.WorkAddress.WorkCode"];
  [profilePayload setValue:@"United States" forKey:@"AddressDetails.WorkAddress.Country"];
  
  // Credit Card
  [profilePayload setValue:@"5555666677778889" forKey:@"CreditCards.CreditCard.Number"];
  [profilePayload setValue:@"Mastercard" forKey:@"CreditCards.CreditCard.Type"];
  [profilePayload setValue:@"06-2020" forKey:@"CreditCards.CreditCard.Expiry"];
  [profilePayload setValue:@"06" forKey:@"CreditCards.CreditCard.Expiry.Month"];
  [profilePayload setValue:@"2020" forKey:@"CreditCards.CreditCard.Expiry.Year"];
  [profilePayload setValue:@"John Snow" forKey:@"CreditCards.CreditCard.NameOnCard"];
  [profilePayload setValue:@"678" forKey:@"CreditCards.CreditCard.CCV"];
  
  // Pass payload back
  [[Fillr sharedInstance] fillFormWithMappings:mappingResult andPayload:profilePayload];
}

@end
