var sortBy = null;


mui('body').on('tap','a',function(){
    window.top.location.href=this.href;
});
var _btn = document.querySelector('#login-btn');
if(mtools.getUserInfo()){
	
	_btn.innerHTML = '<input type="text" id="name-btn" value="'+mtools.getUserInfo().name+'"/>';
	_btn.setAttribute('href', 'javascript:void(0);');
	mtools.getEl('#logoutLi').style.display = 'block';
}else{
	_btn.innerHTML = '立即登陆';
	_btn.setAttribute('href', 'register.html');
	mtools.getEl('#logoutLi').style.display = 'none';
}

function getVideoTitle(obj){
	var dom = mtools.getEl('.search-content-wrapper');
	dom.style.display = 'block';
	if(mtools.isEmpty(obj.value)){
		mtools.getEl('.title-wrapper').innerHTML = '<li style="color: #007aff;" onclick="searchVideo(\' \')">查看全部</li>';
	}else{
		mui.ajax({
			url: '/video/videoController.do?getTitles&title='+obj.value,
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

function sortVideo(type){
	sortBy = type;
	count = 0;
	mtools.getEl('#thelist').innerHTML = '';
	resetPage();
}

function searchVideo(title){
	mtools.getEl('.search-input').value = title.trim();
	mtools.getEl('.search-content-wrapper').style.display = 'none';
	count = 0;
	mtools.getEl('#thelist').innerHTML = '';
	resetPage();
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
	window.location.reload(); 
}