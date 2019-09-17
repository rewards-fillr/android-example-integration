import React, { Component } from 'react';
import {Button, Text, View, TextInput} from 'react-native';
import ReactNative from 'react-native';
import { WebView } from "react-native-webview";
import FillrHeadlessMode from './FillrHeadlessModule';
import FillrWebView from './WebView';
import CustomWebView from './CustomWebView';


export default class HomeScreen extends React.Component {
  render() {
    return (
        <FillrUIWebView name="rn" />
        //<FillrCustomWebView />      
    );
  }
}

// class FillrCustomWebView extends React.Component  {

//   constructor(props) {
//     super(props);
//     FillrHeadlessMode.initializeFillr();
//     this.state = { currenturl: "https://www.fillr.com/demo", navbarUrl: ""};
//   }

//   onSubmitEdit = () => {        
//     this.setState(previousState => ({ currenturl: this.state.navbarUrl} ));
//   }

//   render() {
//     return (
//       <View style={{ flex: 1 }}>      
//         <TextInput
//             style={{height: 40, borderColor: 'gray', borderWidth: 1}}
//             placeholder="url goes here"
//             onSubmitEditing= {this.onSubmitEdit} 
//             onChangeText={(text) => this.setState({navbarUrl: text})}
//             value={this.state.navbarUrl}
//             multiline={false} /> 
            
//         <CustomWebView 
//           ref={r => this.webview = r}          
//           source={{ uri: this.state.currenturl }}  
//           javaScriptEnabled={true}
//           domStorageEnabled={true}
//           decelerationRate="normal"
//           startInLoadingState={true}
//           onNavigationCompleted={(event) => console.log('--> Navigation to issues completed')} />
//       </View>
//     );
//   }
// }

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
          url="https://www.fillr.com/demo"
          onShouldStartLoadWithRequest={() => true}
          javaScriptEnabledAndroid={true}
          startInLoadingState={true}
          style={{ flex: 1 }} /> 
      </View>
    );
  }
}