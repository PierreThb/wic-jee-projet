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
	
	//ajax adding labels 
	$('.add-label').click(function(e){
		e.preventDefault();
		document.getElementById('labelForm:pictureId').value = $(this).data("target");
		document.getElementById('whatTagHiddenForm:pictureId').value = $(this).data("target");
		document.getElementById('whoTagHiddenForm:pictureId').value = $(this).data("target");
		document.getElementById('whereTagHiddenForm:pictureId').value = $(this).data("target");
	});
	
	$('.chips').material_chip();
	$('.chips-initial').material_chip({
	  data: [{
	    tag: 'Apple',
	  }, {
	    tag: 'Microsoft',
	  }, {
	    tag: 'Google',
	  }],
	});
	$('#chips-who').material_chip({
	  placeholder: 'Enter a tag',
	  secondaryPlaceholder: '+roger',
	});
	
	$('#chips-what').material_chip({
	  placeholder: 'Enter a tag',
	  secondaryPlaceholder: '+Flower',
	});
	
	$('#chips-where').material_chip({
		  placeholder: 'Enter a tag',
		  secondaryPlaceholder: '+Tokyo',
	});
	
	$('#chips-what').on('chip.add', function(e, chip) {		
		document.getElementById('whatTagHiddenForm:what').value = chip.tag;
		document.getElementById('whatTagHiddenForm:submitWhatTagHiddenForm').click();
	});
	
	$('#chips-who').on('chip.add', function(e, chip) {
		document.getElementById('whoTagHiddenForm:who').value = chip.tag;
		document.getElementById('whoTagHiddenForm:submitWhoTagHiddenForm').click();
	});
	
	$('#chips-where').on('chip.add', function(e, chip) {
		document.getElementById('whereTagHiddenForm:where').value = chip.tag;
		document.getElementById('whereTagHiddenForm:submitWhereTagHiddenForm').click();
	});
	  
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
	
	
	//ajax to delete album
	$('.delete-album').click(function(e) {
		e.preventDefault();
		
		document.getElementById('deleteHiddenForm:albumId').value = $(this).data("target");
		
		swal({
			title: "Are you sure?",
			text: "You will not be able to recover this album and all its pictures",
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
	
	//ajax to delete user
	$('.delete-user').click(function(e) {
		e.preventDefault();
		
		document.getElementById('deleteHiddenForm:userId').value = $(this).data("target");
		
		swal({
		  title: "Are you sure?",
		  text: "You will not be able to recover this user and all his albums",
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
	
	//event to preset form to share album
	$('.share-album').click(function(e) {
		//ugliest thing ever
		$("#modalShareAlbum").find('.modal-title').html("Share "+$(this).parent().parent().parent().find(".card-title").html());
		document.getElementById('shareAlbumForm:albumId').value = $(this).data("target");
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