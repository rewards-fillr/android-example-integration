import React from 'react';
import { View, TouchableOpacity, Text } from 'react-native';
import FillrHeadlessMode from './FillrHeadlessModule';
import FillrWebView from './FillrWebView';
import {NativeModules} from 'react-native';
import profileDefault from './ProfileDefault.json';
import profileWithCC from './ProfileWithCC.json';

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
    FillrHeadlessMode.updateProfilePayload(profileDefault);    
  }

  fillCreditCard() {
    FillrHeadlessMode.updateProfilePayload(profileWithCC);
    NativeModules.FillrCustomWebView.triggerFill();
  }

  render() {
    return (
      <View style={{ flex: 1 }}>
        <View style={{paddingLeft: 15, paddingTop: 50 }}>
          <TouchableOpacity onPress={this.fillCreditCard}>
            <Text>Fill Credit Card</Text>
          </TouchableOpacity>
        </View>
        <FillrWebView 
          ref={component => this._webview = component}
          automaticallyAdjustContentInsets={true}
          scrollEnabled={true}
          domStorageEnabled = {true}
          javaScriptEnabled={true}
          bounces={true}
          url="https://www.fillr.com/test"
          onShouldStartLoadWithRequest={() => true}
          javaScriptEnabledAndroid={true}
          startInLoadingState={true}
          style={{ flex: 1 }} />
      </View>
    );
  }
}