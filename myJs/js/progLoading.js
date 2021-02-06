//
//  需要 progFunction.js 支持
//

/**
 * 数据加载蒙版
 * @author liuhuiyu
 * @date 2021-02-06
 * @param property
 * @constructor
 * @eg
 let a=new ProgLoading();
 a.show();
 a.show({info:{content:'我修改文字了'}})
 b.show({info:{content:'<span style="color:#FFF">我修改里面的内容了。</span>'}});a.close()
 */
function ProgLoading(property) {
    let baseProperty = {
        style: {
            masking: {
                'width': '100%',
                'height': '100%',
                'top': '0',
                'left': '0',
                'position': 'absolute',
                'text-align': 'center',
                'background-color': '#00000077',
            },
            box: {
                'background-color': '#000161',
                'color': '#00ffd5',
                'top': '50%',
                'left': '50%',
                'position': 'absolute',
                'transform': 'translate(-50%, -50%)',
                'padding': '20px 50px',
            },
        },
        class: {
            masking_class: [],
            masking_style: {},
            box_class: [],
            box_style: {},
        },
        info: {
            div: null,
            content: "数据加载中......",
        },
    };
    let div;//蒙版
    let box;//对话框

    initProgLoading(property);

    /**
     * 参数合并初始化
     * @param property
     */
    function initProgLoading(property) {
        console.log('参数初始化');
        progFunction.assignObj(baseProperty, property);
    }

    /**
     * 添加class
     * @param item
     * @param classList
     */
    function addClass(item, classList) {
        for (let i = 0, len = classList.length; i < len; i++) {
            item.classList.add(classList[i]);
        }
    }

    /**
     * 添加样式
     * @param item
     * @param styles
     */
    function addStyle(item, styles) {
        for (let key in styles) {
            item.style[key] = styles[key];
        }
    }

    /**
     * 显示loading
     * @param property
     */
    this.show = function (property) {
        initProgLoading(property);
        if (div === undefined) {
            let nowZIndex = progFunction.getMaxZIndex() + 100;
            div = document.createElement("div");
            div.style['z-index'] = nowZIndex;
        }
        else {
            //样式 class 清理
        }
        //region 蒙版样式设定
        if (baseProperty.class.masking_class.length > 0) {
            addClass(div, baseProperty.class.masking_class);
            addStyle(div, baseProperty.class.masking_style);
        }
        else {
            addStyle(div, baseProperty.style.masking);
        }
        //endregion
        if (box === undefined) {
            box = document.createElement("div");
            div.appendChild(box);
        }
        //region box样式设定
        if (baseProperty.class.box_class.length > 0) {
            addClass(box, baseProperty.class.box_class);
            addStyle(box, baseProperty.class.box_style);
        }
        else {
            addStyle(box, baseProperty.style.box);
        }
        //endregion
        // if (baseProperty.info.div === null) {
        //     let textNode = document.createTextNode(baseProperty.info.content);
        //     box.appendChild(textNode);
        // }
        // else {
        //     box.appendChild(baseProperty.info.div);
        // }
        box.innerHTML = baseProperty.info.content;
        document.body.append(div);
    }
    this.close = function () {
        if (div !== undefined) {
            document.body.removeChild(div);
        }
    }
}