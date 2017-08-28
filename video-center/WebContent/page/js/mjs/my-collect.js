mui.init({
    pullRefresh:
        {
            container: '#pullrefresh',
            up: {
                contentrefresh: '正在加载...',
                callback: pullupRefresh
            }
        }
});
mui.ready(function () {
    mui('#pullrefresh').pullRefresh().pullupLoading();
});
var count = 0;
/*
 * 上拉加载具体业务实现
 */
function pullupRefresh(){
    setTimeout(function() {
        mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
        var table = document.body.querySelector('.mui-table-view');
        var cells = document.body.querySelectorAll('.mui-table-view-cell');

        for (var i = cells.length, len = i + 20; i < len; i++) {
            var li = document.createElement('li');
            li.className = 'mui-table-view-cell';
            li.innerHTML = '<div class="mui-card">'+
                '<div class="mui-card-header mui-card-media">'+
            '<img src="./images/a.jpg">'+
            '<div class="mui-media-body">'+
            ' 小M'+
            ' <p>发表于 2016-06-30 15:30  2000次播放</p>'+
            '</div>'+
            '<!--<img class="mui-pull-left" src="../images/logo.png" width="34px" height="34px" />'+
            '    <h2>小M</h2>'+
            '     <p>发表于 2016-06-30 15:30 2000次播放</p>-->'+
            ' </div>'+
            '<a class="close-collect active mui-pull-right" style="    margin-right: 10px;"><span class="mui-icon mui-icon-extra mui-icon-extra-heart-filled"></span></a>'+
            '</div>';
            table.appendChild(li);
        }
    }, 100);
}