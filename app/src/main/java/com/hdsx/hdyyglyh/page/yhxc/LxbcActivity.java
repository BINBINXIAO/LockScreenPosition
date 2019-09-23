package com.hdsx.hdyyglyh.page.yhxc;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdsx.hdyyglyh.App;
import com.hdsx.hdyyglyh.BaseActivity;
import com.hdsx.hdyyglyh.R;
import com.hdsx.hdyyglyh.greendb.bean.PathBean;
import com.hdsx.hdyyglyh.greendb.bean.TrackBean;
import com.hdsx.hdyyglyh.greendb.gen.DaoSession;
import com.hdsx.hdyyglyh.utils.PathSmoothTool;
import com.hdsx.hdyyglyh.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hdsx.hdyyglyh.utils.CoordinateTransformUtil.wgs84tobd09;

public class LxbcActivity extends BaseActivity {


    @BindView(R.id.et_type)
    EditText etType;
    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    private DaoSession daoSession;
    private PathBean pathBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_lxbc;
    }

    @Override
    protected String getTitleId() {
        return "线路配置";
    }

    @Override
    protected void initView() {
        daoSession = App.getDao();
    }

    @Override
    protected void initData() {
        pathBean = new PathBean();
        App.getDao().getPathBeanDao().deleteAll();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_ok:
                pathBean.setUserid(et_num.getText().toString());
                pathBean.setName(etType.getText().toString());

                List<TrackBean> trackBeanList = daoSession.getTrackBeanDao().queryBuilder().list();
                if (!hasData(trackBeanList) || trackBeanList.size() < 3) {
                    daoSession.getTrackBeanDao().deleteAll();
                    ToastUtil.showToast("轨迹点太短，保存失败");
                } else {
                    //对轨迹点列表过滤
                    PathSmoothTool pathSmoothTool = new PathSmoothTool();
                    pathSmoothTool.setIntensity(2);//设置滤波强度，默认3
                    List<TrackBean> trackBeans = pathSmoothTool.kalmanFilterPath(trackBeanList);
                    //wgs84坐标
                    String lineString = getLatLonString(trackBeanList);
                    pathBean.setPolygon(lineString);
                    //bd09坐标
                    List<TrackBean> trackBeansbd09 = wgs84ToBd09(trackBeans);
                    String lineStringBd09 = getLatLonString(trackBeansbd09);
                    pathBean.setPolygonbd(lineStringBd09);

                    App.getDao().getPathBeanDao().insert(pathBean);

                    setResult(RESULT_OK);
                    finish();
                }
                break;
        }
    }


    private String getLatLonString(List<TrackBean> trackBeans) {
        StringBuffer sb = new StringBuffer("");
        for (TrackBean bean : trackBeans) {
            sb.append(bean.getLontitude()).append(" ")
                    .append(bean.getLatitude()).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String getPolygonContent(String content) {
        StringBuilder sb = new StringBuilder();
        return sb.append("LINESTRING(").append(content).append(")").toString();
    }

    private List<TrackBean> wgs84ToBd09(List<TrackBean> trackBeans) {
        for (int i = 0; i < trackBeans.size(); i++) {
            TrackBean bean = trackBeans.get(i);
            double[] gpsBean = wgs84tobd09(bean.getLontitude(), bean.getLatitude());
            bean.setLatitude(gpsBean[1]);
            bean.setLontitude(gpsBean[0]);
        }

        return trackBeans;
    }

}
