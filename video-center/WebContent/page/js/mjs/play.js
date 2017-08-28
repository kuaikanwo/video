var player = null;
mui.ready(function () {
	document.querySelector('#back-btn').setAttribute('href', getUrlParam('last'));
	document.body.style.overflow='hidden';
	var thumbnailPath = getUrlParam('thumbnailPath');
	var fileName = getUrlParam('fileName');
	var videoId = getUrlParam('videoId');
	var videoTitle = getUrlParam('title');
	var myVideo = document.querySelector('#m-video');
	
	player = videojs('example_video_1', {
	    "autoplay":false,
	    "poster": '/fileController.do?readThumbnail&fileName=' + getUrlParam('thumbnailPath'),
	    controlBar: {
	        captionsButton: false,
	        chaptersButton : false,
	        liveDisplay:false,
	        playbackRateMenuButton: false,
	        subtitlesButton:false
	      }

	}, function(){
	    this.on('loadeddata',function(){
	        console.log(this)
	    })
	    this.on('ended',function(){
	        /* this.pause();
	         this.hide()*/
	    })

	});
	
	if(mtools.isLogin()){
		player.src('/videoController.do?downloadVideo&videoId=' + videoId + '&userId=' + mtools.getUserId());
		//player.src('/fileController.do?downloadVideo&fileName=' + fileName);
		//player.src('/fileController.do?downloadVideo&fileName=4028ea815e2207f9015e2207f9530000.mp4');
	}
	changeTitle(videoTitle);
	
	initHot();
});

function changeTitle(title){
	document.querySelector('#video-title').innerHTML = title;
}
function shareVideo(){
	mui.toast('您可以使用浏览器自带的分享功能欧-_-');
}

function changePlay(posterPath, fileName, videoId, title){
	player.poster('/fileController.do?readThumbnail&fileName=' + posterPath);
	player.src('/fileController.do?readVideo&fileName=' + fileName);
	changeTitle(title);
	initHot();
}

function initHot(){
	//加载热门视频
	mui.ajax({
		url: '/videoController.do?getHot',
		dataType: 'json',
		success: function(data){
			if(data && data.obj.length > 0){
				var obj = null;
				var table = document.body.querySelector('.mui-table-view');
				table.innerHTML = " ";
    	        var cells = document.body.querySelectorAll('.mui-table-view-cell');
				for(var i=0; i<data.obj.length; i++){
					obj = data.obj[i];
					var li = document.createElement('li');
    	            li.className = 'mui-table-view-cell';
    	            li.innerHTML = '<div   class="mui-card"><a href="javascript:changePlay(\''+obj.thumbnailPath+'\',\''+obj.fileName+'\',\''+obj.id+'\',\''+obj.title+'\');"> '+
    	                '<div class="mui-card-header mui-card-media">'+
    	            '<img onerror="this.src=\'./resource/404.png\'" src="/fileController.do?readThumbnail&fileName='+obj.thumbnailPath+'">'+
    	            '<div class="mui-media-body">'+
    	            obj.crtUserName+
    	            ' <p>发表于 '+obj.crtTime+'  '+obj.playCount+'次播放</p>'+
    	            '</div>'+
    	            '</div>'+
    	            '</a></div>';
    	            table.appendChild(li);
				}
			}
		},
		error: function(){
			mui.toast('系统异常，请稍后重试！code=102');
		}
	});
}

function getUrlParam(key) {
    // 获取参数
    var url = window.location.search;
    // 正则筛选地址栏
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    // 匹配目标参数
    var result = url.substr(1).match(reg);
    //返回参数值
    return result ? decodeURIComponent(result[2]) : null;
}
