$(document).ready(function(){
	$(".check").click(function() {
		if ($('#'+this.id).hasClass('check_on'))
		{
			check_box_id = $('#'+this.id);
			$('#'+this.id).removeClass('check_on');
			$('#'+this.id+'>'+'input').val(0);
		}
		else
		{
			$('#'+this.id).addClass('check_on');
			$('#'+this.id+'>'+'input').val(1);
		}
	});
});