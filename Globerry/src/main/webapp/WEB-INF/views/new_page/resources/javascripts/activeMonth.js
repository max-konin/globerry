$(document).ready(function(){
	$(".month").click(function() {
		$('.month').removeClass('activeMonth');
		$('#'+this.id).addClass('activeMonth'); 
	});
});