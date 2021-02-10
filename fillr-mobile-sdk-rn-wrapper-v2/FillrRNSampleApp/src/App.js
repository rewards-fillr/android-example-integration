import React, { Component } from 'react';
import {Button, Text, View, TextInput} from 'react-native';
import ReactNative from 'react-native';
import FillrHeadlessMode from './FillrHeadlessModule';
import FillrWebView from './FillrWebView';
import { WebView } from 'react-native-webview';

export default class HomeScreen extends React.Component {
  render() {
    return (
        <FillrUIWebView />
    );
  }
}

 class FillrCustomWebView extends React.Component  {

   constructor(props) {
     super(props);
     FillrHeadlessMode.initializeFillr();
     this.state = { currenturl: "https://www.fillr.com/test", navbarUrl: ""};
   }

   onSubmitEdit = () => {
     this.setState(previousState => ({ currenturl: this.state.navbarUrl} ));
   }

   render() {
     return (
         <WebView
           ref={r => this.webview = r}
           source={{ uri: this.state.currenturl }}
           javaScriptEnabled={true}
           domStorageEnabled={true}
           decelerationRate="normal"
           startInLoadingState={true}
           onNavigationCompleted={(event) => console.log('--> Navigation to issues completed')} />
     );
   }
 }

class FillrUIWebView extends React.Component  {

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
          url="https://www.fillr.com/test"
          onShouldStartLoadWithRequest={() => true}
          javaScriptEnabledAndroid={true}
          startInLoadingState={true}
          style={{ flex: 1 }} /> 
      </View>
    );
  }
}