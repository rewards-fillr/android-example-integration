import React, { Component, PropTypes } from 'react';
import { WebView, requireNativeComponent, NativeModules } from 'react-native';
const { CustomWebViewManager } = NativeModules; 
import FillrHeadlessMode from './FillrHeadlessModule';

export default class CustomWebView extends Component {

  constructor(props) {
    super(props);
    FillrHeadlessMode.initializeFillr();     
  }

  static propTypes = {
    //...WebView.propTypes,
    //finalUrl: String
    //onNavigationCompleted: PropTypes.func
  };

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