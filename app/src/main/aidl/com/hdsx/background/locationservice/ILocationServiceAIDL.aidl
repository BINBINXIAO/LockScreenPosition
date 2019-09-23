package com.hdsx.background.locationservice;



interface ILocationServiceAIDL {

    /** 当其他服务已经绑定时调起 */
    void onFinishBind();
}
