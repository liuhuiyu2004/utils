
/**
 * 分页
 * @param property
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

    function setPage(index, nowPage, first, last) {
        let li = document.createElement("li");
        if (!nowPage) {
            (function(index) {
                //监听点击事件 object比如google地图中的Maker对象
                li.addEventListener("click", function(e) {
                    baseProperty.goPageEvent(index); //调用方法
                });
            })(index);
        }
        if (nowPage) {
            li.innerHTML = '(' + (index + 1) + ')';
        } else if (first) {
            li.innerHTML = '首页';
        } else if (last) {
            li.innerHTML = '末页';
        } else {
            li.innerHTML = (index + 1);
        }

        baseProperty.outerDiv.appendChild(li);
    }

    function goPage(index) {
        console.info("跳转" + index);
    }

    /**
     *
     * @param countPageNum 总页数
     * @param nowPageIndex 当前页索引（0开始）
     * @param showPageNum 显示页面数量(最少显示3页)
     */
    this.showPage = function page(countPageNum, nowPageIndex, showPageNum) {
        //region 异常数据处理
        if (countPageNum <= 1) {
            //不显示
            console.log(countPageNum, nowPageIndex, showPageNum, "不显示");
            return;
        }
        if (showPageNum < 3) {
            showPageNum = 3;
        }
        //页面异常数据处理
        if (nowPageIndex < 0) {
            nowPageIndex = 0;
        } else if (nowPageIndex > countPageNum - 1) {
            nowPageIndex = countPageNum - 1
        }
        if (showPageNum > countPageNum) {
            showPageNum = countPageNum;
        }
        //endregion
        //显示页面的中间位置//取丢弃小数部分,保留整数部分
        let halfAmount = parseInt((showPageNum - 1) / 2);
        let amendmentsNum = (showPageNum + 1) % 2;
        let frontNumber, afterNumber;
        console.log("总页面", countPageNum, "当前页面", nowPageIndex, "显示页面数量", showPageNum);
        console.log("一半数量", halfAmount, "修正", amendmentsNum);
        //当前页面位置判断。
        if (nowPageIndex <= halfAmount) {
            console.log("算法1");
            frontNumber = nowPageIndex;
            afterNumber = showPageNum - 1 - frontNumber;
        } else if ((countPageNum - nowPageIndex - 1) <= halfAmount) {
            console.log("算法2");
            afterNumber = countPageNum - nowPageIndex - 1;
            frontNumber = showPageNum - 1 - afterNumber;
        } else {
            console.log("算法3");
            if (countPageNum / 2 > nowPageIndex) {
                frontNumber = halfAmount + amendmentsNum;
                afterNumber = showPageNum - 1 - frontNumber;
            } else {
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
        console.log("前面数量", frontNumber, "后面数量", afterNumber);
        let out = '';
        if ((nowPageIndex - frontNumber) > 0) {
            out += ";首页0";
            setPage(0, (nowPageIndex == 0), true, false);
        }
        for (let i = frontNumber; i > 0; i--) {
            out += ";" + (nowPageIndex - i);
            setPage(nowPageIndex - i, false, false, false);
        }
        out += "(" + (countPageNum - 1) + ")";
        setPage(nowPageIndex, true, false, false);
        for (let i = 0; i < afterNumber; i++) {
            out += ";" + (nowPageIndex + i + 1);
            setPage(nowPageIndex + i + 1, false, false, false);
        }
        if ((nowPageIndex + afterNumber) < countPageNum - 1) {
            out += ";末页(" + (countPageNum - 1) + ")";
            setPage(countPageNum - 1, (nowPageIndex === (countPageNum - 1)), false, true);
        }
        console.log(countPageNum, nowPageIndex, showPageNum, out);
    }
}
