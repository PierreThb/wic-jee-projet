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
	
	
	//ajax to delete image
	$('.delete-image').click(function(e) {
		e.preventDefault();
		
		document.getElementById('deleteHiddenForm:pictureId').value = $(this).data("target");
		
		swal({
		  title: "Are you sure?",
		  text: "You will not be able to recover this file (id : "+$(this).data("target")+")",
		  type: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#DD6B55",
		  confirmButtonText: "Yes, delete it!",
		  closeOnConfirm: true
		},
		function(){
			document.getElementById('deleteHiddenForm:submitDeleteHiddenForm').click();
		});
	});
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

function refreshPage(data){
	if (data.status == "success") {
		window.location.reload();
    }
}