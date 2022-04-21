/**
 * 
 */
let index = {

	init: function() {
		$("#btn-save").on("click", () => { //화살표 함수 왜 씀? this를 바인딩하려고
			this.save();
		});
		
		
	},
	
	
	// 회원가입 처리
	save: function() {
		//alert("함수 호출");
		// 회원가입 처리

		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		//console.log(data);

		//ajax로 3개의 파라미터 데이터를 json으로 바꿔서 insert 요청
		//ajax는 기본적으로 비동기 호출됨.
		$.ajax({
			//요청
			type : "POST",
			url : "/auth/joinProc",
			data : JSON.stringify(data), //자바스크립트 object를 json 문자열로 바꿈
			contentType :  "application/json; charset=utf-8", //요청 보내는 MIME 타입 명시
			dataType : "json" //응답 받는 타입 명시.  json처럼 생겼으면 자바스크립트로 바꿔줌.
		}).done(function(response){
			//성공
			alert("회원가입이 완료 됨");
			//alert(response);
			console.log(response);
			location.href = "/";
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
		
	}
	
	
	// 시큐리티 없이 로그인 하기
	/**
	login: function() {

		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
		}

		$.ajax({

			type : "POST",
			url : "/api/user/login",
			data : JSON.stringify(data), 
			contentType :  "application/json; charset=utf-8",
			dataType : "json" 
		}).done(function(response){
			//성공
				console.log(response);
			alert("로그인 완료 됨");
			console.log(response);
			location.href = "/";
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
		
	}
	 */
	

}

index.init();






