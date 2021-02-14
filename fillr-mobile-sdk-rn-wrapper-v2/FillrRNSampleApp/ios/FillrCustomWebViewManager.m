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

- (UIView *)view
{
  webView = [FillrCustomWebView new];
  webView.navigationDelegate = self;
  
  Fillr *fillr = [Fillr sharedInstance];
  [fillr trackWebview:webView];
  
  return webView;
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
