/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import FillrSDKManagerModule from './FillrSDKManagerModule';
import CustomWebView from './CustomWebView';

export default class App extends React.Component {
  constructor(props){
    super(props);
    FillrSDKManagerModule.initializeFillr();
  }

  render() {
    return (
      <CustomWebView
      source={{ uri: 'https://www.fillr.com/test' }}
      style={{ marginTop: 20 }}
      />
    );
  }
}
