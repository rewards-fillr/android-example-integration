import PropTypes from 'prop-types';

import { requireNativeComponent, ViewPropTypes } from 'react-native';

var viewProps = {
  name: 'FillrWebView',
  propTypes: {
    url: PropTypes.string,
  }
}
module.exports = requireNativeComponent('FillrWebView', viewProps);