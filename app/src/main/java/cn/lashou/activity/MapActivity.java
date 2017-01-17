package cn.lashou.activity;


import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.ArrayList;

import butterknife.BindView;
import cn.lashou.R;
import cn.lashou.core.BaseActivity;
import cn.lashou.util.ToastUtils;

/**
 * Created by luow on 2017/1/10.
 */

public class MapActivity extends BaseActivity implements OnMarkerClickListener, OnGetGeoCoderResultListener, OnMapClickListener {

    @BindView(R.id.bmapView)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private GeoCoder mGeoCoder;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        initLocation();
    }

    @Override
    protected void initData() {

    }

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapClickListener(this);
        mBaiduMap.setOnMarkerClickListener(this);

        // 将底图标注设置为隐藏，方法如下：
        //    mBaiduMap.showMapPoi(false);//地图上没有文字地名了

        LocationClientOption option = new LocationClientOption();
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 30 * 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        //option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        //option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        // option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        //option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();

//        mGeoCoder = GeoCoder.newInstance();
//        mGeoCoder.setOnGetGeoCodeResultListener(this);
//        mGeoCoder.geocode(new GeoCodeOption().city("上海市").address("上海市"));

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        //Receive Location
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(marker.getPosition())
                .zoom(14)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
        mBaiduMap.hideInfoWindow();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Button button = new Button(getApplicationContext());
                button.setText("热修复测试");
                InfoWindow window = new InfoWindow(button, marker.getPosition(), -100);
                mBaiduMap.showInfoWindow(window);
            }
        }, 300);

        return true;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            ToastUtils.showLong(this, "抱歉,没有找到结果");
            return;
        }

        Log.e("lls", geoCodeResult.getLocation() + "897");
        //Receive Location
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(geoCodeResult.getLocation())
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            ArrayList<LatLng> data = new ArrayList<>();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng latLng1 = new LatLng(location.getLatitude() + 0.005, location.getLongitude() + 0.005);
            LatLng latLng2 = new LatLng(location.getLatitude() - 0.012, location.getLongitude() - 0.012);
            LatLng latLng3 = new LatLng(location.getLatitude() + 0.016, location.getLongitude() + 0.016);
            LatLng latLng4 = new LatLng(location.getLatitude() - 0.023, location.getLongitude() - 0.023);
            LatLng latLng5 = new LatLng(location.getLatitude() + 0.028, location.getLongitude() + 0.028);
            LatLng latLng6 = new LatLng(location.getLatitude() - 0.035, location.getLongitude() - 0.035);
            data.add(latLng1);
            data.add(latLng2);
            data.add(latLng3);
            data.add(latLng4);
            data.add(latLng5);
            data.add(latLng6);
            //Receive Location
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(14)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.animateMapStatus(mMapStatusUpdate);


            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_gcoding);
            for (int i = 0; i < 6; i++) {
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(data.get(i))
                        .icon(mCurrentMarker);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);// 关闭定位图层
        mLocationClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
