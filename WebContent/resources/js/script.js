$(document).ready(function(){

	$(".button-collapse").sideNav();
	$('.modal').modal();
	$('.tab-link').click(function(e){
		e.preventDefault();
		$("#"+$(this).attr("href")).click();
	});
});

Dropzone.options.uploadForm = {
	paramName: "uploadForm:file",
	autoProcessQueue: true,
	parallelUploads: 1
};