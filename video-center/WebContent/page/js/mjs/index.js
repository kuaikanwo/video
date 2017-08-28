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
    mui('body').on('tap','a',function(){
        window.top.location.href=this.href;
    });
    var _btn = document.querySelector('#login-btn');
    if(mtools.getUserInfo()){
    	mtools.resetGoldCount(mtools.getEl('#species-count'));
    	_btn.innerHTML = '<input type="text" id="name-btn" value="'+mtools.getUserInfo().name+'"/>';
    	_btn.setAttribute('href', 'javascript:void(0);');
    	mtools.getEl('#logoutLi').style.display = 'block';
    }else{
    	_btn.innerHTML = '立即登陆';
    	_btn.setAttribute('href', 'register.html');
    	mtools.getEl('#logoutLi').style.display = 'none';
    }
});
var count = 0;

/*
 * 上拉加载具体业务实现
 */
function pullupRefresh(){
    setTimeout(function() {
    	mui.ajax({
    		url: '/videoController.do?queryAll&pageNo='+count+'&title='+mtools.getEl('.search-input').value,
        	type: 'POST',
        	processData: false,
        	contentType: false,
        	success: function(data){
        			var obj = data.obj;
            		if(obj.length==0){
            			mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。
            		}else{
            			var table = document.body.querySelector('.mui-table-view');
            	        var cells = document.body.querySelectorAll('.mui-table-view-cell');

            	        for (var i = 0; i < obj.length; i++) {
            	        	var li = document.createElement('li');
            	            li.className = 'mui-table-view-cell';
            	            li.innerHTML = '<div   class="mui-card">'+
            	                '<div class="mui-card-header mui-card-media">'+
            	            '<img src="./images/girl.png">'+
            	            '<div class="mui-media-body">'+
            	            obj[i].crtUserName+
            	            ' <p>发表于 '+obj[i].crtTime+'  '+obj[i].playCount+'次播放</p>'+
            	            '</div>'+
            	            ' </div>'+
            	            ' <div onclick="alertCount()" class="mui-card-content">'+
            	            '   <a href="play.html?fileName='+obj[i].fileName+'&thumbnailPath='+obj[i].thumbnailPath+'&id='+obj[i].id+'&last=index.html&title='+obj[i].title+'"><img onerror="this.src=\'./resource/404.png\'" src="/fileController.do?readThumbnail&fileName='+obj[i].thumbnailPath+'" alt="" width="100%"></a>'+
            	            '   <p class="video-title">'+obj[i].title+'</p>'+
            	            '  </div>'+
            	            '</div>';
            	            table.appendChild(li);
            	        }
            	        mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。
            	        count ++;
        		}
        	},
        	error: function(data){
        		mui.toast('系统异常，请稍后重试！');
        	}
        });
    }, 100);
}

function getVideoTitle(obj){
	var dom = mtools.getEl('.search-content-wrapper');
	dom.style.display = 'block';
	if(mtools.isEmpty(obj.value)){
		mtools.getEl('.title-wrapper').innerHTML = '<li style="color: #007aff;" onclick="searchVideo(\' \')">查看全部</li>';
		/*dom.style.display = 'none';*/
	}else{
		mui.ajax({
			url: '/videoController.do?getTitles&title='+obj.value,
			dataType: 'json',
			success: function(data){
				if(data && data.obj.length > 0){
					var titleStr = '';
					mtools.getEl('.title-wrapper').innerHTML = titleStr;
					for(var i=0; i<data.obj.length; i++){
						titleStr += '<li onclick="searchVideo(\''+data.obj[i]+'\')">'+data.obj[i]+'</li>';
					}
					mtools.getEl('.title-wrapper').innerHTML = titleStr;
				}
			},
			error: function(){
				mui.toast('系统异常，请稍后重试！');
			}
		});
	}
}

function searchVideo(title){
	mtools.getEl('.search-input').value = title.trim();
	mtools.getEl('.search-content-wrapper').style.display = 'none';
	count = 0;
	mtools.getEl('.mui-table-view').innerHTML = '';
	mui('#pullrefresh').pullRefresh().pullupLoading();
	mui('#pullrefresh').pullRefresh().pullupLoading();
}


function requestPage(page){
	if(mtools.isLogin()){
		showMenu()
		window.location.href = page;
	}
}
function menuHandler() {
    document.querySelector('.menu-right').style.display = 'none';
}
function showMenu() {
	var disValue = document.querySelector('.menu-right').style.display;
    if(disValue == 'block'){
        document.querySelector('.menu-right').style.display = 'none';
    }else{
        document.querySelector('.menu-right').style.display = 'block';
    }
}
function alertCount() {
    document.querySelector('.menu-right').style.display = 'none';
}

function clearLoginInfo(){
	window.localStorage.userInfo = '';
	window.location.href=window.location.href; 
}