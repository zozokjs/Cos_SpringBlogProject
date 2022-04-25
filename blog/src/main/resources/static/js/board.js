/**
 * 
 */
let index = {

	init: function() {
		$("#btn-save").on("click", () => { 
			this.save();
		});
		
		$("#btn-delete").on("click", () => { 
			this.deleteById(); //delete는 예약어라서 그냥 쓰면 안 됨
		});
		
		$("#btn-update").on("click", () => { 
			this.update()
		});
		
		$("#btn-reply-save").on("click", () => { 
			this.replySave()
		});
	},
	
	
	// 게시글 저장 처리
	save: function() {
		//alert("함수 호출");
		// 회원가입 처리

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		$.ajax({
			//요청
			type : "POST",
			url : "/api/board",
			data : JSON.stringify(data), //자바스크립트 object를 json 문자열로 바꿈
			contentType :  "application/json; charset=utf-8", //요청 보내는 MIME 타입 명시
			dataType : "json" //응답 받는 타입 명시.  json처럼 생겼으면 자바스크립트로 바꿔줌.
		}).done(function(response){
			//성공
			alert("글이 저장 되었습니다");
			//alert(response);
			console.log(response);
			location.href = "/";
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
		
	},
	
	
	// 게시글 삭제 처리
	deleteById: function() {
	
		let id = $("#id").text(); 
		//왜 text?
		//input태그안에 있으면 val()로
		//태그로 감싸져 있으면 text()로 가져옴
		
		$.ajax({
			//요청
			type : "DELETE",
			url : "/api/board/"+id,
			dataType : "json"
		}).done(function(response){
			
			//console.log(response);
			alert("글이 삭제 되었습니다.");			
			location.href = "/";
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
		
	},
	
	
	
	// 게시글 수정 처리
	update: function() {
			
		let id = $("#id").val();
		

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		//console.log(id+" / "+data.title+" / "+ data.content);	
			
		$.ajax({
			//요청
			type : "PUT",
			url : "/api/board/"+id,
			data : JSON.stringify(data), 
			contentType :  "application/json; charset=utf-8",
			dataType : "json" 
		}).done(function(response){
			//성공
			console.log(response);
			alert("글 수정이 완료 되었습니다");
			//alert(response);
			
			location.href = "/";
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
		
	},

	// 댓글 저장 처리
	replySave: function() {

		let data = {
			
			boardId: $("#boardId").val(),
			userId: $("#userId").val(),
			content: $("#reply-content").val()
			
		};
		
		//let boardId = $("#boardId").val();
		//val() 붙이고 안 붙이고의 차이가 크다
		
		console.log(data);
	
		$.ajax({
			//요청
			type : "POST",
			url : `/api/board/${data.boardId}/reply`, //요청 주소에 변수 넣었음
			data : JSON.stringify(data), 
			contentType :  "application/json; charset=utf-8", 
			dataType : "json" 
		}).done(function(response){
			//성공
			//alert("댓글 저장 됨");
			//alert(response);
			console.log(response);
			location.href = `/board/${data.boardId}`;  //반드시 숫자 1 옆에 있는 ` 이걸 써야 함 L키 오른족에 있는 것과 엄연히 다름
		}).fail(function(error){
			//실패
			alert(JSON.stringify(error));
		});
	
	},
}

index.init();






