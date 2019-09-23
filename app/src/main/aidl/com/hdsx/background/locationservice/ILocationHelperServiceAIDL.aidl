package com.hdsx.background.locationservice;


interface ILocationHelperServiceAIDL {

    /**
    * 定位service绑定完毕后通知helperservice自己绑定的notiId
    * @param notiId 定位service的notiId
    */
    void onFinishBind(int notiId);
}
