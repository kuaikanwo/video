var myScroll,pullDownEl, pullDownOffset,pullUpEl, pullUpOffset,generatedCount = 0;
function resetPage(){
	loaded()
}
function loaded() {
	//动画部分
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
	if(myScroll)
		myScroll.destroy()
	myScroll = new iScroll('wrapper', {
		useTransition: true,
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新';
				pullUpAction();
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多';
			}
		},
		onScrollMove: function () {
		
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '释放刷新';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = 'Pull down to refresh...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '释放刷新';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = 'Pull up to load more...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中';				
				pullDownAction();	// Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中';				
				pullUpAction();	// Execute custom function (ajax call?)
			}
		}
	});
	
	loadAction();
}
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);//阻止冒泡
document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 0); }, false);

//初始状态，加载数据
function loadAction(){
	loadData();
}

//下拉刷新当前数据
function pullDownAction () {
	setTimeout(function () {
		//这里执行刷新操作
		
		myScroll.refresh();	
	}, 400);
}

//上拉加载更多数据
function pullUpAction () {
	count = 0;
	mtools.getEl('#thelist').innerHTML = '';
	loadData();
}

var count = 0;

/*
 * 上拉加载具体业务实现
 */
function loadData(){
	mui.ajax({
		url: '/video/videoController.do?queryAll&pageNo='+count+'&title='+mtools.getEl('.search-input').value + '&sortBy='+ sortBy,
    	type: 'POST',
    	processData: false,
    	contentType: false,
    	success: function(data){
    			var obj = data.obj;
        		if(obj.length==0){
        			//代表没有更多数据了。
        		}else{
        			var el, li;
        			el = mtools.getEl('#thelist');
        			
        			
        			var advertising = [
        					           	{
        					           		"banner": "https://img.alicdn.com/imgextra/i2/3009067882/TB2z69BazmfF1JjSspcXXXZMXXa_!!3009067882.jpg_800x800Q30.jpg_.webp",
        					           		"link": "https://ju.taobao.com/m/jusp/alone/detailwap/mtp.htm?item_id=542040095159&_target=_blank&spm=608.7719685.remaif1.1.7a54a0a0dpAZEH&_target=_blank&_force=wap&item_id=542040095159&bucket=7&impid=6be27sUaPF&pageno=1&floorIndex=3&is_jusp=1&_target=_blank&_format=true"
        					           	},
        					           	{
        					           		"banner": "https://img.alicdn.com/imgextra/i4/2386428115/TB2ScH8d5wIL1JjSZFsXXcXFFXa_!!2386428115.jpg_800x800Q30.jpg_.webp",
        					           		"link": "https://ju.taobao.com/m/jusp/alone/detailwap/mtp.htm?item_id=45414754763&_target=_blank&spm=608.7719685.remaif1.4.7a54a0a0M6HaYm&_target=_blank&_force=wap&item_id=45414754763&bucket=7&impid=6be24hieXQ&pageno=1&floorIndex=3&is_jusp=1&_target=_blank&_format=true"
        					           	} 
        				           ];
        			
        			
        			for (var i = 0; i < obj.length; i++) {
        				li = document.createElement('div');
        				if((i % 2) != 0)
        					li.setAttribute('class', 'float-right');
        				//
        				if(i != 0 && (i%10 == 0) && advertising[i/10-1]){
        					//显示广告位
    						var bannerObj = advertising[i/10-1];
        					li.innerHTML =  '<a href="'+bannerObj.link+'"> '+
    		    			'	<img alt="" src="'+bannerObj.banner+'"> '+
    		    			'</a>';
        					el.appendChild(li, el.childNodes[0]);
        				}
        				
        				li = document.createElement('div');
        				if((i % 2) != 0)
        					li.setAttribute('class', 'float-right');
    					li.innerHTML =  '<a href="play.html?fileName='+obj[i].fileName+'&thumbnailPath='+obj[i].thumbnailPath+'&id='+obj[i].id+'&last=index.html&title='+obj[i].title+'"> '+
		    			'	<img alt="" src="./images/111.jpg"> '+
		    			'	<p class="video-title">dddd</p> '+
		    			'	<img alt="" src="./images/xiongbenxiong.jpg" class="user-pic"> '+
		    			'	<span class="play-count"> '+
		    			obj[i].playCount +'		人浏览 '+
		    			'	</span> '+
		    			'</a>';
        				el.appendChild(li, el.childNodes[0]);
        			}
        			myScroll.refresh();
        	        count ++;
    		}
    	},
    	error: function(data){
    		mui.toast('系统异常，请稍后重试！');
    	}
    });
}