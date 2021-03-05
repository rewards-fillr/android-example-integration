/**
 * Copyright (c) 2015-present, Facebook, Inc.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "FillrCustomWebViewManager.h"

#import <React/RCTUIManager.h>
#import <React/RCTDefines.h>
#import <React/RCTViewManager.h>

#import "Fillr.h"
#import "FillrCustomWebView.h"

@interface FillrCustomWebViewManager () <WKNavigationDelegate> {
  FillrCustomWebView *webView;
}

@property (nonatomic, copy) NSString *url;

@end

@implementation FillrCustomWebViewManager

RCT_EXPORT_MODULE(FillrCustomWebView)
RCT_EXPORT_VIEW_PROPERTY(url, NSString)
RCT_EXTERN_METHOD(triggerFill)

- (UIView *)view
{
  webView = [FillrCustomWebView new];
  webView.navigationDelegate = self;
  
  Fillr *fillr = [Fillr sharedInstance];
  [fillr trackWebview:webView];
  
  return webView;
}

- (void)triggerFill {
  dispatch_async(dispatch_get_main_queue(), ^{
    if (self->webView) {
      Fillr *fillr = [Fillr sharedInstance];
      [fillr triggerFill:self->webView];
    }
  });
}

- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler {
    if ([[Fillr sharedInstance] canHandleWebViewRequest:navigationAction.request]) {
        [[Fillr sharedInstance] handleWebViewRequest:navigationAction.request forWebView:webView];
        decisionHandler(WKNavigationActionPolicyCancel);
        return;
    }
    
    // Your code goes here afterwards
    decisionHandler(WKNavigationActionPolicyAllow);
}

- (void)webView:(WKWebView *)webView didCommitNavigation:(WKNavigation *)navigation {
    [[Fillr sharedInstance] handleWebViewDidStartLoad:webView];
}

- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation {
    [[Fillr sharedInstance] handleWebViewDidFinishLoad:webView];
}

@end
