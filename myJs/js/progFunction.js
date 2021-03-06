/**
 * @author liuhuiyu
 * @date 2021-02-06
 * 程序通用函数
 * @type {{assignObj: (function(*=, *=): *), exitFullScreen: progFunction.exitFullScreen, isFullScreen: (function(): boolean), getMaxZIndex: (function(): any), fullScreen: progFunction.fullScreen}}
 */
progFunction = {
    /**
     * ajax封装
     * @author LiuHuiYu
     * Created DateTime 2021-03-06 15:11
     * @param url
     * @param data
     * @param backFunction
     * @return
     */
    ajax: function (url, data, backFunction) {
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            async: true,
            data: data,
            success: function (res) {
                if (res.success) {
                    progFunction.runFunc(backFunction.success, res.data);
                }
                else {
                    debugger;
                    progFunction.runFunc(backFunction.error, res.msg);
                }
            },
            error: function (res) {
                debugger;
                progFunction.runFunc(backFunction.error, "错误码：" + res.status);
            },
            complete: function () {
                progFunction.runFunc(backFunction.complete);
            }
        });
    },
    /**
     * 如果对象是函数，执行函数
     * @author LiuHuiYu
     * Created DateTime 2021-03-06 15:10
     * @param runFunction 要执行的函数
     * @param v1 函数参数
     * @param v2 函数参数
     * @param v3 函数参数
     * @param v4 函数参数
     * @param v5 函数参数
     * @return
     */
    runFunc: function (runFunction, v1, v2, v3, v4, v5) {
        if (typeof (runFunction) === "function") {
            runFunction(v1, v2, v3, v4, v5);
        }
    },
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
            docElm.msRequestFullscreen();
        }
    },
    /**
     * 节点全屏
     * @author LiuHuiYu
     * Created DateTime 2021-03-05 15:01
     * @param element 元素
     * @return
     */
    launchFullScreen: function (element) {
        if (element.requestFullscreen) {
            element.requestFullscreen();
        }
        else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();//火狐
        }
        else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();//ie浏览器
            document.getElementById("fullScreen").style.height = window.screen.height + "px";
            document.getElementById("fullScreen").style.width = document.documentElement.clientWidth + "px";
        }
        else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullScreen();//谷歌浏览器
        }
    },
    /**
     * 退出全屏
     */
    exitFullScreen: function () {
        if (document.exitFullscreen) {
            document.exitFullscreen();
        }
        else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        }
        else if (document.webkitCancelFullScreen) {
            document.webkitCancelFullScreen();
        }
        else if (document.msExitFullscreen) {
            document.msExitFullscreen();
        }
    },
    /**
     * 当前是否全屏
     * @returns {boolean, undefined}ie11检测不到是否全屏
     */
    isFullScreen: function () {
        return (
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
                if ($(e).css('position') !== 'static')
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
        for (let key in sources) {
            if (sources.hasOwnProperty(key)) {
                // 如果target也存在 那就再次合并
                obj[key] = target.hasOwnProperty(key) ? progFunction.assignObj(target[key], sources[key]) : sources[key];
            }
        }
        return obj;
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
    stringIsEmpty: function (obj) {
        return typeof (obj) == "string" && obj.trim() === "";
    },
    stringIsNotEmpty: function (obj) {
        return typeof (obj) == "string" && obj.trim() !== "";
    },
    /**
     * 是否是ie浏览器
     * @returns {boolean}
     * @constructor
     */
    isIE: function () {
        return (!!window.ActiveXObject || "ActiveXObject" in window);
    },
    /**
     * 遍历循环map
     * @author LiuHuiYu
     * Created DateTime 2021-03-06 15:07
     * @param map 循环map
     * @param func(value,key) 对象操作
     * @return 是否结束循环
     */
    forMap: function (map, func) {
        for (let key in map) {
            if (map.hasOwnProperty(key)) {
                let res = func(map[key], key);
                if (res === true) {
                    break;
                }
            }
        }
    }
}