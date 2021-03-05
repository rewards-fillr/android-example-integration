import PropTypes from 'prop-types';
import { requireNativeComponent, ViewPropTypes } from 'react-native';

var viewProps = {
  name: 'FillrCustomWebView',
  propTypes: {
    url: PropTypes.string,
  }
}

module.exports = requireNativeComponent('FillrCustomWebView', viewProps);