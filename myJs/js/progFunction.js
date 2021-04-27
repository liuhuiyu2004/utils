/**
 * 程序通用函数
 * @type {{assignObj: (function(*=, *=): *), exitFullScreen: progFunction.exitFullScreen, isFullScreen: (function(): boolean), getMaxZIndex: (function(): any), fullScreen: progFunction.fullScreen}}
 */
progFunction = {
    /**
     * 功能描述
     * @author LiuHuiYu
     * Created DateTime 2021-03-15 9:00
     * @param url
     * @param data
     * @param backFunction {success,error,complete}回调函数
     * @return
     */
    ajax: function (url, data, backFunction) {
        if (typeof (backFunction.beforeSend) === "undefined") {
            backFunction.beforeSend = function (xhr) {
                xhr.setRequestHeader("Authorization", "Basic " + btoa("test:test"));
            };
        }
        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            async: true,
            data: data,
            traditional: true,
            beforeSend: function (xhr) {
                backFunction.beforeSend(xhr);
            },
            success: function (res) {
                if (res.success) {
                    progFunction.runFunc(backFunction.success, res.data);
                }
                else {
                    // debugger;
                    progFunction.runFunc(backFunction.error, res.msg);
                }
            },
            error: function (res) {
                // debugger;
                progFunction.runFunc(backFunction.error, "错误码：" + res.status);
            },
            complete: function () {
                progFunction.runFunc(backFunction.complete);
            }
        });
    },
    runFunc: function (runFunction, v1, v2, v3, v4, v5) {
        if (typeof (runFunction) === "function") {
            runFunction(v1, v2, v3, v4, v5);
        }
    },
    /**
     * 进入全屏
     */
    fullScreen: function () {
        try {
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
        } catch (error) {
            console.error("执行全屏失败：", error);
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
        if (document.fullscreen !== undefined) {
            return document.fullscreen;
        }
        else if (document.mozFullScreen !== undefined) {
            return document.mozFullScreen;
        }
        else if (document.webkitIsFullScreen !== undefined) {
            return document.webkitIsFullScreen;
        }
        else if (document.webkitFullScreen !== undefined) {
            return document.webkitFullScreen;
        }
        else if (document.msFullScreen !== undefined) {
            return document.msFullScreen;
        }
        else {
            return undefined;
        }
    },
    /**
     * 最大的 zIndex 获取
     * @returns {number}
     */
    getMaxZIndex: function () {
        return Math.max.apply(null,
            $.map($('body *'), function (e) {
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
        if (typeof target != 'object' || typeof sources == 'function') {
            return sources;
        }
        if (typeof sources != 'object') {
            return target;
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
     * 默认赋值
     * @author LiuHuiYu
     * Created DateTime 2021-03-16 9:56
     * @param inputValue 传入值
     * @param defValue 默认返回值(null)
     * @param allowNull 允许null
     * @return
     */
    defaultValue: function (inputValue, defValue, allowNull) {
        if (allowNull === undefined) {
            allowNull = false;
        }
        if (inputValue === undefined || (inputValue === null && !allowNull)) {
            if (defValue === undefined) {
                return null;
            }
            return defValue;
        }
        return inputValue;
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
    },
    /**
     * 遍历循环 array（反向循环）
     * @author LiuHuiYu
     * Created DateTime 2021-03-08 11:12
     * @param array 数组
     * @param func 便利函数返回true 结束循环
     * @return boolean 是否终止循环跳出
     */
    forArray: function (array, func) {
        let isBreak = false;
        for (let index = array.length - 1; index >= 0; index--) {
            isBreak = func(array[index], index);
            if (isBreak === true) {
                break;
            }
        }
        return isBreak;
    },
    formatDate: function (date, fmt) {
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        let o = {
            'M+': date.getMonth() + 1,
            'd+': date.getDate(),
            'h+': date.getHours(),
            'm+': date.getMinutes(),
            's+': date.getSeconds()
        };
        for (let k in o) {
            let t = new RegExp('(' + k + ')');
            if (t.test(fmt)) {
                let str = o[k] + '';
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
            }
        }

        function padLeftZero(str) {
            return ('00' + str).substr(str.length)
        }

        return fmt;
    },
    formatTimestamp: function (timestamp, fmt) {
        let date = new Date(timestamp);
        return progFunction.formatDate(date, fmt)
    },
    /**
     * 获取随机数
     * @author LiuHuiYu
     * Created DateTime 2021-03-18 15:02
     * @param start 最小数
     * @param end  最大数
     * @param fixed 是整数
     * @return
     */
    getRandom: function (start, end, fixed) {
        if (fixed === undefined) {
            fixed = 0;
        }
        let differ = end - start
        let random = Math.random()
        return (start + differ * random).toFixed(fixed)
    },
}