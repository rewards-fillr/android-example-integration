import React, { Component } from 'react';
import { requireNativeComponent, NativeModules } from 'react-native';
import { WebView } from "react-native-webview";
const { FillrCustomWebViewManager } = NativeModules; 

export default class FillrWebView extends Component {
  static defaultProps = {
    finalUrl: 'about:blank',
  };

  _onNavigationCompleted = (event) => {
    const { onNavigationCompleted } = this.props;
    onNavigationCompleted && onNavigationCompleted(event);
  }

  render() {
    return (
      <WebView
        {...this.props}
        nativeConfig={{
          component: FillrCustomWebView,
           props: {
              url: this.props.finalUrl,
             finalUrl: this.props.finalUrl,
             onNavigationCompleted: this._onNavigationCompleted,
           },
          viewManager: FillrCustomWebViewManager
        }}
      />
    );
  }
}

const FillrCustomWebView = requireNativeComponent(
  'FillrCustomWebView',
  FillrWebView,
  WebView.extraNativeComponentConfig
);