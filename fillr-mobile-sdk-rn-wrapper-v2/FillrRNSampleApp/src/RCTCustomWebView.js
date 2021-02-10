import React, { Component, PropTypes } from 'react';
import { WebView, requireNativeComponent, NativeModules } from 'react-native';
import { WebView } from 'react-native-webview';
import FillrHeadlessMode from './FillrHeadlessModule';

export default class CustomWebView extends Component {

  constructor(props) {
    super(props);
    FillrHeadlessMode.initializeFillr();     
  }

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
          component: RCTCustomWebView,
           props: {
             finalUrl: this.props.finalUrl,
             onNavigationCompleted: this._onNavigationCompleted,
           },
          viewManager: CustomWebViewManager
        }}
      />
    );
  }
}

const RCTCustomWebView = requireNativeComponent(
  'RCTCustomWebView',
  CustomWebView,
  WebView.extraNativeComponentConfig
);