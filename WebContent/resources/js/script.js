$(document).ready(function(){

	$(".button-collapse").sideNav();
	$('.tab-link').click(function(e){
		e.preventDefault();
		$("#"+$(this).attr("href")).click();
	});
});