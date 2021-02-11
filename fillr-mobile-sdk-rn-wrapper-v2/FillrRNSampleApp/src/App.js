import React, { Component } from 'react';
import {Button, Text, View, TextInput} from 'react-native';
import ReactNative from 'react-native';
import FillrHeadlessMode from './FillrHeadlessModule';
import FillrWebView from './FillrWebView';
import { WebView } from 'react-native-webview';

export default class HomeScreen extends React.Component {
  render() {
    return (
        <WebViewContainer />
    );
  }
}

class WebViewContainer extends React.Component  {

  constructor(props) {
    super(props);
    FillrHeadlessMode.initializeFillr();     
  }

  render() {
    return (
      <View style={{ flex: 1 }}>
        <FillrWebView 
          ref={component => this._webview = component}
          automaticallyAdjustContentInsets={true}
          scrollEnabled={true}
          domStorageEnabled = {true}
          javaScriptEnabled={true}
          bounces={true}
          source={{ uri: 'https://www.fillr.com/test' }}
          url="https://www.fillr.com/test"
          onShouldStartLoadWithRequest={() => true}
          javaScriptEnabledAndroid={true}
          startInLoadingState={true}
          style={{ flex: 1 }} /> 
      </View>
    );
  }
}