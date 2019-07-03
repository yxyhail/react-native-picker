import { requireNativeComponent, View } from 'react-native';
import React, { Component } from 'react';


import PropTypes from "prop-types";

//requireNativeComponent函数中的第一个参数就是刚刚CircleManager.getName返回的值。
const RNPicker = requireNativeComponent("RNPicker");

// MyPicker.propTypes = {
//   /**
//    * Callback that is called continuously when the user is dragging the map.
//    */
//   pickerDic: PropTypes.array,
//   // isLoop: PropTypes.bool,
//   // pickerConfirmBtnText: PropTypes.string,
//   // pickerCancelBtnText: PropTypes.string,
//   // pickerTitleText: PropTypes.string,
//   // pickerConfirmBtnColor: PropTypes.array,
//   // pickerCancelBtnColor: PropTypes.array,
//   // pickerTitleColor: PropTypes.array,
//   // pickerToolBarBg: PropTypes.array,
//   // pickerTextEllipsisLen: PropTypes.number,
//   // pickerBg: PropTypes.array,
//   // pickerRowHeight: PropTypes.number,
//   // wheelFlex: PropTypes.array,
//   // pickerData: PropTypes.array,
//   // selectedValue: PropTypes.array,
//   // onPickerConfirm() { },
//   // onPickerCancel() { },
//   onPickerSelect: PropTypes.func,
//   // pickerToolBarFontSize: PropTypes.number,
//   // pickerFontSize: PropTypes.number,
//   // pickerFontColor: PropTypes.array,
//   ...View.propTypes
// }




class MyPicker extends Component {

  /**
   * 单击事件
   */
  _onPickerSelect(event) {
    if (this.props.onPickerSelect) {
      if (!this.props.onPickerSelect) {
        return;
      }
      let nativeEvent = event.nativeEvent;
      // 使用event.nativeEvent.msg获取原生层传递的数据
      this.props.onPickerSelect(nativeEvent);
    }
  }

  render() {
    return <RNPicker
      {...this.props}
      onPickerSelect={(event) => this._onPickerSelect(event)} />
  }
}

/**
 * Composes `View`.
 *
 * - src: string
 * - borderRadius: number
 * - resizeMode: 'cover' | 'contain' | 'stretch'
 */
module.exports = MyPicker;