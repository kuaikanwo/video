var player = null;
mui.ready(function () {
	document.querySelector('#back-btn').setAttribute('href', getUrlParam('last'));
	document.body.style.overflow='hidden';
	var thumbnailPath = getUrlParam('thumbnailPath');
	var videoId = getUrlParam('id');
	var videoTitle = getUrlParam('title');
	var myVideo = document.querySelector('#m-video');
	var fileName = getUrlParam('fileName');
	player = videojs('example_video_1', {
	    "autoplay":false,
	    "poster": getUrlParam('thumbnailPath'),
	    controlBar: {
	        captionsButton: false,
	        chaptersButton : false,
	        liveDisplay:false,
	        playbackRateMenuButton: false,
	        subtitlesButton:false
	      }

	}, function(){
		this.on('loadstart', function(){
			console.log('loadstart');
		})
	    this.on('loadeddata',function(){
	        console.log(1231231)
	    })
	    this.on('ended',function(){
	        /* this.pause();
	         this.hide()*/
	    })

	});
	
	player.src(fileName);
	
	changeTitle(videoTitle);
	initHot();
	mui.toast('击播放按钮即可播放呦');
});


function changeTitle(title){
	document.querySelector('#video-title').innerHTML = title;
}
function shareVideo(){
	mui.toast('您可以使用浏览器自带的分享功能欧-_-');
}

function changePlay(posterPath, fileName, title){
	player.poster('thumbnail/' + posterPath);
	
	player.src(fileName);
	
	changeTitle(title);
	initHot();
	mui.toast('击播放按钮即可播放呦');
}

function initHot(){
	//加载热门视频
	mui.ajax({
		url: '/video/videoController.do?getHot',
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
    	            li.innerHTML = '<div   class="mui-card"><a href="javascript:changePlay(\''+obj.thumbnailPath+'\',\''+obj.fileName+'\',\''+obj.title+'\');"> '+
    	                '<div class="mui-card-header mui-card-media">'+
    	            '<img onerror="this.src=\'./resource/404.png\'" src="thumbnail/'+obj.thumbnailPath+'">'+
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
