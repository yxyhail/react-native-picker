package com.beefe.picker.component;

import android.content.Context;
import android.graphics.Color;

import com.beefe.picker.util.PickerUtil;
import com.beefe.picker.view.OnSelectedListener;
import com.beefe.picker.view.ReturnData;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * @author yangxinyu
 * @version V1.0
 * @date 2019-06-12 17:40
 */
public class RNPickerManager extends SimpleViewManager<PickerViewLayout> {

    @Nonnull
    @Override
    public String getName() {
        return "RNPicker";
    }

    @Nonnull
    @Override
    protected PickerViewLayout createViewInstance(@Nonnull final ThemedReactContext reactContext) {
        final PickerViewLayout pickerViewLayout = new PickerViewLayout(reactContext);
        return pickerViewLayout;
    }

    @ReactProp(name = "pickerData")
    public void setData(final PickerViewLayout view, ReadableArray data) {
        view.setPickerData(data, null);
        view.setSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(ArrayList<ReturnData> selectedList) {
                onReceiveNativeEvent(view.getContext(), view.getId(),selectedList);
            }
        });
    }

    @ReactProp(name = "isLoop")
    public void setIsLoop(PickerViewLayout view, boolean isLoop) {
        view.setIsLoop(isLoop);
    }

    @ReactProp(name = "selectedValue")
    public void setSelectValue(PickerViewLayout view, ReadableArray selectValue) {
        view.setSelectValue(PickerUtil.getSelectedValue(selectValue));
    }

    @ReactProp(name = "pickerFontColor")
    public void setPickerColor(PickerViewLayout view, ReadableArray value) {
        int[] colors = PickerUtil.getColor(value);
        int pickerTextColor = Color.argb(colors[3], colors[0], colors[1], colors[2]);
        view.setTextColor(pickerTextColor);
    }

    @ReactProp(name = "pickerFontSize")
    public void setPickFontSize(PickerViewLayout view, float size) {
        view.setTextSize(size);
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder().put("onPickerSelect",
                        MapBuilder.of("phasedRegistrationNames"
                                , MapBuilder.of("bubbled", "onPickerSelect")))
                        .build();
    }

    private void onReceiveNativeEvent(Context context, int viewId,ArrayList<ReturnData> returnData) {
        WritableMap event = Arguments.createMap();
//        event.putString("type", eventKey);
        WritableArray indexes = Arguments.createArray();
        WritableArray values = Arguments.createArray();
        for (ReturnData data : returnData) {
            indexes.pushInt(data.getIndex());
            values.pushString(data.getItem());
        }
        event.putArray("selectedValue", values);
        event.putArray("selectedIndex", indexes);

        ReactContext reactContext = (ReactContext) context;
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                viewId,
                "onPickerSelect",
                event);
    }
}
