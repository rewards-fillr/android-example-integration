//
//  FillrCustomWebView.m
//  FillrRNSampleApp
//
//  Created by Alex Bin Zhao on 15/2/21.
//

#import "FillrCustomWebView.h"

@implementation FillrCustomWebView

- (void)setUrl:(NSString *)url {
  [self loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:url]]];
}

@end
