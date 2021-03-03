/**
 * @author liuhuiyu
 * @date 2021-02-06
 * 程序通用函数
 * @type {{assignObj: (function(*=, *=): *), exitFullScreen: progFunction.exitFullScreen, isFullScreen: (function(): boolean), getMaxZIndex: (function(): any), fullScreen: progFunction.fullScreen}}
 */
progFunction = {
    /**
     * 进入全屏
     */
    fullScreen: function () {
        let docElm = document.documentElement;
        //W3C
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        }
        //FireFox
        else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        }
        //Chrome等
        else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
        //IE11
        else if (docElm.msRequestFullscreen) {
            document.body.msRequestFullscreen();
        }
    },
    /**
     * 退出全屏
     */
    exitFullScreen: function () {
        if (document.exitFullScreen) {
            document.exitFullScreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
        }
        else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
    },
    /**
     * 当前是否全屏
     * @returns {boolean}
     */
    isFullScreen: function () {
        return !!(
            document.fullscreen ||
            document.mozFullScreen ||
            document.webkitIsFullScreen ||
            document.webkitFullScreen ||
            document.msFullScreen
        );
    },
    /**
     * 最大的 zIndex 获取
     * @returns {number}
     */
    getMaxZIndex: function () {
        return Math.max.apply(null,
            $.map($('body *'), function (e, n) {
                if ($(e).css('position') != 'static')
                    return parseInt($(e).css('z-index')) || -1;
            }));
    },
    /**
     * 对象合并
     * @param target 原始对象
     * @param sources 加入的合并数据
     * @returns {*}
     */
    assignObj: function (target, sources) {
        let obj = target;
        if (typeof target != 'object' || typeof sources != 'object') {
            return sources; // 如果其中一个不是对象 就返回sources
        }
        else {
            for (let key in sources) {
                // 如果target也存在 那就再次合并
                obj[key] = target.hasOwnProperty(key) ? progFunction.assignObj(target[key], sources[key]) : sources[key];
            }
        }
        return obj;
    },
    isIE: function () {
        return !!window.ActiveXObject || "ActiveXObject" in window;
    },
    /**
     * 深层复制
     * @author LiuHuiYu
     * Created DateTime 2021-03-03 10:22
     * @param obj
     * @return
     */
    clone: function (obj) {
        let o;
        if (typeof obj == "object") {
            if (obj === null) {
                o = null;
            }
            else {
                if (obj instanceof Array) {
                    o = [];
                    for (let i = 0, len = obj.length; i < len; i++) {
                        o.push(progFunction.clone(obj[i]));
                    }
                }
                else {
                    o = {};
                    for (let j in obj) {
                        if (obj.hasOwnProperty(j)) {
                            o[j] = progFunction.clone(obj[j]);
                        }
                    }
                }
            }
        }
        else {
            o = obj;
        }
        return o;
    },
}