$(document).ready(function() {

	$(".button-collapse").sideNav();
	$('.modal').modal({
		complete : function() {

		}
	});

	$('.tab-link').click(function(e) {
		e.preventDefault();
		$("#" + $(this).attr("href")).click();
	});

	//modal button to refresh the page on click (hidden by default, showed when upload done)
	$('.refresh-modal').click(function(e) {
		e.preventDefault();
		var action = $(this).data("action");
		console.log($(this));
		switch (action) {
		case "refresh":
			window.location.reload();
			break;
		default:
		}
	});
	$('.refresh-modal').hide();
});

Dropzone.options.uploadForm = {
	paramName : "uploadForm:file",
	autoProcessQueue : true,
	parallelUploads : 1,
	init : function() {
		this.on("complete", function(file) {
			if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
				$('.refresh-modal').fadeIn();
			}
		});
	}
};