$(document).ready(function(){
	$(".month").click(function() {
		$('.month').removeClass('activeMonth');
		$('#'+this.id).addClass('activeMonth'); 
		
		alert('id='+this.id+'  activeMonth='+$('.activeMonth').text());
		$(".WhenSelect option:selected").val()
	});
});