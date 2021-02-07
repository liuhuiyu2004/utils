/**
 * 分页功能
 * @author LiuHuiYu
 * Created DateTime 2021-02-07 10:09
 * @param property {showFirstPage,showLastPage,parentDiv,outerDiv,outPage,goPageEvent}
 * @return
 */
function progPaging(property) {
    let baseProperty = {
        /**
         * 显示首页
         */
        showFirstPage: true,
        /**
         * 显示末页
         */
        showLastPage: true,
        /**
         * 分页上级节点（如果没有默认是body）
         */
        parentDiv: null,
        /**
         * 分页Dom
         */
        outerDiv: null,
        /**
         * 输出函数
         * 参数（页面索引，是否是当前页，是否是首页，是否是末页）
         */
        outPage: setPage,
        /**
         * 跳转事件函数
         */
        goPageEvent: goPage,
    };
    Object.assign(baseProperty, property);

    /**
     * 设置更新分页页面
     * @param index 当前索引
     * @param isNowPage 是否是当前页面
     * @param isFirst 是否是第一页
     * @param isLast  是否是最后一页
     */
    function setPage(index, isNowPage, isFirst, isLast) {
        let li = document.createElement("li");
        if (!isNowPage) {
            (function (index) {
                //监听点击事件 object比如google地图中的Maker对象
                li.addEventListener("click", function () {
                    baseProperty.goPageEvent(index); //调用方法
                });
            })(index);
        }
        if (isNowPage) {
            li.innerHTML = '(' + (index + 1) + ')';
        }
        else if (isFirst) {
            li.innerHTML = '首页';
        }
        else if (isLast) {
            li.innerHTML = '末页';
        }
        else {
            li.innerHTML = (index + 1);
        }

        baseProperty.outerDiv.appendChild(li);
    }

    function goPage(index) {
        console.info("跳转" + index);
    }

    /**
     * 功能描述
     * @author LiuHuiYu
     * Created DateTime 2021-02-07 10:15
     * @param countPageNum 总页数
     * @param nowPageIndex 当前页索引（0开始）
     * @param showPageNum 显示页面数量(最少显示3页)
     * @param property 更新设定参数
     * @return
     */
    this.showPage = function page(countPageNum, nowPageIndex, showPageNum, property) {
        Object.assign(baseProperty, property);

        //region 异常数据处理
        if (countPageNum <= 1) {
            //不显示
            return;
        }
        if (showPageNum < 3) {
            showPageNum = 3;
        }
        //页面异常数据处理
        if (nowPageIndex < 0) {
            nowPageIndex = 0;
        }
        else if (nowPageIndex > countPageNum - 1) {
            nowPageIndex = countPageNum - 1
        }
        if (showPageNum > countPageNum) {
            showPageNum = countPageNum;
        }
        //endregion
        //显示页面的中间位置//取丢弃小数部分,保留整数部分
        let halfAmount = Math.ceil((showPageNum - 1) / 2);
        let amendmentsNum = (showPageNum + 1) % 2;
        let frontNumber, afterNumber;
        //当前页面位置判断。
        if (nowPageIndex <= halfAmount) {
            frontNumber = nowPageIndex;
            afterNumber = showPageNum - 1 - frontNumber;
        }
        else if ((countPageNum - nowPageIndex - 1) <= halfAmount) {
            afterNumber = countPageNum - nowPageIndex - 1;
            frontNumber = showPageNum - 1 - afterNumber;
        }
        else {
            if (countPageNum / 2 > nowPageIndex) {
                frontNumber = halfAmount + amendmentsNum;
                afterNumber = showPageNum - 1 - frontNumber;
            }
            else {
                afterNumber = halfAmount + amendmentsNum;
                frontNumber = showPageNum - 1 - afterNumber;
            }
        }

        if (baseProperty.parentDiv == null) {
            baseProperty.parentDiv = document.createElement("div");
            document.body.append(baseProperty.parentDiv);
        }
        if (baseProperty.outerDiv == null) {
            baseProperty.outerDiv = document.createElement("lu");
            baseProperty.parentDiv.appendChild(baseProperty.outerDiv);
        }
        baseProperty.outerDiv.innerHTML = '';
        if ((nowPageIndex - frontNumber) > 0) {
            setPage(0, (nowPageIndex === 0), true, false);
        }
        for (let i = frontNumber; i > 0; i--) {
            setPage(nowPageIndex - i, false, false, false);
        }
        setPage(nowPageIndex, true, false, false);
        for (let i = 0; i < afterNumber; i++) {
            setPage(nowPageIndex + i + 1, false, false, false);
        }
        if ((nowPageIndex + afterNumber) < countPageNum - 1) {
            setPage(countPageNum - 1, (nowPageIndex === (countPageNum - 1)), false, true);
        }
    }
}
