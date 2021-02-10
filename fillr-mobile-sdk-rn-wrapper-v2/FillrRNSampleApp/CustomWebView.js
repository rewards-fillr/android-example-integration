import React, { Component } from 'react';
import { requireNativeComponent, NativeModules } from 'react-native';
import { WebView } from "react-native-webview";
const { RNCCustomWebViewManager } = NativeModules; 

export default class CustomWebView extends Component {
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
          component: RNCCustomWebView,
           props: {
             finalUrl: this.props.finalUrl,
             onNavigationCompleted: this._onNavigationCompleted,
           },
          viewManager: RNCCustomWebViewManager
        }}
      />
    );
  }
}

const RNCCustomWebView = requireNativeComponent(
  'RNCCustomWebView',
  CustomWebView,
  WebView.extraNativeComponentConfig
);