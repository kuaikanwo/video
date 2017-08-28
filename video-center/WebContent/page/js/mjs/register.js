var codeInter = null, index = 60;
function sendAuthCode() {
	var phone  = document.querySelector('#phone').value;
	if(mtools.validatemobile(phone)){
		if (index == 60) {
			changeStyle();
			codeInter = window.setInterval(changeTip, 1000);
			mui.ajax({
				url: '/userController.do?sendAuthCode&phone='+phone,
			    type: 'POST',
			    success: function(data){
			    	if(data && data.obj){
			    		mui.alert('\''+data.obj+'\'', '您的验证码是', function() {
						});
			    	}else{
			    		mui.toast('系统异常，请稍后重试！code=1');
			    	}
			    	
			    },
			    error: function(data){
			    	mui.toast('系统异常，请稍后重试！code=2');
			    }
			});
		}
	}
}

function changeTip() {
	var sendSpan = document.querySelector('#send');
	if(index == 0){
		window.clearInterval(codeInter);
		document.querySelector('#send').innerHTML = '发送验证码';
		sendSpan.style.color = '#007aff';
		index = 60;
		return false;
	}
	sendSpan.style.color = '#ec1211';
	sendSpan.innerHTML = index+'秒后重发';
	index--;
}

function changeStyle(){
	var sendSpan = document.querySelector('#send');
	sendSpan.style.color = '#ec1211';
	sendSpan.innerHTML = index+'秒后重发';
	index--;
}

function changeBtn(obj){
	var subBtn = document.querySelector('#submit');
	var phone  = document.querySelector('#phone').value;
	if(obj.value.length >= 5 && mtools.validatemobile(phone)){
		subBtn.removeAttribute('disabled');
	}else{
		subBtn.setAttribute('disabled','');
	}
}

function submitForm(){
	var phone  = document.querySelector('#phone').value;
	var code  = document.querySelector('#code').value;
	if(mtools.validatemobile(phone) && mtools.validateAuthCode(code))
	mui.ajax({
		url: '/userController.do?login&phone='+phone+'&code='+code,
		type: 'POST',
		success: function(data){
			if(data && data.status == '2000'){
				window.localStorage.setItem('userInfo', JSON.stringify(data.obj));
				window.location.href = "index.html";
			}else{
				mui.toast(data.msg);
			}
		},
		error: function(data){
			mui.toast(data.msg);
		}
	});
}